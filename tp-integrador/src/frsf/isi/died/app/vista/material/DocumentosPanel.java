package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.DocumentosController;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class DocumentosPanel extends JPanel {
    DocumentosController controller;
	private JButton btnBuscar;
	private DocumentoTableModel tableModel;
	private JTable tabla;
	private JScrollPane scrollPane;
	private JButton btnCancelar;
	private JButton verArbol;

	public DocumentosPanel() {
		this.setLayout(new GridBagLayout());
	 	tableModel = new DocumentoTableModel();
	}

	
	public void construir() {
		JFrame frame = new JFrame("Búsqueda de Documentos");
		JPanel panel = new JPanel();
		JTextField txtTitulo = new JTextField();
		JTextField txtCapitulo = new JTextField();
		JTextField txtAutor = new JTextField();
		JTextField txtResumen = new JTextField();
		JTextField txtSeccion = new JTextField();
		JTextField txtEditorial = new JTextField();
		JTextField txtMetadato = new JTextField();
		JTextField txtPalabraClave = new JTextField();
		JTextField txtParrafo = new JTextField();
		GridBagConstraints gridConst= new GridBagConstraints();
		
    	panel.setLayout(new GridBagLayout());
    	
    	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=0;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Titulo: "),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=0;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtTitulo.setColumns(30);
    	panel.add(txtTitulo,gridConst);
    	
       	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=1;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Metadato: "),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=1;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtMetadato.setColumns(30);
    	panel.add(txtMetadato,gridConst);
    	
       	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=2;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Autor: "),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=2;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtAutor.setColumns(30);
    	panel.add(txtAutor,gridConst);
    	
       	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=3;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Editorial: "),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=3;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtEditorial.setColumns(30);
    	panel.add(txtEditorial,gridConst);
    	
    	
       	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=4;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Palabra Clave:"),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=4;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtPalabraClave.setColumns(30);
    	panel.add(txtPalabraClave,gridConst);
    	
     	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=5;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Resumen:"),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=5;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtResumen.setColumns(30);
    	panel.add(txtResumen,gridConst);
    	
    	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=6;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Parrafo:"),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=6;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtParrafo.setColumns(30);
    	panel.add(txtParrafo,gridConst);

    	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=7;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Capitulo:"),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=7;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtCapitulo.setColumns(30);
    	panel.add(txtCapitulo,gridConst);
    	
    	
    	gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=8;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Seccion:"),gridConst);
    	
    	gridConst.gridx=1;
    	gridConst.gridwidth=10;	
    	gridConst.gridy=8;
    	gridConst.anchor=GridBagConstraints.EAST;
    	txtSeccion.setColumns(30);
    	panel.add(txtSeccion,gridConst);
    	
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener( e ->{
			if(!txtTitulo.getText().isEmpty()) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(txtTitulo.getText().toString())), true);
			}
			if(!txtMetadato.getText().isEmpty()) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(txtMetadato.getText().toString())), true);
			}
			if(!txtAutor.getText().isEmpty()) {
				String str = txtAutor.getText().toString();
				String[] arrayStr = str.split("\\s*,\\s*");
				for(String s : arrayStr) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(s)), true);
				}
			}
			if(!txtEditorial.getText().isEmpty()) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(txtEditorial.getText().toString())), true);
			}
			if(!txtPalabraClave.getText().isEmpty()) {
				String str = txtPalabraClave.getText().toString();
				String[] arrayStr = str.split("\\s*,\\s*");
				for(String s : arrayStr) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(s)), true);
				}
			}
			if(!txtResumen.getText().isEmpty()) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(txtResumen.getText().toString())), true);
			}
			if(!txtParrafo.getText().isEmpty()) {
				String str = txtParrafo.getText().toString();
				String[] arrayStr = str.split("\\s*,\\s*");
				for(String s : arrayStr) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(s)), true);
				}
			}
			if(!txtSeccion.getText().isEmpty()) {
				String str = txtSeccion.getText().toString();
				String[] arrayStr = str.split("\\s*,\\s*");
				for(String s : arrayStr) {
				this.setListaMateriales((controller.getMaterialDAO().findByContenido(s)), true);
				}
			}
		});
		gridConst.gridx=0;
		gridConst.gridy=9;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(btnBuscar, gridConst);
		panel.add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener( e ->{
			this.setListaMateriales(this.controller.getMaterialDAO().listaMateriales(), true);
		});
		gridConst.gridx=0;
		gridConst.gridy=10;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(btnCancelar, gridConst);
		panel.add(btnCancelar);
		
		verArbol = new JButton("Ver árbol");
		verArbol.addActionListener( e ->{
			if(this.tabla.getSelectedRow() != -1) {
					this.controller.arbolDeContenidos(this.tabla.getSelectedRow());
			}
			else {
				JOptionPane.showMessageDialog(this, "No ha seleccionado un material de la tabla");
			}
		});
		gridConst.gridx=0;
		gridConst.gridy=10;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(verArbol, gridConst);
		panel.add(verArbol);
		
		tabla = new JTable(this.tableModel);
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		gridConst.gridx=0;
		gridConst.gridwidth=7;	
		gridConst.gridy=12;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;		
		panel.add(scrollPane, gridConst);
		
		frame.add(panel);
		frame.pack();
    	frame.setLocationByPlatform(true);
    	frame.setVisible(true);
    	frame.setResizable(false);
	}

	public DocumentoTableModel getTableModel() {
		return tableModel;
	}
	
	public DocumentosController getController() {
		return controller;
	}

	
	public void setController(DocumentosController documentosController) {
		this.controller = documentosController;
	}
	
	public void setListaMateriales(List<MaterialCapacitacion> materiales,boolean actualizar) {
		this.tableModel.setMateriales(materiales);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}


}
