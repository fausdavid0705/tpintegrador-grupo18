
	package frsf.isi.died.app.vista.material;

	import java.util.List;

	import javax.swing.table.AbstractTableModel;

	import frsf.isi.died.tp.modelo.productos.Video;

	public class VideoTableModel extends AbstractTableModel {

		private List<Video> videos;
		private String[] columnas = {"ID","Titulo","Precio","Costo publicacion","Duracion en Segundos","Precio Suscripcion"};
		
		
		@Override
		public String getColumnName(int indice) {
			return this.columnas[indice];
		}
		
		public List<Video> getVideos() {
			return videos;
		}

		public void setVideos(List<Video> videos) {
			this.videos = videos;
		}

		@Override
		public int getRowCount() {
			return videos.size();
		}

		@Override
		public int getColumnCount() {
			return columnas.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object valor = null;
			switch (columnIndex) {
			case 0:
				valor = this.videos.get(rowIndex).getId();
				break;
			case 1:
				valor = this.videos.get(rowIndex).getTitulo();
				break;
			case 2:
				valor = this.videos.get(rowIndex).precio();
				break;
			case 3:
				valor = this.videos.get(rowIndex).getCosto();
				break;
			case 4:
				valor = this.videos.get(rowIndex).getDuracionEnSegundos();
				break;
			case 5:
				valor = this.videos.get(rowIndex).precio();
				break;
			default:
				System.out.println("Indice fuera de rango");
				valor = "S/D";
				break;
			}
			return valor;
		}

	}
