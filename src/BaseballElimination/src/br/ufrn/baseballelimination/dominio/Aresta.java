package br.ufrn.baseballelimination.dominio;

/**
 * Created by joao on 25/05/16.
 */
public class Aresta {

    private Object origem;
    private Object destino;
    private int capacidade;

    public Aresta(Object origem, Object destino, int capacidade) {
        this.origem = origem;
        this.destino = destino;
        this.capacidade = capacidade;
    }

    public Object getOrigem() {
        return origem;
    }

    public void setOrigem(Object origem) {
        this.origem = origem;
    }

    public Object getDestino() {
        return destino;
    }

    public void setDestino(Object destino) {
        this.destino = destino;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return this.origem + "->" + this.destino + "(" + this.capacidade + ")";
    }
}
