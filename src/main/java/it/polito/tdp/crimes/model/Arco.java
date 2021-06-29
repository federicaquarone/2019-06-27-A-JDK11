package it.polito.tdp.crimes.model;

public class Arco {
	
	String e1;
	String e2;
	int peso;
	
	public Arco(String e1, String e2, int peso) {
		super();
		this.e1 = e1;
		this.e2 = e2;
		this.peso = peso;
	}
	
	public String getE1() {
		return e1;
	}

	public void setE1(String e1) {
		this.e1 = e1;
	}

	public String getE2() {
		return e2;
	}

	public void setE2(String e2) {
		this.e2 = e2;
	}

	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
