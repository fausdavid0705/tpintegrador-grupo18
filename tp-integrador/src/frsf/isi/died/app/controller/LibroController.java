package frsf.isi.died.app.controller;

import java.sql.Date;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.material.LibroPanel;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class LibroController {

	private LibroPanel panelLibro;
	private MaterialCapacitacionDao materialDAO;
	
	public LibroController(LibroPanel panel) {
		this.panelLibro = panel;
		this.panelLibro.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	
	public void agregarLibro(String titulo,Double costo,Double precio,Integer paginas) {	
		Libro l = new Libro(0,titulo, costo, precio, paginas) ;
		materialDAO .agregarLibro(l);
		this.panelLibro.setListaLibros(materialDAO.listaLibros(),true);
	}
	
	public void crearPanel() {		
		this.panelLibro.setListaLibros(materialDAO.listaLibros(),false);
		this.panelLibro.construir();
	}

	public LibroPanel getPanelLibro() {
		return panelLibro;
	}

	public void setPanelLibro(LibroPanel panelLibro) {
		this.panelLibro = panelLibro;
	}


	public void modificarLibro(Integer ID, String titulo, Double costo, Double precio, Integer paginas,
			Integer calificacion, String tema, Date fecha, Relevancia relev) {
		materialDAO.modificarLibro(ID, titulo, costo, precio, paginas, calificacion, tema, fecha, relev);		
	}


	public void eliminarLibro(Integer id) {
		if (materialDAO.findById(id).esLibro())
		materialDAO.eliminarLibro((Libro)materialDAO.findById(id));	
	}
	
	
}
