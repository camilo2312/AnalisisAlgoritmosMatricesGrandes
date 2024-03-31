package model;

import java.util.ArrayList;


/*
 * Clase que permite almacenar el algoritmo ejecutado con sus respectivos tiempos
 * */
public class Algorithm {
	private int id;
	private String name;
	private Double averageTime;
	private Double rankTime;
	private Double standarDeviationTime;
	private Double varianceTime;
	private ArrayList<Time> lstTimes = new ArrayList<>();

	/*
	 * Constructor de la clase
	 * */
	public Algorithm(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/*
	 * Getters y setters de las propiedades
	 * */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Time> getLstTimes() {
		return lstTimes;
	}

	public void setLstTimes(ArrayList<Time> lstTimes) {
		this.lstTimes = lstTimes;
	}


	public Double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
	}

	public Double getRankTime() {
		return rankTime;
	}

	public void setRankTime(Double rankTime) {
		this.rankTime = rankTime;
	}

	public Double getStandarDeviationTime() {
		return standarDeviationTime;
	}

	public void setStandarDeviationTime(Double standarDeviationTime) {
		this.standarDeviationTime = standarDeviationTime;
	}

	public Double getVarianceTime() {
		return varianceTime;
	}

	public void setVarianceTime(Double varianceTime) {
		this.varianceTime = varianceTime;
	}

	/**
	 * Método que calcula los datos estadisticos
	 * de un algoritmo especifico utilizando los tiempos de ejecución
	 * del mismo
	 */
	public void calculateData() {
		getAverage();
		getRank();
		getStandarDeviation();
		getVariance();
	}

	/**
	 * Método que permite obtener el promedio de los tiempos
	 * de ejecución de un algoritmo especifico
	 */
	private void getAverage() {
		Double sum = 0.0;
		double average = 0.0;

		// Linea que suma los tiempos de ejecución del algoritmo
		for(Time time : lstTimes) {
			sum += time.getTime();
		}

		// Línea que calcula el promedio
		average = sum / lstTimes.size();

		// Se guarda el promedio en la variable
		this.averageTime = average;
	}

	/**
	 * Metodo para calcular el rango de los tiempos
	 * de ejecución de un algoritmo especifico
	 */
	private void getRank() {
        long max = lstTimes.get(0).getTime();
        long min = lstTimes.get(0).getTime();

        for (Time time : lstTimes) {
        	Long timeValue = time.getTime();

            if (timeValue > max) {
                max = timeValue;
            }

            if (timeValue < min) {
                min = timeValue;
            }
        }

        Double rank = (double) (max - min);
        this.rankTime = rank;
	}

	/**
	 * Metodo para calcular la desviacion estandar de la lista de tiempos de ejecucion
	 * de cada algoritmo
	 */
	private void getStandarDeviation() {
		double average = this.averageTime;
        double totalSum = 0.0;

        for(Time time : lstTimes) {
        	totalSum += Math.pow(time.getTime() - average, 2);
        }

        Double standarDeviation = Math.sqrt(totalSum / this.lstTimes.size());
        this.standarDeviationTime = standarDeviation;
	}

	/**
	 * Metodo para calcular la varianza de la lista de tiempos de ejecucion de
	 * cada algoritmo
	 */
	private void getVariance() {
		double average = this.averageTime;
        double totalSum = 0.0;

        for (Time time : lstTimes) {
            totalSum += Math.pow(time.getTime() - average, 2);
        }

        Double varianza = totalSum / this.lstTimes.size();
        this.varianceTime = varianza;
	}

}
