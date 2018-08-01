package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import frsf.isi.died.app.controller.BusquedaController;
import frsf.isi.died.app.controller.GrafoController;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.tp.modelo.BibliotecaABB;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class BusquedaPanel extends JPanel {

	private JScrollPane scrollPane;
	private JTextField txtDesde;
	private JTextField txtHasta;
	private JTextField txtTitulo;
	private JTextField txtCalificacion;
	private JTextField txtTema;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JLabel lblTitulo;
	private JLabel lblCalificacion;
	private JLabel lblTema;
	private JLabel lblCriterios;
	private JLabel lblSeleccioneTipoDeOrdenamiento;
	private JComboBox<Ordenamiento> orden;
	private JTable tabla;
	private JButton btnBuscar;
	private JButton btnCancelar;
	private MaterialTableModel tableModel;
	private BusquedaController controller;
	/*	WISHLIST DE MATERIAL	*/
	private JButton btnWish;
	/*	RELACIONES	*/
	private JButton btnRelaciones;
	private JButton btnOrdenar;
	private JButton verArbol;
	private JButton buscarDocumentos;
	
	public BusquedaPanel() {
		this.setLayout(new GridBagLayout());
		tableModel = new MaterialTableModel();
	}
	
	public void construir() {
		GridBagConstraints gridConst= new GridBagConstraints();

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener( e ->{
			try {
				if(!txtTitulo.getText().isEmpty()) {
					this.setListaMateriales((controller.getMaterialDAO().findByTitulo(txtTitulo.getText().toString())), true);
				}
				if(!txtCalificacion.getText().isEmpty()) {
					this.setListaMateriales(controller.getMaterialDAO().findByCalificacion(Integer.parseInt(txtCalificacion.getText())), true);
				
				}
				if(!txtTema.getText().isEmpty()) {
					this.setListaMateriales(controller.getMaterialDAO().findByTema(txtTema.getText()), true);
				
				}
				if(!txtDesde.getText().isEmpty() || !txtHasta.getText().isEmpty()) {
					Date desde,hasta;
					if (txtDesde.getText().isEmpty()) desde = Date.valueOf("0001-01-01");
						else desde = Date.valueOf(txtDesde.getText());
					if (txtHasta.getText().isEmpty()) hasta = Date.valueOf("0001-01-01");
						else hasta = Date.valueOf(txtHasta.getText());
					this.setListaMateriales(controller.getMaterialDAO().findByFecha(desde, hasta), true);
				}
			
			}catch(Exception ex) {
			    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridx=6;
		gridConst.gridy=1;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(btnBuscar, gridConst);
		
		btnCancelar= new JButton("Cancelar");
		btnCancelar.addActionListener( e ->{
			this.setListaMateriales(this.controller.getMaterialDAO().listaMateriales(), true);
		});
		gridConst.gridx=6;
		gridConst.gridy=2;
		this.add(btnCancelar, gridConst);
		
		
		lblSeleccioneTipoDeOrdenamiento = new JLabel("Seleccione el tipo de ordenamiento:  ");
		gridConst.gridx=0;
		gridConst.gridy=3;
		gridConst.anchor = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblSeleccioneTipoDeOrdenamiento,gridConst);
		
		orden = new JComboBox(Ordenamiento.values());
		orden.setMaximumSize(orden.getPreferredSize());
		gridConst.fill = GridBagConstraints.BOTH;
		gridConst.gridx++;
		gridConst.gridy = 3;
		this.add(orden, gridConst);
		
		lblCriterios = new JLabel("Criterios de búsqueda: ");
		gridConst.gridx=0;
		gridConst.gridy=0;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblCriterios,gridConst);
		
		lblTitulo = new JLabel("Titulo: ");
		gridConst.gridx=0;
		gridConst.gridy=1;
		this.add(lblTitulo, gridConst);
		
		txtTitulo = new JTextField();
		txtTitulo.setColumns(10);
		gridConst.gridx=1;
		gridConst.gridy=1;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtTitulo, gridConst);
		
		lblCalificacion = new JLabel("Calificacion: ");
		gridConst.gridx=2;
		gridConst.gridy=1;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblCalificacion, gridConst);
		
		txtCalificacion = new JTextField();
		txtCalificacion.setColumns(10);
		gridConst.gridx=3;
		gridConst.gridy=1;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtCalificacion, gridConst);
		
		lblTema = new JLabel("Tema: ");
		gridConst.gridx=4;
		gridConst.gridy=1;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblTema, gridConst);
		
		txtTema = new JTextField();
		txtTema.setColumns(10);
		gridConst.gridx=5;
		gridConst.gridy=1;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtTema, gridConst);
		
		lblDesde = new JLabel("Desde: ");
		gridConst.gridx=0;
		gridConst.gridy=2;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblDesde, gridConst);
		
		txtDesde = new JTextField();
		txtDesde.setColumns(10);
		gridConst.gridx++;
		gridConst.gridy=2;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtDesde, gridConst);
		
		lblHasta = new JLabel("Hasta: ");
		gridConst.gridx++;
		gridConst.gridy=2;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblHasta, gridConst);
		
		txtHasta = new JTextField();
		txtHasta.setColumns(10);
		gridConst.gridx++;
		gridConst.gridy=2;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtHasta, gridConst);
		
		btnWish = new JButton("Añadir a WishList");
		btnWish.addActionListener( e ->{
			this.tabla.setRowSelectionAllowed(true);
			if(this.tabla.getSelectedRow() != -1) {
				this.controller.addWish(this.tabla.getSelectedRows());
				}
				else {
					JOptionPane.showMessageDialog(this, "No ha seleccionado un material de la tabla");
				}
		});
		gridConst.gridx=6;
		gridConst.gridy=3;
		this.add(btnWish, gridConst);
		
		btnRelaciones = new JButton("Ver Grafo");
		btnRelaciones.addActionListener( e ->{
		//	this.tabla.setRowSelectionAllowed(true);
			if(this.tabla.getSelectedRow() != -1) {
			this.controller.verRelaciones(this.tabla.getSelectedRow());
			}
			else {
				JOptionPane.showMessageDialog(this, "No ha seleccionado un material de la tabla");
			}
		});
		gridConst.gridx=5;
		gridConst.gridy=3;
		this.add(btnRelaciones, gridConst);
		
		btnOrdenar = new JButton("Ordenar tabla");
		btnOrdenar.addActionListener( e ->{
		//	this.tabla.setRowSelectionAllowed(true);
			Ordenamiento aux = (Ordenamiento) orden.getSelectedItem();
			
			switch(aux){
			case Titulo:{
				this.tableModel.getMateriales().sort((mc1,mc2)-> mc1.getTitulo().toUpperCase().compareTo(mc2.getTitulo().toUpperCase()));
				this.tableModel.fireTableDataChanged();
				break;
			}
			case Precio:{
				this.tableModel.getMateriales().sort((mc1,mc2)-> mc1.precio().intValue()- mc2.precio().intValue());
				this.tableModel.fireTableDataChanged();
				break;
			}
			case Calificacion:{
				this.tableModel.getMateriales().sort((mc1,mc2)-> mc1.getCalificacion().compareTo(mc2.getCalificacion()));
				this.tableModel.fireTableDataChanged();
				break;
			}
			case Relevancia:{
				this.tableModel.getMateriales().sort((mc1,mc2)-> -mc1.getRelevancia().compareTo(mc2.getRelevancia()));
				this.tableModel.fireTableDataChanged();
				break;
			}
			case Fecha:{
				this.tableModel.getMateriales().sort((mc1,mc2)-> mc1.getFechaPublicacion().compareTo(mc2.getFechaPublicacion()));
				this.tableModel.fireTableDataChanged();
				break;
			}
			default:
				break;
			}
		});
		gridConst.gridx=4;
		gridConst.gridy=3;
		this.add(btnOrdenar, gridConst);	
		
		verArbol = new JButton("Árbol de Contenidos");
		verArbol.addActionListener( e ->{
			if(this.tabla.getSelectedRow() != -1) {
					this.controller.arbolDeContenidos(this.tabla.getSelectedRow());
			}
			else {
				JOptionPane.showMessageDialog(this, "No ha seleccionado un material de la tabla");
			}
		});
		gridConst.gridx=5;
		gridConst.gridy=2;
		this.add(verArbol, gridConst);
		
		buscarDocumentos = new JButton("Buscar documentos");
		buscarDocumentos.addActionListener( e ->{
			this.controller.busquedaDeDocumentos();
		});
		gridConst.gridx=4;
		gridConst.gridy=2;
		this.add(buscarDocumentos, gridConst);
		
		
		tabla = new JTable(this.tableModel);
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		
		gridConst.gridx=0;
		gridConst.gridwidth=7;	
		gridConst.gridy=4;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;		
		this.add(scrollPane, gridConst);
	
		
	}
	
	public MaterialTableModel getTableModel() {
		return tableModel;
	}
	
	public BusquedaController getController() {
		return controller;
	}

	public void setController(BusquedaController controller) {
		this.controller = controller;
	}
	
	public void setListaMateriales(List<MaterialCapacitacion> materiales,boolean actualizar) {
		this.tableModel.setMateriales(materiales);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}

}
