package br.ufrn.baseballeliminationgraph.model;

/**
 * Created by joao on 26/05/16.
 */
public class Time {

    private String nome;
    private int jogosGanhos;
    private int jogosPerdidos;
    private int jogosRestantes;
    private String jogos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getJogosGanhos() {
        return jogosGanhos;
    }

    public void setJogosGanhos(int jogosGanhos) {
        this.jogosGanhos = jogosGanhos;
    }

    public int getJogosPerdidos() {
        return jogosPerdidos;
    }

    public void setJogosPerdidos(int jogosPerdidos) {
        this.jogosPerdidos = jogosPerdidos;
    }

    public int getJogosRestantes() {
        return jogosRestantes;
    }

    public void setJogosRestantes(int jogosRestantes) {
        this.jogosRestantes = jogosRestantes;
    }

    public String getJogos() {
        return jogos;
    }

    public void setJogos(String jogos) {
        this.jogos = jogos;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
