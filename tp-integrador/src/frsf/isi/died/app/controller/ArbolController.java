package frsf.isi.died.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.dao.util.CsvDatasource;
import frsf.isi.died.app.vista.material.ArbolPanel;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class ArbolController {
	ArbolPanel panelArbol;
	JTree arbol;
	DefaultTreeModel modeloArbol; 
	MaterialCapacitacion mat;
	private CsvDatasource dataSource;
	MaterialCapacitacionDao materialDao;
	
	public ArbolController(MaterialCapacitacion m) {
		mat = m;
		panelArbol = new ArbolPanel(this); 	
		dataSource = new CsvDatasource();
		materialDao = new MaterialCapacitacionDaoDefault();
		this.leerArbol();
			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode();
    		modeloArbol = new DefaultTreeModel(raiz);
    		this.cargarArbol(m.getContenido(),raiz);
    		arbol = new JTree(modeloArbol);
		panelArbol.construir(arbol);
	}

	public ArbolController(MaterialCapacitacion m, boolean b) {
		if (b) {
		mat = m;
		panelArbol = new ArbolPanel(this); 	
		dataSource = new CsvDatasource();
		this.leerArbol();
		}
	}
	
	public void cargarArbol(Nodo hijo, DefaultMutableTreeNode padre) {
		String tipoNodo = hijo.getTipoNodo().toString().substring(0, 1).toUpperCase() + hijo.getTipoNodo().toString().substring(1).toLowerCase();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(tipoNodo+": "+hijo.getValor());
		modeloArbol.insertNodeInto(newNode,padre,0);
		for (Nodo n : hijo.getHijos()) {
			cargarArbol(n,newNode);
		}
		return;
	}
	
	public void agregarNodo(String padre, TipoNodo tipo , String contenido ) {
		agregarNodo(padre,tipo,contenido,mat.getContenido());
		this.guardarArbol();
	}
	
	private Boolean agregarNodo(String padre, TipoNodo tipo, String contenido, Nodo raiz) {
		if (raiz.toString().equals(padre)) {
			raiz.agregarHijo(contenido,tipo);
			return true;
		}
		for (Nodo n : raiz.getHijos()) {
			if (agregarNodo(padre,tipo,contenido,n)) return true;
		}
		return false;
	}
	
	private void leerArbol() {
		mat.getContenido().setHijos(new ArrayList<Nodo>());
		String archivo = "arbol_"+mat.getTitulo() + ".csv";
		List<List<String>> lista = dataSource.readFile(archivo);
		for (List<String> fila : lista) {
			Nodo n = new Nodo();
			n.loadFromStringRow(fila);
			mat.getContenido().agregarAlArbol(n,fila.get(0));
		}
	}
	

	private void guardarArbol() {
		String archivo = "arbol_"+mat.getTitulo() + ".csv";
		try {
			dataSource.vaciarCsv(archivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Nodo n : mat.getContenido().getHijos()) 
			guardarArbol(archivo,n);
	}
	
	private void guardarArbol(String archivo, Nodo n) {
		try {
			dataSource.agregarFilaAlFinal(archivo, n.asCsvRow());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Nodo a : n.getHijos()) guardarArbol(archivo,a);
	}
		
	
}
