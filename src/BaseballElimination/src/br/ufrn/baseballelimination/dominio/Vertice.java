package br.ufrn.baseballelimination.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joao on 25/05/16.
 */
public class Vertice {

    private List<Aresta> arestas = new ArrayList<>();

    public void addAresta(Aresta aresta) {
        arestas.add(aresta);
    }

    public Aresta getAresta(int numero) {
        if(arestas.size() <= numero) {
            return null;
        } else {
            return arestas.get(numero);
        }
    }

    public int getTamanho() {
        return arestas.size();
    }

}
