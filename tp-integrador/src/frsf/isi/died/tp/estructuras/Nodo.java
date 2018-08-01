package frsf.isi.died.tp.estructuras;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.died.tp.modelo.productos.Relevancia;

public class Nodo {
    private List<Nodo> hijos = new ArrayList<>();
    private String valor;
	private TipoNodo tipoNodo;
	private Nodo padre = null;
	
	//Inicializar raiz 
    public Nodo(String valor) {
    	this.valor = valor;
    	this.tipoNodo = TipoNodo.TITULO;
    }

    
	public Nodo(String valor, Nodo padre, TipoNodo tipoNodo) {
		this.valor = valor;
		this.padre = padre;
		this.tipoNodo = tipoNodo;
	}

	public Nodo() {
		
	}


	public List<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(List<Nodo> hijos) {
        this.hijos = hijos;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public TipoNodo getTipoNodo() {
		return tipoNodo;
	}

	public void setTipoNodo(TipoNodo tipoNodo) {
		this.tipoNodo = tipoNodo;
	}
	
	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public Nodo agregarHijo(String valor,TipoNodo tipoNodo) {
		Nodo nuevo = new Nodo(valor,null,tipoNodo);
		this.agregarHijo(nuevo);
		return nuevo;
	}
	
	public void agregarHijo(Nodo hijo){
		//Hijos de título: Metadatos - Resumen – Capítulos
		if(this.getTipoNodo().equals(TipoNodo.TITULO)){
			if(hijo.getTipoNodo().equals(TipoNodo.METADATO)) {
				if(this.existeLugar(hijo)) {
					hijo.setPadre(this);
					this.hijos.add(hijo);
				}
				
			}
			else if(hijo.getTipoNodo().equals(TipoNodo.RESUMEN)) {
					if(this.existeLugar(hijo)) {
						hijo.setPadre(this);
						this.hijos.add(hijo);
					}
			}
			else if(hijo.getTipoNodo().equals(TipoNodo.CAPITULO)) {
				hijo.setPadre(this);
				this.hijos.add(hijo);
			}
		}
		//Hijos de Metadatos: Autor (*), Editorial, Fecha de publicación, Palabras claves
		else if(this.getTipoNodo().equals(TipoNodo.METADATO)){	
			if(hijo.getTipoNodo().equals(TipoNodo.AUTOR)) { 
				if(!hijo.valor.isEmpty()) {
				hijo.setPadre(this);
				this.hijos.add(hijo);
			}
			}
			else if(hijo.getTipoNodo().equals(TipoNodo.EDITORIAL)) {
					if(this.existeLugar(hijo)) {
						if(!hijo.valor.isEmpty()) {
						hijo.setPadre(this);
						this.hijos.add(hijo);
					}
			}
			}
			else if(hijo.getTipoNodo().equals(TipoNodo.PALABRA_CLAVE)) {
				hijo.setPadre(this);
				if(!hijo.valor.isEmpty()) {
				this.hijos.add(hijo);
			}
			}
		}
		//Hijos de Resumen: Parrafo(*)
		else if(this.getTipoNodo().equals(TipoNodo.RESUMEN)){
			if(hijo.getTipoNodo().equals(TipoNodo.PARRAFO)) {
				if(!hijo.valor.isEmpty()) {
				hijo.setPadre(this);
				this.hijos.add(hijo);
			}
			}
		}
		//Hijos de Capitulo: Seccion(*)
		else if(this.getTipoNodo().equals(TipoNodo.CAPITULO)) {
			if(hijo.getTipoNodo().equals(TipoNodo.SECCION)) {
				if(!hijo.valor.isEmpty()) {
				hijo.setPadre(this);
				this.hijos.add(hijo);
				}
			}
			else if(hijo.getTipoNodo().equals(TipoNodo.PARRAFO)) {
				if(!hijo.valor.isEmpty()) {
				hijo.setPadre(this);
				this.hijos.add(hijo);
			}
			}
		}
		
	}
	
	public void imprimir(String salto) {
		String output = this.getTipoNodo().toString().substring(0, 1).toUpperCase() +this.getTipoNodo().toString().substring(1).toLowerCase();
		   System.out.println(salto + output +": " + this.getValor());
		   this.getHijos().forEach(each ->  each.imprimir(salto + salto));
		 }
	
	@Override 
	public String toString(){
		return (this.getTipoNodo().toString().substring(0, 1).toUpperCase() +this.getTipoNodo().toString().substring(1).toLowerCase() + ": " +this.getValor());
	}
	public int size() {
		return hijos.size()+1;
	}
	
	public boolean esVacio() {
		if(this.getValor().isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean existeLugar(Nodo nodo) {
		for (Nodo n : this.getHijos()) {
			if (n.getTipoNodo().equals(nodo.getTipoNodo())) {
				return false;
			}
		}
		return true;
	}

	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		if (this.getPadre() == null) lista.add("");
		else lista.add(this.getPadre().toString());
		lista.add(this.valor);
		lista.add(this.tipoNodo.toString());
		System.out.println("Como csv:");//-----------------------
		for (String dice : lista) System.out.println(dice);//----
		return lista;
	}
	
	public void loadFromStringRow(List<String> datos) {
		this.valor = datos.get(1);
		this.tipoNodo = TipoNodo.valueOf(datos.get(2));

	}
	
	public Boolean agregarAlArbol (Nodo n, String padre) {
		if (this.toString().equals(padre)) {
			this.agregarHijo(n);
			return true;
		}
		else {
			if (this.getHijos().isEmpty()) return false;
			else {
				for (Nodo a : this.getHijos()) {
					if (a.agregarAlArbol(n, padre)) return true;
				}
			}
		}
		return false;
	}
}