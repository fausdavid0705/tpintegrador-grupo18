package frsf.isi.died.app.dao;

import java.awt.Container;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import frsf.isi.died.tp.estructuras.*;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Relevancia;
import frsf.isi.died.tp.modelo.productos.Video;

public interface MaterialCapacitacionDao {

	public void agregarLibro(Libro mat);
	public void agregarVideo(Video mat);
	public void eliminarLibro(Libro mat);
	public void eliminarVideo(Video mat);
	public void modificarLibro(Integer ID, String titulo, Double costo, Double precio, Integer paginas, Integer calificacion, String tema, Date fecha, Relevancia relev);
	public void modificarVideo(Integer ID, String titulo, Double costo, Double precio, Integer durac, Integer calificacion, String tema, Date fecha, Relevancia relev);
	public List<Libro> listaLibros();
	public List<Video> listaVideos();
	public List<MaterialCapacitacion> listaMateriales();
	public MaterialCapacitacion findById(Integer id);
	public void crearCamino(Integer idOrigen, Integer idDestino);
	public List<MaterialCapacitacion> buscarCamino(Integer idOrigen, Integer idDestino, Integer saltos);
	List<MaterialCapacitacion> findByTitulo(String titulo);
	List<MaterialCapacitacion> findByCalificacion(Integer calificacion);
	List<MaterialCapacitacion> findByFecha(Date desde, Date hasta);
	List<MaterialCapacitacion> findByTema(String tema);
	public ArrayList<MaterialCapacitacion> getWishListAsList();
	public PriorityQueue<MaterialCapacitacion> getWishList();
	public ArrayList<List<MaterialCapacitacion>> caminosPosibles(Integer nodo1, Integer nodo2);
	Grafo<MaterialCapacitacion> getGrafo();
	List<MaterialCapacitacion> pageRank(List<MaterialCapacitacion> relacionadosPorTema);
	List<MaterialCapacitacion> findByContenido(String titulo);
	
}
