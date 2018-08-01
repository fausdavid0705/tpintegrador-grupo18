package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import frsf.isi.died.app.controller.ArbolController;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class ArbolPanel {
	JFrame frame;
	JPanel panel;
	JTextArea texto;
	JComboBox<TipoNodo> cmbContenidos; 
	JTextField txtContenido;
	GridBagConstraints gridConst;
    JButton btnAgregarContenido;
    JScrollPane  treeView;
    ArbolController arbolControl;
	private AbstractButton btnBorrarContenido;
	
	public ArbolPanel(ArbolController a) {
		arbolControl = a;
	}
	
	public void construir(JTree tree) {	
		frame = new JFrame("Arbol de Contenidos");
		panel = new JPanel();
		texto = new JTextArea();
		txtContenido = new JTextField();
		gridConst= new GridBagConstraints();
    	btnAgregarContenido = new JButton("Agregar Contenido");
    	cmbContenidos = new JComboBox<TipoNodo>();
    	cmbContenidos.addItem(TipoNodo.AUTOR);
    	cmbContenidos.addItem(TipoNodo.CAPITULO);
    	cmbContenidos.addItem(TipoNodo.EDITORIAL);
    	cmbContenidos.addItem(TipoNodo.METADATO);
    	cmbContenidos.addItem(TipoNodo.PALABRA_CLAVE);
    	cmbContenidos.addItem(TipoNodo.PARRAFO);
    	cmbContenidos.addItem(TipoNodo.RESUMEN);
    	cmbContenidos.addItem(TipoNodo.SECCION);

    	treeView = new JScrollPane(tree);
	
    	panel.setLayout(new GridBagLayout());   
    	
    	gridConst.gridx=0;
    	gridConst.gridwidth=7;	
    	gridConst.gridy=2;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Contenidos:"),gridConst);
		
    	gridConst.gridx=0;
    	gridConst.gridwidth=7;	
    	gridConst.gridy=3;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(cmbContenidos, gridConst);
	
    	gridConst.gridx=0;
    	gridConst.gridwidth=7;	
    	gridConst.gridy=4;
    	gridConst.anchor=GridBagConstraints.WEST;
    	panel.add(new JLabel("Texto:"),gridConst);  
	
    	gridConst.gridx=0;
    	gridConst.gridwidth=7;	
    	gridConst.gridy=5;
    	gridConst.anchor=GridBagConstraints.WEST;
    	txtContenido.setColumns(30);
    	panel.add(txtContenido, gridConst);
	
    	gridConst.gridx=0;
    	gridConst.gridwidth=7;	
    	gridConst.gridy=6;
    	gridConst.fill = GridBagConstraints.BOTH;
    	gridConst.anchor=GridBagConstraints.EAST;

    	btnAgregarContenido.addActionListener( e ->{			
    		TreeSelectionModel smd = tree.getSelectionModel();
    		if(smd.getSelectionCount() > 0){
    			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
    			String output = cmbContenidos.getSelectedItem().toString().substring(0, 1).toUpperCase() + cmbContenidos.getSelectedItem().toString().substring(1).toLowerCase();
    			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(output+": "+txtContenido.getText());
    			arbolControl.agregarNodo(selectedNode.toString(),(TipoNodo) cmbContenidos.getSelectedItem(),txtContenido.getText());
    			if(selectedNode.toString().contains("Titulo")){
    				if(newNode.toString().contains("Metadato")) {
    					selectedNode.add(newNode);
    					newNode.setAllowsChildren(true);
    				}
    				else if(newNode.toString().contains("Resumen")) {
    					selectedNode.add(newNode);
    					newNode.setAllowsChildren(true);
    				}
    				else if(newNode.toString().contains("Capitulo")) {
    					selectedNode.add(newNode);
    					newNode.setAllowsChildren(true);
    				}
    			}
    			else if(selectedNode.toString().contains("Metadato")){
    				if(!txtContenido.getText().isEmpty()) {
    					if(newNode.toString().contains("Autor")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(false);
    					}
    					else if(newNode.toString().contains("Editorial")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(false);
    					}
    					else if(newNode.toString().contains("Fecha Publicacion")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(false);
    					}
    					else if(newNode.toString().contains("Palabra_clave")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(false);
    					}
    				}
    			}
    			else if(selectedNode.toString().contains("Resumen")) {
    				if(!txtContenido.getText().isEmpty()) {
    					if(newNode.toString().contains("Parrafo")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(true);
    					}
    				}
    			}
    			else if(selectedNode.toString().contains("Capitulo")) {
    				if(!txtContenido.getText().isEmpty()) {
    					if(newNode.toString().contains("Seccion")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(false);
    					}
    					if(newNode.toString().contains("Parrafo")) {
    						selectedNode.add(newNode);
    						newNode.setAllowsChildren(false);
    					}
    				}
    			}
    			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
    			model.reload();
    		}  
    	});
    	panel.add(btnAgregarContenido,gridConst);
    	
    	gridConst.gridx=0;
    	gridConst.gridwidth=7;	
    	gridConst.gridy=7;
    	gridConst.anchor=GridBagConstraints.WEST;	
    	panel.add((new JLabel("Árbol de Contenido: ")),gridConst);
	
    	gridConst.gridx=0;
    	gridConst.gridwidth=10;
    	gridConst.gridheight=10;
    	gridConst.gridy=8;
    	gridConst.fill=GridBagConstraints.BOTH;	
    	gridConst.anchor=GridBagConstraints.PAGE_START;	
    	panel.add(treeView, gridConst);

    	frame.add(panel);
    	frame.pack();
    	frame.setLocationByPlatform(true);
    	frame.setVisible(true);
    	frame.setResizable(false);
	}
}
