package frsf.isi.died.app.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.grafo.AristaView;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.app.vista.grafo.VerticeView;
import frsf.isi.died.app.vista.material.BusquedaPanel;
import frsf.isi.died.app.vista.material.DocumentosPanel;
import frsf.isi.died.app.vista.material.VideoPanel;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class BusquedaController {
	private BusquedaPanel panelBusqueda;	
	private MaterialCapacitacionDao materialDAO;
	
	public BusquedaController(BusquedaPanel panel) {
		this.panelBusqueda = panel;
		this.panelBusqueda.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	public void crearPanel() {		
		this.panelBusqueda.setListaMateriales(materialDAO.listaMateriales(),false);
		this.panelBusqueda.construir();
	}

	public BusquedaPanel getPanelBusqueda() {
		return panelBusqueda;
	}

	public void setPanelBusqueda(BusquedaPanel panelBusqueda) {
		this.panelBusqueda = panelBusqueda;
	}
	
	public MaterialCapacitacionDao getMaterialDAO() {
		return materialDAO;	
	}

	public void setMaterialDAO(MaterialCapacitacionDao materialDAO) {
		this.materialDAO = materialDAO;
	}

	public void addWish(int[] selectedRows) {
		for (Integer i : selectedRows)
		materialDAO.getWishList().offer(panelBusqueda.getTableModel().getMaterialAt(i));	
	}
	
	public void arbolDeContenidos(int selectedRow) {
		MaterialCapacitacion m =  panelBusqueda.getTableModel().getMaterialAt(selectedRow);
		ArbolController arb = new ArbolController(m);
	}
	
	public void busquedaDeDocumentos() {
		DocumentosController doc = new DocumentosController();
	}

	public void verRelaciones(int selectedRow) {
		JFrame framePrincipal = new JFrame();
		Random rand = new Random();
		MaterialCapacitacion m = panelBusqueda.getTableModel().getMaterialAt(selectedRow);
		ArrayList<MaterialCapacitacion> matRelacionados = (ArrayList<MaterialCapacitacion>) materialDAO.findByTema(m.getTema());
		JPanel panel = new JPanel(new BorderLayout());
		ControlPanel controlPanel = new ControlPanel();
		GrafoPanel grafoPanel = new GrafoPanel();
		GrafoController grfController = new GrafoController(grafoPanel,controlPanel,matRelacionados);
		panel.add(controlPanel , BorderLayout.PAGE_START);
		panel.add(grafoPanel , BorderLayout.CENTER);
		framePrincipal.pack();
		int x,y;
		for(MaterialCapacitacion mu: matRelacionados) {
			x = rand.nextInt(1000) + 100;
			y = rand.nextInt(200) + 100;  			
			if(x!=y) {
				grfController.crearVertice(x,y, Color.BLUE, mu);
			}
			else {
				grfController.crearVertice(x+100,y, Color.BLUE, mu);
			}
			if(mu.equals(m)) {
				grfController.crearVertice(x,y, Color.RED, m);
			}
		}
		for (Arista<MaterialCapacitacion> a : materialDAO.getGrafo().getAristas()) {
			if (grafoPanel.getVerticesId().contains((a.getInicio().getValor().getId()))) {
				VerticeView origen = grafoPanel.verticePorId(a.getInicio().getValor().getId());
				VerticeView destino = grafoPanel.verticePorId(a.getFin().getValor().getId());
				AristaView arista = new AristaView();
				arista.setOrigen(origen);
				arista.setDestino(destino);
				grafoPanel.agregar(arista);
			}
		};
		framePrincipal.setContentPane(panel);
		framePrincipal.setVisible(true);
		framePrincipal.pack();
	}
}
