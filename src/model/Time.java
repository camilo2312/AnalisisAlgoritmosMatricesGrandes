package model;

/*
 * Clase que permite almacenar los tiempos de cada tamaño de matriz
 * */
public class Time {
	private Integer sizeMatrix;
	private Long time;

	/*
	 * Constructor de la clase
	 * */
	public Time(Integer sizeMatrix, Long time) {
		super();
		this.sizeMatrix = sizeMatrix;
		this.time = time;
	}
	/*
	 * Getters y setters de las propiedades
	 * */
	public Integer getSizeMatrix() {
		return sizeMatrix;
	}
	public void setSizeMatrix(Integer sizeMatrix) {
		this.sizeMatrix = sizeMatrix;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}


}
