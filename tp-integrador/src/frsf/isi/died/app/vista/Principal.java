package frsf.isi.died.app.vista;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import frsf.isi.died.app.controller.TiposAcciones;
import frsf.isi.died.app.controller.MenuController;

public class Principal {
	public static void main(String[] args) {
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	          createAndShowGUI();
	        }
	    });
	}

	 private static void createAndShowGUI() {
	 JFrame f = new JFrame();
		 MenuController controller = new MenuController(f);
	      	JMenuBar menuBar;
	        JMenu menu;
	        JMenuItem menuItem;
	        JLabel bienvenida;
	        bienvenida = new JLabel("¡BIENVENIDO! Utilice las pestañas Sistema y Opciones para ver las funcionalidades de la aplicación.", SwingConstants.CENTER);

	        menuBar = new JMenuBar();
	        menu = new JMenu("Sistema");
	        menuBar.add(menu);
	        
	        menuItem = new JMenuItem("Buscar Material");
	        menuItem.addActionListener(e -> controller.showView(TiposAcciones.BUSQUEDA));
	        menu.add(menuItem);
	        menu.addSeparator();
	        
	        menuItem = new JMenuItem("Libros");
	        menuItem.addActionListener(e -> controller.showView(TiposAcciones.ABM_LIBROS));
	        menu.add(menuItem);

	        menuItem = new JMenuItem("Videos");
	        menuItem.addActionListener(e -> controller.showView(TiposAcciones.ABM_VIDEOS));
	        menu.add(menuItem);
	        menu.addSeparator();
	        
	        menuItem = new JMenuItem("Salir");
	        menuItem.addActionListener(e->System.exit(99));
	        menu.add(menuItem);

	        menuBar.add(menu);
	        
	        menu = new JMenu("Opciones");
	        menuBar.add(menu);
	        
	        menuItem = new JMenuItem("Ver Wishlist");
	        menuItem.addActionListener(e -> controller.showView(TiposAcciones.VER_WISHLIST));
	        menu.add(menuItem);

	        f.add(bienvenida);
	        f.setJMenuBar(menuBar);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	        f.pack();
	        f.setVisible(true);
	    }

}
