package br.ufrn.baseballelimination.logica;

import br.ufrn.baseballelimination.dominio.Aresta;
import br.ufrn.baseballelimination.dominio.GrafoDirecionado;
import br.ufrn.baseballelimination.dominio.Vertice;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by joao on 25/05/16.
 */
public class FluxoMaximo {

    /**
     * Gera o grafo de fluxo em rede utilizando o algoritmo de Ford Fulkerson
     * @param grafo - Grafo a ser utilizado
     * @param origem - Vértice de origem
     * @param destino - Vértice de destino
     * @return - Grafo de Fluxo em rede
     */
    public static HashMap<Aresta, Integer> gerarGrafoFluxo(GrafoDirecionado grafo, Integer origem, Integer destino) {
        LinkedList<Aresta> caminho;
        // O fluxo presente em cada aresta
        HashMap<Aresta, Integer> fluxo = new HashMap<Aresta, Integer>();

        // Todos as arestas são iniciadas com fluxo = 0
        for (Aresta e : grafo.getArestas()) {
            fluxo.put(e, 0);
        }

        while ((caminho = bfs(grafo, origem, destino, fluxo)) != null) {
            int capacidadeMinima = Integer.MAX_VALUE;
            Object ultimoVertice = origem;
            for (Aresta aresta : caminho) {
                int c;
                if (aresta.getOrigem().equals(ultimoVertice)) {
                    c = aresta.getCapacidade() - fluxo.get(aresta);
                    ultimoVertice = aresta.getDestino();
                } else {
                    c = fluxo.get(aresta);
                    ultimoVertice = aresta.getOrigem();
                }
                if (c < capacidadeMinima) {
                    capacidadeMinima = c;
                }
            }

            // Change flow of all edges of the path by the value calculated
            // above.
            ultimoVertice = origem;
            for (Aresta aresta : caminho) {
                // If statement like above
                if (aresta.getOrigem().equals(ultimoVertice)) {
                    fluxo.put(aresta, fluxo.get(aresta) + capacidadeMinima);
                    ultimoVertice = aresta.getDestino();
                } else {
                    fluxo.put(aresta, fluxo.get(aresta) - capacidadeMinima);
                    ultimoVertice = aresta.getDestino();
                }
            }
        }
        return fluxo;
    }

    /**
     * Dado um fluxo retorna o fluxo máximo dessa rede, pegando as arestas que saem da origem e
     * somando seus fluxos.
     * @param fluxo - Grafo do fluxo
     * @param grafo - Grafo original
     * @param origem - Vértice de origem
     * @return
     */
    public static int getTamanhoFluxo(HashMap<Aresta, Integer> fluxo, GrafoDirecionado grafo, Integer origem) {
        int fluxoMaximo = 0;

        Vertice verticeOrigem = grafo.getVertice(origem);
        for (int i = 0; i < verticeOrigem.getTamanho(); i++) {
            fluxoMaximo += fluxo.get(verticeOrigem.getAresta(i));
        }

        return fluxoMaximo;
    }

    /**
     * Algoritmo para buscar o caminho
     * @param grafo - Grafo utilizado para a busca
     * @param origem - Vértice de onde deve-se partir a busca
     * @param destino - Vértice destino da busca
     * @param fluxo - Fluxo atual da rede
     * @return - Caminho
     */
    private static LinkedList<Aresta> bfs(GrafoDirecionado grafo, Integer origem, Integer destino,
                                         HashMap<Aresta, Integer> fluxo) {
        HashMap<Object, Aresta> visitados = new HashMap<>();

        LinkedList<Object> verticesIteracao = new LinkedList<>();
        visitados.put(origem, null);
        verticesIteracao.add(origem);
        all: while (!verticesIteracao.isEmpty()) {
            LinkedList<Object> tmp = new LinkedList<>();
            for (Object verticeID : verticesIteracao) {
                Vertice vertice = grafo.getVertice(verticeID);
                for (int i = 0; i < vertice.getTamanho(); i++) {
                    Aresta e = vertice.getAresta(i);

                    //Só adicionar caso o vertíce não tenha sido visitado e tenha ainda capacidade para o fluxo.
                    if (e.getOrigem().equals(verticeID)
                            && !visitados.containsKey(e.getDestino())
                            && fluxo.get(e) < e.getCapacidade()) {
                        visitados.put(e.getDestino(), e);
                        if (e.getDestino().equals(destino)) {
                            break all;
                        }
                        tmp.add(e.getDestino());
                    } else if (e.getDestino().equals(verticeID)
                            && !visitados.containsKey(e.getOrigem())
                            && fluxo.get(e) > 0) {
                        visitados.put(e.getOrigem(), e);
                        if (e.getOrigem().equals(destino)) {
                            break all;
                        }
                        tmp.add(e.getOrigem());
                    }
                }
            }
            verticesIteracao = tmp;
        }

        // Caso o caminho não tenha sido encontrado retorne null
        if (verticesIteracao.isEmpty()) {
            return null;
        }

        Object vertice = destino;
        LinkedList<Aresta> caminho = new LinkedList<>();

        // Caso um caminho tenha sido encontrado construir ele.
        while (!vertice.equals(origem)) {
            Aresta aresta = visitados.get(vertice);
            caminho.addFirst(aresta);
            if (aresta.getOrigem().equals(vertice)) {
                vertice = aresta.getDestino();
            } else {
                vertice = aresta.getOrigem();
            }
        }

        return caminho;
    }

}
