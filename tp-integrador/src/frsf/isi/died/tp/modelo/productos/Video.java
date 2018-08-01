/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsf.isi.died.tp.modelo.productos;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author mdominguez
 */
public class Video extends MaterialCapacitacion {

    private Integer duracionEnSegundos;
    private static final Double _VALOR_SEGUNDO = 0.15;

    public Video() {
        super();
    }

    public Video(Integer id,String titulo){
        super(id,titulo);
        this.duracionEnSegundos=0;
		this.tema = "";
    }
    
    public Video(Integer id,String titulo, Double costo) {
        super(id,titulo, costo);
        this.duracionEnSegundos = 0;
		this.tema = "";
    }

    public Video(Integer id,String titulo, Double costo, Integer duracion) {
        super(id,titulo, costo);
        this.duracionEnSegundos = duracion;
		this.tema = "";
    }

    @Override
    public Double precio() {
        return costo + (duracionEnSegundos * _VALOR_SEGUNDO);
    }

    public Integer getDuracionEnSegundos() {
        return duracionEnSegundos;
    }

    public void setDuracionEnSegundos(Integer duracionEnSegundos) {
        this.duracionEnSegundos = duracionEnSegundos;
    }

	@Override
	public Boolean esLibro() {
		return false;
	}

	@Override
	public Boolean esVideo() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Video && super.equals(obj) ;
	}
	
	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.id.toString());
//		lista.add("\""+this.titulo.toString()+"\"");
		lista.add(this.titulo.toString());
		lista.add(this.costo.toString());
		lista.add(this.duracionEnSegundos.toString());
		//**----------------------------------------------------------
		lista.add(this.calificacion.toString());
		switch (this.relevancia) {
		case ALTA:{
			lista.add("ALTA");
			break;
		}	
		case MEDIA:{
			lista.add("MEDIA");
			break;
		}
		case BAJA:{
			lista.add("BAJA");
			break;
		}
		default: lista.add("BAJA");
		break;
	}
		if (this.getFechaPublicacion() == null)
		lista.add(Date.valueOf(LocalDate.now()).toString());
		else lista.add(this.fechaPublicacion.toString());
		lista.add(this.tema);
		return lista;
	}
	

	@Override
	public void loadFromStringRow(List<String> datos) {
		this.id =Integer.valueOf(datos.get(0));
		this.titulo = datos.get(1);
		this.costo =Double.valueOf(datos.get(2));
		this.duracionEnSegundos =Integer.valueOf(datos.get(3));
		//*----------------------------------------------------------
		this.calificacion = Integer.valueOf(datos.get(4));
		this.relevancia = Relevancia.valueOf(datos.get(5));
		this.fechaPublicacion = Date.valueOf(datos.get(6));
		this.tema = datos.get(7);
	}


}