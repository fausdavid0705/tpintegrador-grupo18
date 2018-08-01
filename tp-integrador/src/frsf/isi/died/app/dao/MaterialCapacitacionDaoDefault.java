package frsf.isi.died.app.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.sql.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import frsf.isi.died.app.dao.util.CsvDatasource;
import frsf.isi.died.app.dao.util.CsvRecord;
import frsf.isi.died.tp.estructuras.*;
import frsf.isi.died.tp.modelo.Biblioteca;
import frsf.isi.died.tp.modelo.BibliotecaABB;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Relevancia;
import frsf.isi.died.tp.modelo.productos.Video;
import frsf.isi.died.tp.util.*;

	public class MaterialCapacitacionDaoDefault implements MaterialCapacitacionDao{
	private static Grafo<MaterialCapacitacion> GRAFO_MATERIAL  = new Grafo<MaterialCapacitacion>();
	private static Integer SECUENCIA_ID=0;
	private static Biblioteca biblioteca = new BibliotecaABB();
	private static PriorityQueue<MaterialCapacitacion> wishList = new PriorityQueue<MaterialCapacitacion>(11,new PrioridadMaterial());
	private CsvDatasource dataSource;
	
	public MaterialCapacitacionDaoDefault() {
		dataSource = new CsvDatasource();
		if(GRAFO_MATERIAL.esVacio()) {
			cargarGrafo();
		}
	}
	
	private void cargarGrafo() {
		Integer LIBRO_ID,VIDEO_ID;//----------------------------------------------------
		LIBRO_ID = VIDEO_ID = 0;//------------------------------------------------------
		
		/* **** Para no comenzar con ID 0 cada vez que comienza el programa **** */

		List<List<String>> libros = dataSource.readFile("libros.csv");
		for(List<String> filaLibro : libros) {
			Libro aux = new Libro();
			aux.loadFromStringRow(filaLibro);
			aux.setRaizContenido(aux.getTitulo());
			GRAFO_MATERIAL.addNodo(aux);
			LIBRO_ID = aux.getId();//----------------------------------------------------
		}
		List<List<String>> videos= dataSource.readFile("videos.csv");
		for(List<String> filaVideo: videos) {
			Video aux = new Video();
			aux.loadFromStringRow(filaVideo);
			aux.setRaizContenido(aux.getTitulo());
			GRAFO_MATERIAL.addNodo(aux);
			VIDEO_ID = aux.getId();//---------------------------------------------------
		}
		if (LIBRO_ID > VIDEO_ID) SECUENCIA_ID = LIBRO_ID;//-----------------------------
		else SECUENCIA_ID = VIDEO_ID ; //-----------------------------------------------
		List<List<String>> aristas= dataSource.readFile("aristas.csv");
		for(List<String> filaArista: aristas) {
			MaterialCapacitacion n1 = this.findById(Integer.valueOf(filaArista.get(0)));
			MaterialCapacitacion n2 = this.findById(Integer.valueOf(filaArista.get(2)));
			GRAFO_MATERIAL.conectar(n1, n2);
		}
 	}
	
	@Override
	public void agregarLibro(Libro mat) {
		mat.setId(++SECUENCIA_ID);
		GRAFO_MATERIAL.addNodo(mat);	
		biblioteca.agregar(mat);
		try {
			dataSource.agregarFilaAlFinal("libros.csv", mat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void agregarVideo(Video mat) {
		mat.setId(++SECUENCIA_ID);
		GRAFO_MATERIAL.addNodo(mat);				
		biblioteca.agregar(mat);
		try {
			dataSource.agregarFilaAlFinal("videos.csv", mat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Libro> listaLibros() {
		List<Libro> libros = new ArrayList<>();
		for(MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.esLibro()) libros.add((Libro)mat); 
		}
		return libros;
	}

	@Override
	public List<Video> listaVideos() {
		List<Video> vids = new ArrayList<>();
		for(MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.esVideo()) vids.add((Video)mat); 
		}
		return vids;
	}

	@Override
	public List<MaterialCapacitacion> listaMateriales() {
		return GRAFO_MATERIAL.listaVertices();
	}

	@Override
	public MaterialCapacitacion findById(Integer id) {
		for(MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getId().equals(id)) return mat;
		}
		return null;
	}

	@Override
	public List<MaterialCapacitacion> findByTitulo(String titulo){
		List<MaterialCapacitacion> materiales = new ArrayList<MaterialCapacitacion>();
		for (MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getTitulo().equals(titulo)) materiales.add(mat);
		}
		return materiales;
	}
	
	@Override
	public List<MaterialCapacitacion> findByContenido(String valor){
		List<MaterialCapacitacion> materiales = new ArrayList<MaterialCapacitacion>();
		for (MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getContenido().getValor().equals(valor)) {
				materiales.add(mat);
			}
			for (Nodo n : mat.getContenido().getHijos()) {
				if(n.getValor().equals(valor)) { 
					materiales.add(mat);
				}
				else {
					for (Nodo n2 : n.getHijos()) {
						if(n2.getValor().equals(valor)) { 
						materiales.add(mat);
						}
						}
				}
			}
		}
		return materiales;
	}
		
		
	
	@Override
	public List<MaterialCapacitacion> findByCalificacion(Integer calificacion){
		List<MaterialCapacitacion> materiales = new ArrayList<MaterialCapacitacion>();
		for (MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getCalificacion().equals(calificacion)) materiales.add(mat);
		}
		return materiales;
	}
	
	@Override
	public List<MaterialCapacitacion> findByTema(String tema){
		List<MaterialCapacitacion> materiales = new ArrayList<MaterialCapacitacion>();
		for (MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getTema().equals(tema)) materiales.add(mat);
		}
		return materiales;
	}
	
	@Override
	public List<MaterialCapacitacion> findByFecha(Date desde, Date hasta){
		List<MaterialCapacitacion> materiales = new ArrayList<MaterialCapacitacion>();
		if(!hasta.equals(Date.valueOf("0001-01-01"))) {
		for (MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getFechaPublicacion().before(hasta) && mat.getFechaPublicacion().after(desde) || mat.getFechaPublicacion().equals(desde) || mat.getFechaPublicacion().equals(hasta)) materiales.add(mat);
		}
		}
		else {
			for (MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
				if(mat.getFechaPublicacion().after(desde) || mat.getFechaPublicacion().equals(desde))  
					materiales.add(mat);
			}
		}
		return materiales;
	}

	@Override
	public List<MaterialCapacitacion> buscarCamino(Integer idOrigen, Integer idDestino, Integer saltos) {
		MaterialCapacitacion n1 = this.findById(idOrigen);
		MaterialCapacitacion n2 = this.findById(idDestino);
		return GRAFO_MATERIAL.buscarCaminoNSaltos(n1, n2, saltos);
	}
	
	@Override
	/*public List<MaterialCapacitacion> caminosPosibles(Integer nodo1, Integer nodo2){
		MaterialCapacitacion m1 = this.findById(nodo1);
		MaterialCapacitacion m2 = this.findById(nodo2);
		return GRAFO_MATERIAL.caminosPosibles(m1, m2);
		
	}*/
	public ArrayList<List<MaterialCapacitacion>> caminosPosibles(Integer nodo1, Integer nodo2){
		MaterialCapacitacion m1 = this.findById(nodo1);
		MaterialCapacitacion m2 = this.findById(nodo2);
		ArrayList<List<MaterialCapacitacion>> caminosPosibles = GRAFO_MATERIAL.caminosPosibles(m1, m2);
		for (List<MaterialCapacitacion> camino : caminosPosibles) {
			if (!camino.isEmpty()) {System.out.println("Camino "+(caminosPosibles.indexOf(camino)+1)+":");
			for (MaterialCapacitacion mat : camino) System.out.println(mat.toString());
			}
			
		}
		return caminosPosibles;
	}
	
	@Override
	public void crearCamino(Integer idOrigen, Integer idDestino) {
		MaterialCapacitacion n1 = this.findById(idOrigen);
		MaterialCapacitacion n2 = this.findById(idDestino);
		GRAFO_MATERIAL.conectar(n1, n2);
		List<String> fila = new ArrayList<>();
		fila.add(n1.getId()+"");
		fila.add(n1.getTitulo());
		fila.add(n2.getId()+"");
		fila.add(n2.getTitulo());
		try {
			dataSource.agregarFilaAlFinal("aristas.csv", fila);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarLibro(Libro mat) {
		try {
			dataSource.vaciarCsv("libros.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Libro l : this.listaLibros()) {
			try {
				if (mat.getId().intValue() != l.getId().intValue())dataSource.agregarFilaAlFinal("libros.csv", l.asCsvRow());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GRAFO_MATERIAL  = new Grafo<MaterialCapacitacion>();
		this.cargarGrafo();
	}

	@Override
	public void eliminarVideo(Video mat) {
		try {
			dataSource.vaciarCsv("videos.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Video l : this.listaVideos()) {
			try {
				if (mat.getId().intValue() != l.getId().intValue())dataSource.agregarFilaAlFinal("videos.csv", l.asCsvRow());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GRAFO_MATERIAL  = new Grafo<MaterialCapacitacion>();
		this.cargarGrafo();
	}

	@Override
	public void modificarLibro(Integer ID, String titulo, Double costo, Double precio, Integer paginas, Integer calificacion, String tema, Date fecha, Relevancia relev) {
		List<MaterialCapacitacion> materiales = GRAFO_MATERIAL.listaVertices();
		for (MaterialCapacitacion mat: materiales) {
			if (mat.getId().intValue() == ID.intValue()) {
				Libro aux = (Libro) mat;
				if ( titulo != "@@" ) aux.setTitulo(titulo);
				if ( tema != "@@" ) aux.setTema(tema);
				if ( costo != -1.0 ) aux.setCosto(costo);
				if ( precio != -1.0 ) aux.setPrecioCompra(precio);
				if ( paginas != -1 ) aux.setPaginas(paginas);
				if ( calificacion != null ) aux.setCalificacion(calificacion);
				if ( !fecha.toString().equals("0001-01-01")) aux.setFechaPublicacion(fecha);
				if ( relev != null ) aux.setRelevancia(relev);
				break;
			}
		}
		try {
			dataSource.vaciarCsv("libros.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Libro l : this.listaLibros()) {
			try {
				dataSource.agregarFilaAlFinal("libros.csv", l.asCsvRow());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void modificarVideo(Integer ID, String titulo, Double costo, Double precio, Integer durac, Integer calificacion, String tema, Date fecha, Relevancia relev) {
		List<MaterialCapacitacion> materiales = GRAFO_MATERIAL.listaVertices();
		for (MaterialCapacitacion mat: materiales) {
			if (mat.getId().intValue() == ID.intValue()) {
				Video aux = (Video) mat;
				if ( titulo != "@@" ) aux.setTitulo(titulo);
				if ( tema != "@@" ) aux.setTema(tema);
				if ( costo != -1.0 ) aux.setCosto(costo);
				//if ( precio != null ) aux.setPrecioCompra(precio);
				if ( durac != -1 ) aux.setDuracionEnSegundos(durac);;
				if ( calificacion != null ) aux.setCalificacion(calificacion);
				if ( !fecha.toString().equals("0001-01-01")) aux.setFechaPublicacion(fecha);
				if ( relev != null ) aux.setRelevancia(relev);
				break;
			}
		}
		try {
			dataSource.vaciarCsv("videos.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Video l : this.listaVideos()) {
			try {
				dataSource.agregarFilaAlFinal("videos.csv", l.asCsvRow());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public PriorityQueue<MaterialCapacitacion> getWishList() {
		return MaterialCapacitacionDaoDefault.wishList;
	}
	
	@Override
	public ArrayList<MaterialCapacitacion> getWishListAsList() {
		ArrayList<MaterialCapacitacion> lista = new ArrayList<MaterialCapacitacion>();
		lista.addAll(wishList);
		return lista;
	}

	@Override
	public Grafo<MaterialCapacitacion> getGrafo(){
		return GRAFO_MATERIAL;
	}
	
	@Override
	public List<MaterialCapacitacion> pageRank(List<MaterialCapacitacion> relacionadosPorTema){
		List<MaterialCapacitacion> auxOrdenados = new ArrayList<MaterialCapacitacion>();
		this.calculaPageRank(relacionadosPorTema);
		auxOrdenados.addAll(relacionadosPorTema);
		auxOrdenados.sort((mc1,mc2)-> mc2.getPageRank().compareTo(mc1.getPageRank()));
		return auxOrdenados;
		
	}
	
	private void calculaPageRank(List<MaterialCapacitacion> listaMateriales) {
		Double d = 0.5;
		Double sum = 0.0;
		Double pr = 0.0;
		listaMateriales.stream().forEach((m) ->{
			m.setPageRank(0.0);
		});
		for(MaterialCapacitacion mat : listaMateriales) {
			sum = 0.0;
			for(int i=1;i<=GRAFO_MATERIAL.getAdyacentes(mat).size();i++) {
				if(GRAFO_MATERIAL.gradoSalida(listaMateriales.get(i)) != 0){
				sum=sum+((listaMateriales.get(i).getPageRank())/GRAFO_MATERIAL.gradoSalida(listaMateriales.get(i)));
				}
				else {
					sum = sum + 0;
				}
				
			}
			pr=(1-d)+(d*sum);
			mat.setPageRank(pr);
		}
	}

}
