package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		dao= new EventsDao();
	}
	
	public List<String> getCategorie(){
		return dao.getGategorie();
	}
	public List<Integer> getAnno(){
		return dao.getAnno();
	}
	public void creaGrafo(String categoria, int anno) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici
		Graphs.addAllVertices(grafo, dao.getVertici(categoria, anno));
		
		//aggiungo archi
		for(Arco a: dao.getArchi(categoria, anno)) {
			Graphs.addEdge(grafo,  a.getE1(), a.getE2(), a.getPeso());
		}
		
		
	}
	
	public int getNVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return grafo.edgeSet().size();
	}
	
	public List <Arco> getArchiPeso(int anno, String categoria) {
		Integer pesoMax=0;
		List<Arco> migliore= new ArrayList<Arco>();
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>pesoMax) {
				pesoMax= (int) this.grafo.getEdgeWeight(e);
				migliore.clear();
				migliore.add(new Arco(grafo.getEdgeTarget(e),grafo.getEdgeSource(e), pesoMax));
			}else if(this.grafo.getEdgeWeight(e)==pesoMax) {
				pesoMax=(int) this.grafo.getEdgeWeight(e);
				migliore.add(new Arco(grafo.getEdgeTarget(e),grafo.getEdgeSource(e), pesoMax));
				
			}
		}
		return migliore;
	}
		
	public String migliore() {
		String s="";
		double pesomax=0.0;
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>pesomax) {
				pesomax=grafo.getEdgeWeight(e);
			}
		}
		for(DefaultWeightedEdge ed: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(ed)==pesomax) {
				s=grafo.getEdgeSource(ed)+" "+ grafo.getEdgeTarget(ed)+" "+grafo.getEdgeWeight(ed)+"\n";
			}
		}
		return s;
	}
}
