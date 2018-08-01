
	package frsf.isi.died.app.controller;

	import java.sql.Date;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
	import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
	import frsf.isi.died.app.vista.material.VideoPanel;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Relevancia;
import frsf.isi.died.tp.modelo.productos.Video;

	public class VideoController {

		private VideoPanel panelVideo;
		private MaterialCapacitacionDao materialDAO;
		
		public VideoController(VideoPanel panel) {
			this.panelVideo = panel;
			this.panelVideo.setController(this);
			materialDAO = new MaterialCapacitacionDaoDefault();
		}

		
		public void agregarVideo(String titulo,Double costo,Integer duracion) {	
			Video v = new Video(0,titulo, costo, duracion) ;
			materialDAO .agregarVideo(v);
			this.panelVideo.setListaVideos(materialDAO.listaVideos(),true);
		}
		
		public void crearPanel() {		
			this.panelVideo.setListaVideos(materialDAO.listaVideos(),false);
			this.panelVideo.construir();
		}

		public VideoPanel getVideoPanel() {
			return panelVideo;
		}

		public void setPanelVideo(VideoPanel panelVideo) {
			this.panelVideo = panelVideo;
		}


		public void eliminarVideo(Integer id) {
			if (materialDAO.findById(id).esVideo())
				materialDAO.eliminarVideo((Video)materialDAO.findById(id));	
			
		}


		public void modificarVideo(Integer ID, String titulo, Double costo, Double precio, Integer durac,
				Integer calificacion, String tema, Date fecha, Relevancia relev) {
			materialDAO.modificarVideo(ID, titulo, costo, precio, durac, calificacion, tema, fecha, relev);
			
		}
		
		
	}
