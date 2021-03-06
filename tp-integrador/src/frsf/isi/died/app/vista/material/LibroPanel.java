package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import frsf.isi.died.app.controller.LibroController;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class LibroPanel extends JPanel{
	
	private JScrollPane scrollPane;
	private JTable tabla;
	private JLabel lblTitulo;
	private JLabel lblCosto;
	private JLabel lblPrecioCompra;
	private JLabel lblPaginas;
	private JTextField txtTitulo;
	private JTextField txtCosto;
	private JTextField txtPrecioCompra;
	private JTextField txtPaginas;
	private JButton btnAgregar;
	private JButton btnCancelar;
	/*	MODIFICACION DE LIBRO	*/
	private JLabel lblModificacion;
	private JLabel lblID;
	private JLabel lblCalificacion;
	private JLabel lblFechaPublicacion;
	private JLabel lblRelevancia;
	private JLabel lblTema;
	private JTextField txtID;
	private JTextField txtFechaPublicacion;
	private JTextField txtTema;
	private JComboBox<Relevancia> comRelevancia;
	private JSpinner spnCalificacion;
	private JButton btnModificar;
	private JButton btnEliminar;

	private LibroTableModel tableModel;

	private LibroController controller;
	
	public LibroPanel() {
		this.setLayout(new GridBagLayout());
		tableModel = new LibroTableModel();
	}
	
	public void construir() {
		GridBagConstraints gridConst= new GridBagConstraints();
		
		lblTitulo = new JLabel("Titulo: ");
		gridConst.gridx=0;
		gridConst.gridy=0;
		gridConst.anchor  = GridBagConstraints.EAST;
		this.add(lblTitulo, gridConst);
		
		txtTitulo = new JTextField();
		txtTitulo.setColumns(40);
		gridConst.gridx=1;
		gridConst.gridwidth=5;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtTitulo, gridConst);
		

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener( e ->{
			try {
				Double costo = Double.valueOf(txtCosto.getText());
				Double precio = Double.valueOf(txtPrecioCompra.getText());
				Integer paginas = Integer.valueOf(txtPaginas.getText());
				controller.agregarLibro(txtTitulo.getText(), costo, precio, paginas);
				txtTitulo.setText("");
				txtCosto.setText("");
				txtPrecioCompra.setText("");
				txtPaginas.setText("");
			}catch(Exception ex) {
			    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridwidth=1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridx=6;
		this.add(btnAgregar, gridConst);
		
		
		lblCosto= new JLabel("Costo: ");		
		gridConst.gridx=0;
		gridConst.gridy=1;
		gridConst.anchor  = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblCosto, gridConst);
		
		txtCosto = new JTextField();
		txtCosto.setColumns(5);
		gridConst.gridx=1;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtCosto, gridConst);
		
		lblPrecioCompra= new JLabel("Precio Compra: ");
		gridConst.gridx=2;
		gridConst.anchor  = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblPrecioCompra, gridConst);
		
		txtPrecioCompra = new JTextField();
		txtPrecioCompra.setColumns(5);
		gridConst.gridx=3;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtPrecioCompra, gridConst);
		
		lblPaginas= new JLabel("Paginas: ");		
		gridConst.gridx=4;
		gridConst.anchor  = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblPaginas, gridConst);
		
		txtPaginas = new JTextField();
		txtPaginas.setColumns(5);
		gridConst.gridx=5;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtPaginas, gridConst);


		btnCancelar= new JButton("Cancelar");
		gridConst.gridx=6;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnCancelar, gridConst);
		
		/** Modificacion **/
		
		lblModificacion = new JLabel("Modificacion: ");
		gridConst.gridx=0;
		gridConst.gridy=2;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblModificacion,gridConst);
		
		lblID = new JLabel("ID:");
		gridConst.gridx=0;
		gridConst.gridy=3;
		gridConst.anchor  = GridBagConstraints.EAST;
		this.add(lblID, gridConst);
		
		txtID = new JTextField();
		gridConst.gridx++;
		gridConst.anchor = GridBagConstraints.WEST;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtID, gridConst);
		
		lblCalificacion = new JLabel("Calificacion: ");
		gridConst.gridx++;
		gridConst.gridy=3;
		gridConst.anchor  = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblCalificacion, gridConst);
		
		SpinnerModel spin = new SpinnerNumberModel(100,0,100,1);//-- Valores del Spinner
		spnCalificacion = new JSpinner(spin);
		gridConst.gridx++;
		gridConst.gridy=3;
		gridConst.anchor  = GridBagConstraints.WEST;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(spnCalificacion, gridConst);
		
		lblFechaPublicacion = new JLabel("Fecha Publicacion (YYYY-MM-DD): ");
		gridConst.gridx++;
		gridConst.gridy=3;
		gridConst.anchor  = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;
		this.add(lblFechaPublicacion, gridConst);
		
		txtFechaPublicacion = new JTextField();
		gridConst.gridx++;
		gridConst.gridy=3;
		gridConst.anchor  = GridBagConstraints.WEST;
		gridConst.fill = GridBagConstraints.BOTH;
		this.add(txtFechaPublicacion, gridConst);
		
		lblRelevancia = new JLabel("Relevancia");
		gridConst.gridx=0;
		gridConst.gridy=4;
		gridConst.anchor  = GridBagConstraints.EAST;
		gridConst.fill = GridBagConstraints.NONE;		
		this.add(lblRelevancia, gridConst);
		
		comRelevancia = new JComboBox(Relevancia.values());
		gridConst.gridx++;
		gridConst.gridy=4;
		gridConst.fill = GridBagConstraints.BOTH;
		gridConst.anchor  = GridBagConstraints.CENTER;
		this.add(comRelevancia, gridConst);
		
		lblTema = new JLabel("Tema: ");
		gridConst.gridx++;
		gridConst.gridy=4;
		gridConst.fill = GridBagConstraints.NONE;
		gridConst.anchor  = GridBagConstraints.CENTER;
		this.add(lblTema, gridConst);
		
		txtTema = new JTextField();
		gridConst.gridx++;
		gridConst.gridy=4;
		gridConst.fill = GridBagConstraints.BOTH;
		gridConst.anchor  = GridBagConstraints.CENTER;
		this.add(txtTema, gridConst);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener( e ->{
		try {
			String titulo;
			String tema;
			Double costo;
			Double precio;
			Integer paginas;
			Date fecha;
			if (txtTitulo.getText().isEmpty()) titulo = "@@";
				else titulo = txtTitulo.getText();
			if (txtTema.getText().isEmpty()) tema = "@@";
				else tema = txtTema.getText();
			if (txtCosto.getText().isEmpty()) costo = -1.0;
				else costo = Double.valueOf(txtCosto.getText());
			if (txtPrecioCompra.getText().isEmpty()) precio = -1.0;
				else precio = Double.valueOf(txtPrecioCompra.getText());
			if (txtPaginas.getText().isEmpty()) paginas = -1;
				else paginas = Integer.valueOf(txtPaginas.getText());
			if (txtFechaPublicacion.getText().isEmpty()) fecha = Date.valueOf("0000-01-01");
				else fecha = Date.valueOf(txtFechaPublicacion.getText());
			Integer ID = Integer.valueOf(txtID.getText());
			Integer calificacion = Integer.valueOf(spnCalificacion.getValue().toString());
			Relevancia relev = (Relevancia) comRelevancia.getSelectedItem();
			
			controller.modificarLibro(ID,titulo,costo,precio,paginas,calificacion,tema,fecha,relev);
			
			txtTitulo.setText("");
			txtCosto.setText("");
			txtPrecioCompra.setText("");
			txtPaginas.setText("");
			txtID.setText("");
			this.tableModel.fireTableDataChanged();
			
		}catch(Exception ex) {
		    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	});
		gridConst.gridx=6;
		gridConst.gridy=3;
		gridConst.anchor  = GridBagConstraints.CENTER;
		this.add(btnModificar, gridConst);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(e -> {
			try {
				controller.eliminarLibro(Integer.valueOf(txtID.getText()));
				this.tableModel.getLibros().removeIf(a -> a.getId().intValue() == Integer.valueOf(txtID.getText()).intValue());
				this.tableModel.fireTableDataChanged();
				txtID.setText("");
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridx=6;
		gridConst.gridy=4;
		gridConst.anchor  = GridBagConstraints.CENTER;
		this.add(btnEliminar, gridConst);
		
		/** END Modificacion **/
		
		tabla = new JTable(this.tableModel);
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		
		gridConst.gridx=0;
		gridConst.gridwidth=7;	
		gridConst.gridy=5;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;		
		this.add(scrollPane, gridConst);
	}

	public LibroController getController() {
		return controller;
	}


	public void setController(LibroController controller) {
		this.controller = controller;
	}
	
	public void setListaLibros(List<Libro> librosLista,boolean actualizar) {
		this.tableModel.setLibros(librosLista);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}

}