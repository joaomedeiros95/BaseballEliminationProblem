package br.ufrn.baseballelimination.dominio;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by joao on 25/05/16.
 */
public class GrafoDirecionado {

    protected HashMap<Object, Vertice> vertices = new HashMap<>();
    protected LinkedList<Aresta> arestas = new LinkedList<>();

    /**
     * Método utilizado para montar o grafo.
     *
     * @param verticeOrigemID
     *            Identificador do vértice de origem
     * @param verticeDestinoID
     *            Identificador do vértice de destino
     * @param capacidade
     *            Capacity of the edge
     */
    public void adicionarAresta(Integer verticeOrigemID, Integer verticeDestinoID, int capacidade) {
        Vertice verticeOrigem;
        Vertice verticeDestino;

        if (!this.vertices.containsKey(verticeOrigemID)) {
            verticeOrigem = new Vertice();
            this.vertices.put(verticeOrigemID, verticeOrigem);
        } else {
            verticeOrigem = this.vertices.get(verticeOrigemID);
        }

        if (!this.vertices.containsKey(verticeDestinoID)) {
            verticeDestino = new Vertice();
            this.vertices.put(verticeDestinoID, verticeDestino);
        } else {
            verticeDestino = this.vertices.get(verticeDestinoID);
        }

        Aresta aresta = new Aresta(verticeOrigemID, verticeDestinoID, capacidade);
        verticeOrigem.addAresta(aresta);
        verticeDestino.addAresta(aresta);
        this.arestas.add(aresta);
    }

    public Vertice getVertice(Object nodeID) {
        return this.vertices.get(nodeID);
    }

    public LinkedList<Aresta> getArestas() {
        return this.arestas;
    }

}
