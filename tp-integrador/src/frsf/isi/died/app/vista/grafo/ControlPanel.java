/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsf.isi.died.app.vista.grafo;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.GrafoController;
import frsf.isi.died.app.vista.material.MaterialTableModel;
import frsf.isi.died.app.vista.material.PageRankModel;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

/**
 *
 * @author mdominguez
 */
public class ControlPanel extends JPanel {
    
    private JComboBox<MaterialCapacitacion> cmbVertice1; 
    private JComboBox<MaterialCapacitacion> cmbVertice2; 
    private JTextField txtLongitudCamino;
    private JButton btnBuscarCamino; 
    private GrafoController controller;
    private List<MaterialCapacitacion> listaVertices;
    private JButton mostrarPageRank; 
    private JButton btnCaminosDisponibles;
	private PageRankModel tableModel;
	private JTable tabla;
	private JScrollPane scrollPane;

    public void armarPanel(List<MaterialCapacitacion> listaVertices){
		GridBagConstraints gridConst= new GridBagConstraints();
    	tableModel = new PageRankModel();
    	this.setLayout(new GridBagLayout());
    	this.listaVertices = listaVertices;
    	this.cmbVertice1 = new JComboBox(listaVertices.toArray()); 
        this.cmbVertice2 = new JComboBox(listaVertices.toArray());
        gridConst.gridx=0;
    	gridConst.gridwidth=0;
    	gridConst.gridy=0;
    	gridConst.anchor=GridBagConstraints.WEST;
        this.add(new JLabel("Buscar camino de N saltos: "),gridConst);        
        this.txtLongitudCamino = new JTextField(5);
        this.btnBuscarCamino = new JButton("Buscar Camino");
        this.btnBuscarCamino.addActionListener(
                e -> { 
                	if(txtLongitudCamino.getText().isEmpty()) {
            				JOptionPane.showMessageDialog(this, "No ha ingresado los saltos del camino.");
                	}
                    Integer n =Integer.parseInt(txtLongitudCamino.getText());
                    if(n == 0) {
                    	JOptionPane.showMessageDialog(this, "Ha ingresado el valor 0.");
                    }
                    Integer idOrigen = this.listaVertices.get(cmbVertice1.getSelectedIndex()).getId();
                    Integer idDestino= this.listaVertices.get(cmbVertice2.getSelectedIndex()).getId();
                    controller.buscarCamino(idOrigen,idDestino,n); 
                }
        );
        this.btnCaminosDisponibles = new JButton("Buscar Caminos Posibles"); 
        this.btnCaminosDisponibles.addActionListener(
                e -> { 
                	JFrame frame = new JFrame("Caminos Posibles");
                	JTextArea texto = new JTextArea();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(baos);
                    PrintStream old = System.out;
                    System.setOut(ps);
                	Integer idOrigen = this.listaVertices.get(cmbVertice1.getSelectedIndex()).getId();
                	Integer idDestino= this.listaVertices.get(cmbVertice2.getSelectedIndex()).getId();
                	controller.caminosPosibles(idOrigen,idDestino);
                    System.out.flush();
                    System.setOut(old);
                    if(!baos.toString().isEmpty()) {
            		texto.setText(baos.toString());
                    }
                    else {
                    	texto.setText("No hay caminos posibles de "+controller.getMaterialDao().findById(idOrigen).getTitulo()+" a "
                    			+controller.getMaterialDao().findById(idDestino).getTitulo());
                    }
            		System.out.flush();
            		texto.setEditable(false);
            		gridConst.gridx=0;
            		gridConst.gridwidth=7;	
            		gridConst.gridy=4;
            		gridConst.weighty=1.0;
            		gridConst.weightx=1.0;
            		gridConst.fill=GridBagConstraints.BOTH;
            		gridConst.anchor=GridBagConstraints.PAGE_START;
            		frame.add(texto);
                    frame.pack();
                    frame.setSize(800,600);
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                    frame.setResizable(false);
                }
        );
        
        gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=1;
    	gridConst.anchor=GridBagConstraints.WEST;
        this.add(new JLabel("Origen"),gridConst); 
        gridConst.gridx=0;
    	gridConst.gridwidth=0;	
    	gridConst.gridy=2;
    	gridConst.anchor=GridBagConstraints.EAST;
    	this.add(cmbVertice1,gridConst);
    	gridConst.gridx=0;
      	gridConst.gridwidth=0;	
      	gridConst.gridy=3;
      	gridConst.anchor=GridBagConstraints.WEST;
    	this.add(new JLabel("Destino"),gridConst);
    	gridConst.gridx=0;
     	gridConst.gridwidth=0;	
     	gridConst.gridy=4;
     	gridConst.anchor=GridBagConstraints.EAST;
    	this.add(cmbVertice2,gridConst);
    	gridConst.gridx=0;
      	gridConst.gridwidth=0;	
      	gridConst.gridy=5;
      	gridConst.anchor=GridBagConstraints.WEST;
    	this.add(new JLabel("Cantidad de saltos"),gridConst);     
    	gridConst.gridx=1;
      	gridConst.gridwidth=0;	
      	gridConst.gridy=5;
      	gridConst.anchor=GridBagConstraints.EAST;
    	this.add(txtLongitudCamino,gridConst);   
    	gridConst.gridx=0;
      	gridConst.gridwidth=0;	
      	gridConst.gridy=6;
      	gridConst.anchor=GridBagConstraints.WEST;
    	this.add(btnBuscarCamino,gridConst);
    	gridConst.gridx=0;
      	gridConst.gridwidth=0;	
      	gridConst.gridy=7;
      	gridConst.anchor=GridBagConstraints.WEST;
    	this.add(btnCaminosDisponibles,gridConst);
    	this.mostrarPageRank = new JButton("Mostrar Page Rank");
    	 this.mostrarPageRank.addActionListener(
                 e -> { 
                	JFrame frame = new JFrame("Page Rank");
                	JPanel panel = new JPanel();
            		this.setListaMateriales(this.getController().getMaterialDao().pageRank(listaVertices), true);
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
            		panel.add(scrollPane, gridConst);
            		frame.add(panel);
                    frame.pack();
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                    frame.setResizable(false);
                 }
         );
    	gridConst.gridx=0;
     	gridConst.gridwidth=0;	
     	gridConst.gridy=8;
     	gridConst.anchor=GridBagConstraints.WEST;
    	this.add(mostrarPageRank,gridConst);
    }

    public GrafoController getController() {
        return controller;
    }

    public void setController(GrafoController controller) {
        this.controller = controller;
    }

	public void setListaMateriales(List<MaterialCapacitacion> materiales,boolean actualizar) {
		this.tableModel.setMateriales(materiales);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}

    
}
