package frsf.isi.died.tp.modelo.productos;

public enum Relevancia {
	ALTA,MEDIA,BAJA;

	public static int intValue(Relevancia valor) {
		switch (valor){
			case ALTA: return 3;
			case MEDIA: return 2;
			case BAJA: return 1;
		}
		return 0;
	}

}
