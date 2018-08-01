package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import frsf.isi.died.app.controller.BusquedaController;
import frsf.isi.died.app.controller.WishListController;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class WishListPanel extends JPanel{
	private MaterialTableModel tableModel;
	private WishListController controller;
	private JTable tabla;
	private JScrollPane scrollPane;
	
	public WishListPanel() {
		this.setLayout(new GridBagLayout());
		tableModel = new MaterialTableModel();
	}
	
	public WishListController getController() {
		return controller;
	}

	public void setController(WishListController controller) {
		this.controller = controller;
	}
	
	public void setListaMateriales(List<MaterialCapacitacion> materiales,boolean actualizar) {
		this.tableModel.setMateriales(materiales);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}

	public void construir() {
		GridBagConstraints gridConst= new GridBagConstraints();
		
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

}
