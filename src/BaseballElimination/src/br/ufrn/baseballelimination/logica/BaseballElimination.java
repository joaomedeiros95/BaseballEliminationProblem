package br.ufrn.baseballelimination.logica;

import br.ufrn.baseballelimination.dominio.Aresta;
import br.ufrn.baseballelimination.dominio.GrafoDirecionado;
import br.ufrn.baseballelimination.exception.TimeNaoEncontradoException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by joao on 26/05/16.
 */
public class BaseballElimination {

    /** Informações dos times */
    private Integer qtdTimes;
    private String[] times;
    private Integer[] jogosGanhos;
    private Integer[] jogosPerdidos;
    private Integer[] jogosRestantes;
    private Integer[][] jogosContra;

    /** Variáveis auxiliares */
    private static final int INFINITO = Integer.MAX_VALUE;
    private int soma = 0;

    public BaseballElimination(String arquivo) {
        init(arquivo);
    }

    private void init(String arquivo) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(arquivo));
            int time = 0;
            boolean primeiraLinha = true;
            String linha;

            while((linha = reader.readLine()) != null) {
                if(primeiraLinha) {
                    primeiraLinha = false;
                    qtdTimes = Integer.parseInt(linha);
                    times = new String[qtdTimes];
                    jogosGanhos = new Integer[qtdTimes];
                    jogosPerdidos = new Integer[qtdTimes];
                    jogosRestantes = new Integer[qtdTimes];
                    jogosContra = new Integer[qtdTimes][qtdTimes];
                } else {
                    String[] info = linha.split("\\s+");
                    times[time] = info[0];
                    jogosGanhos[time] = Integer.parseInt(info[1]);
                    jogosPerdidos[time] = Integer.parseInt(info[2]);
                    jogosRestantes[time] = Integer.parseInt(info[3]);

                    //montar jogos contra
                    for(int j = 0; j < qtdTimes; j++) {
                        jogosContra[time][j] = Integer.parseInt(info[4 + j]);
                    }
                    time++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifica se o time foi eliminado
     * @param time - Time a ser verificado
     * @return true - Se o time foi eliminado
     * false - Se o time não foi eliminado
     * @throws TimeNaoEncontradoException - Caso o time não tenha sido encontrado lança uma exceção
     */
    public boolean verificaEliminacao(String time) throws TimeNaoEncontradoException {
        int idTime = findTimePosition(time);

        /*
         * Verifica se ainda é possível o time ganhar o campeonato, ou seja,
         * se existe um time que o total de jogos ganhos é maior que o máximo
         * que o time atual pode vencer então ele já é considerado eliminado (eliminado trivialmente).
         */
        int maximo = jogosGanhos[idTime] + jogosRestantes[idTime];
        for(int i = 0; i < qtdTimes; i++) {
            if(maximo < jogosGanhos[i]) {
                System.out.println("Time " + time + " eliminado trivialmente pois seu maximo de pontos e " + maximo +
                        " e " + times[i] + " possui " + jogosGanhos[i] + " pontos.");
                return true;
            }
        }

        Integer fluxoMaximo = getFluxoMaximo(idTime);

        if(fluxoMaximo == soma) {
            System.out.println("Time " + time + " ainda tem chance de ganhar.");
            return false;
        } else {
            System.out.println("Time " + time + " sera eliminado nao trivialmente.");
            return true;
        }
    }

    /**
     * Dado um time encontra o valor do Fluxo máximo na rede
     * @param idTime - Id do time a ser testado
     * @return - Valor do fluxo máximo na rede
     */
    private Integer getFluxoMaximo(Integer idTime) {
        int origem = 0;
        int destino;
        int pos = 1;
        int[][] verticesGames = new int[qtdTimes][qtdTimes];
        int[] verticesTimes = new int[qtdTimes];

        /* Monta os vértices dos jogos */
        for(int i = 0; i < qtdTimes; i++) {
            if(i != idTime) {   //Nesse algoritmo o time a ser testado não deve ser considerado
                for(int j = 0; j < qtdTimes; j++) {
                    if(j != idTime && j > i) {
                        verticesGames[i][j] = pos;
                        verticesGames[j][i] = pos;
                        pos++;
                    }
                }
            }
        }

        /* Monta os vértices dos times */
        for(int i = 0; i < qtdTimes; i++) {
            if(i != idTime) {   //Nesse algoritmo o time a ser testado não deve ser considerado
                verticesTimes[i] = pos;
                pos++;
            }
        }

        destino = pos;
        GrafoDirecionado grafo = new GrafoDirecionado();

        /* Utilizado posteriormente para verificar a eliminação do time */
        soma = 0;
        /* Monta o grafo */
        for(int i = 0; i < qtdTimes; i++) {
            if(i != idTime) {   //Nesse algoritmo o time a ser testado não deve ser considerado
                for(int j = 0; j < qtdTimes; j++) {
                    if(j != idTime && j > i) {
                        soma += jogosContra[i][j];

                        pos = verticesGames[i][j];
                        grafo.adicionarAresta(origem, pos, jogosContra[i][j]);
                        grafo.adicionarAresta(pos, verticesTimes[i], INFINITO);
                        grafo.adicionarAresta(pos, verticesTimes[j], INFINITO);
                    }
                }

                int maximo = jogosGanhos[idTime] + jogosRestantes[idTime];
                grafo.adicionarAresta(verticesTimes[i], destino, maximo - jogosGanhos[i]);
            }
        }

        /* Gera o grafo de Fluxo em Rede */
        HashMap<Aresta, Integer> fluxo = FluxoMaximo.gerarGrafoFluxo(grafo, origem, destino);

        return FluxoMaximo.getTamanhoFluxo(fluxo, grafo, origem);
    }

    /**
     * Acha o id de um time dado o nome dele
     * @param nome - Nome do time
     * @return - Id do time
     * @throws TimeNaoEncontradoException - Caso o time não tenha sido encontrado lança uma exceção.
     */
    public int findTimePosition(String nome) throws TimeNaoEncontradoException {
        int pos = 0;

        for(String time : times) {
            if(time.equalsIgnoreCase(nome)) {
                return pos;
            }
            pos++;
        }

        //se não achou
        throw new TimeNaoEncontradoException("Time não foi encontrado");
    }

    public Integer getQtdTimes() {
        return qtdTimes;
    }

    public String[] getTimes() {
        return times;
    }

    public Integer[] getJogosGanhos() {
        return jogosGanhos;
    }

    public Integer[] getJogosPerdidos() {
        return jogosPerdidos;
    }

    public Integer[] getJogosRestantes() {
        return jogosRestantes;
    }

    public Integer[][] getJogosContra() {
        return jogosContra;
    }
}
