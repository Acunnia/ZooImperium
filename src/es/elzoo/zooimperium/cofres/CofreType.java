package es.elzoo.zooimperium.cofres;

public enum CofreType {
	COMIDA("comida"),
	ARMAS("armas");
	
	private String tipo;
	
	CofreType(String cofreTipo) {
		this.tipo = cofreTipo;
	}
	
	public String tipo() {
		return tipo;
	}
}
