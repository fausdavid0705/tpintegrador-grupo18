package frsf.isi.died.app.controller;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.material.WishListPanel;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.util.PrioridadMaterial;

public class WishListController {
	private WishListPanel panelWishList;
	private WishListController controller;

	private MaterialCapacitacionDao materialDAO;
	
	public WishListController(WishListPanel panel) {
		this.panelWishList = panel;
		this.panelWishList.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	public void crearPanel() {		
		ArrayList<MaterialCapacitacion> wishList = materialDAO.getWishListAsList();
		Collections.sort(wishList, new PrioridadMaterial());
		this.panelWishList.setListaMateriales(wishList,false);
		this.panelWishList.construir();
	}

	public WishListPanel getPanelWishList() {
		return panelWishList;
	}

	public void setPanelWishList(WishListPanel panelBusqueda) {
		this.panelWishList = panelBusqueda;
	}
	
	public MaterialCapacitacionDao getMaterialDAO() {
		return materialDAO;
	}

	public void setMaterialDAO(MaterialCapacitacionDao materialDAO) {
		this.materialDAO = materialDAO;
	}

}
