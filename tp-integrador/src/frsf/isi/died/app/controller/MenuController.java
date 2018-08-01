package frsf.isi.died.app.controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.app.vista.material.BusquedaPanel;
import frsf.isi.died.app.vista.material.LibroPanel;
import frsf.isi.died.app.vista.material.VideoPanel;
import frsf.isi.died.app.vista.material.WishListPanel;
import java.awt.Color;

public class MenuController {

	private JFrame framePrincipal;
	private MaterialCapacitacionDao materialDAO;
	
	public MenuController(JFrame f) {
		this.framePrincipal = f;
		materialDAO = new MaterialCapacitacionDaoDefault();
	}
	
	public void showView(TiposAcciones accion) {
		switch (accion) {
		case ABM_LIBROS:
			LibroPanel panelLibros = new LibroPanel();
			LibroController controllerLibro = new LibroController(panelLibros);
			controllerLibro.crearPanel();
			framePrincipal.setContentPane(controllerLibro.getPanelLibro());
			break;
		case ABM_VIDEOS:
			VideoPanel panelVideos = new VideoPanel();
			VideoController controllerVideo = new VideoController(panelVideos);
			controllerVideo.crearPanel();
			framePrincipal.setContentPane(controllerVideo.getVideoPanel());
			break;
		case BUSQUEDA:
			BusquedaPanel panelBusqueda = new BusquedaPanel();
			BusquedaController controllerBusqueda = new BusquedaController(panelBusqueda);
			controllerBusqueda.crearPanel();
			framePrincipal.setContentPane(controllerBusqueda.getPanelBusqueda());
			break;
		case VER_WISHLIST:
			WishListPanel panelWishList = new WishListPanel();
			WishListController controllerWishList = new WishListController(panelWishList);
			controllerWishList.crearPanel();
			framePrincipal.setContentPane(controllerWishList.getPanelWishList());
		default:
			break;
		}
		framePrincipal.pack();

	}
	
	
}
