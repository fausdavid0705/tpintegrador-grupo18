package frsf.isi.died.app.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.grafo.AristaView;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.app.vista.grafo.VerticeView;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.estructuras.Grafo;
import frsf.isi.died.tp.estructuras.Vertice;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class GrafoController {


	private GrafoPanel vistaGrafo;
	private ControlPanel vistaControl;
	private MaterialCapacitacionDao materialDao;

	public GrafoController(GrafoPanel panelGrf, ControlPanel panelCtrl, List<MaterialCapacitacion> materialesRelacionados) {
		this.vistaGrafo = panelGrf;
		this.vistaGrafo.setController(this);
		this.vistaControl = panelCtrl;
		this.vistaControl.setController(this);
		this.materialDao = new MaterialCapacitacionDaoDefault();
		this.vistaControl.armarPanel(materialesRelacionados);
	}
	
	public GrafoController(GrafoPanel panelGrf) {
		this.vistaGrafo = panelGrf;
		this.vistaGrafo.setController(this);
		this.materialDao = new MaterialCapacitacionDaoDefault();
	}
	
	public void crearVertice(Integer coordenadaX, Integer coordenadaY, Color color, MaterialCapacitacion mc) {
		VerticeView v = new VerticeView(coordenadaX, coordenadaY, color);
		v.setId(mc.getId());
		v.setNombre(mc.getTitulo());
		this.vistaGrafo.agregar(v);
		this.vistaGrafo.repaint();
	}

	public void cargarArista(AristaView arista) {
		this.vistaGrafo.agregar(arista);
	}
	
	public void crearArista(AristaView arista) {
		this.materialDao.crearCamino(arista.getOrigen().getId(), arista.getDestino().getId());
		this.vistaGrafo.agregar(arista);
		this.vistaGrafo.repaint();
	}

	public void buscarCamino(Integer nodo1, Integer nodo2, Integer saltos) {
		List<MaterialCapacitacion> camino;
		camino = this.materialDao.buscarCamino(nodo1, nodo2, saltos);
		this.vistaGrafo.caminoPintar(camino);
		this.vistaGrafo.repaint();
	}
	
	public ArrayList<List<MaterialCapacitacion>> caminosPosibles(Integer nodo1, Integer nodo2){
		return this.materialDao.caminosPosibles(nodo1, nodo2);
	}
	
	
	public List<MaterialCapacitacion> listaVertices() {
		return materialDao.listaMateriales();
	}
	
	public GrafoPanel getVistaGrafo() {
		return vistaGrafo;
	}

	public MaterialCapacitacionDao getMaterialDao() {
		return this.materialDao;
	}

	public ControlPanel getVistaControl() {
		return vistaControl;
	}


}
