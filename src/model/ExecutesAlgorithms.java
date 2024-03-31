package model;

import java.lang.reflect.Method;
import java.util.ArrayList;

import utilities.AdminFiles;
import utilities.MatrixAlgorithms;

/*
 * Clase que permite ejecutar los algoritmos
 * esta clase es la principal, puest¿o que será la encargada
 * de ejecutar los algoritmos de multiplicación de matrices, además
 * de guardar los resultados de tiempo de ejecución de los mismos
 * */
public class ExecutesAlgorithms {

	private ArrayList<Algorithm> lstAlgorithms = new ArrayList<>();
	private ArrayList<AlgorithmTable> lstAlgorithmsTable = new ArrayList<>();

	public ArrayList<Algorithm> getLstAlgorithms() {
		return lstAlgorithms;
	}

	public void setLstAlgorithms(ArrayList<Algorithm> lstAlgorithms) {
		this.lstAlgorithms = lstAlgorithms;
	}

	public ArrayList<AlgorithmTable> getLstAlgorithmsTable() {
		return lstAlgorithmsTable;
	}

	public void setLstAlgorithmsTable(ArrayList<AlgorithmTable> lstAlgorithmsTable) {
		this.lstAlgorithmsTable = lstAlgorithmsTable;
	}

	/**
	 * Método que permite registrar los algoritmos de la aplicación
	 * al momento de iniciar el formulario principal
	 */
	public void registerAlgorithms() {
		lstAlgorithms.add(new Algorithm(1, "NaivOnArray"));
		lstAlgorithms.add(new Algorithm(2, "NaivLoopUnrollingTwo"));
		lstAlgorithms.add(new Algorithm(3, "NaivLoopUnrollingFour"));
		lstAlgorithms.add(new Algorithm(4, "WinogradOriginal"));
		lstAlgorithms.add(new Algorithm(5, "WinogradScaled"));
		lstAlgorithms.add(new Algorithm(6, "StrassenNaiv"));
		lstAlgorithms.add(new Algorithm(7, "StrassenWinograd"));
		lstAlgorithms.add(new Algorithm(8, "IIISequentialBlock"));
		lstAlgorithms.add(new Algorithm(9, "IIIParallelBlock"));
		lstAlgorithms.add(new Algorithm(10, "IIIEnhancedParallelBlock"));
		lstAlgorithms.add(new Algorithm(11, "IVSequentialBlock"));
		lstAlgorithms.add(new Algorithm(12, "IIIParallelBlock"));
		lstAlgorithms.add(new Algorithm(13, "IVEnhancedParallelBlock"));
		lstAlgorithms.add(new Algorithm(14, "VSequentialBlock"));
		lstAlgorithms.add(new Algorithm(15, "VParallelBlock"));
	}

	/*
	 * Método que ejecuta todos los algoritmos por cada matriz
	 * */
	public void executeAlgorithms() {

		for (int i = 0; i < 15; i++) {
			ArrayList<Time> lstTimes = new ArrayList<Time>();
			int n = 4;
			int timeNumber = 0;

			refreshValuesTable(i);

			for (int j = 0; j < 8; j++) {

				// Variables para calcular el tiempo de ejecución de cada algoritmo
				Long startTime = (long)0;
				Long endTime = (long)0;
				String pathFile = getPathFileMatrix(n);

				int[][] firstMatrix = AdminFiles.readMatrixFile(pathFile);
				int[][] secondMatrix = firstMatrix;

				// Matriz que almacena el resultado de la multiplicación
				int[][] resultMatrix = new int[n][n];

				// Se obtiene el nombre del método basado en la lista creada
				// anteriormente
				String methodName = lstAlgorithms.get(i).getName();
				System.out.println("Algoritmo " + methodName);

				Method method;

				try {

					/*
					 * Se obtiene el método a ejecutar de la clase ExecutesAlgorithms utilizando
					 * el nombre del método
					 * */
					method = MatrixAlgorithms.class.getMethod(methodName, int[][].class, int[][].class, int[][].class, int.class, int.class, int.class);

					startTime = System.nanoTime();
			        method.invoke(null, firstMatrix, secondMatrix, resultMatrix, n, n, n);
			        endTime = System.nanoTime();

				} catch (Exception e) {
					e.printStackTrace();
				}

				// se calcula el tiempo de ejecución total del algortimo
				Long totalTime = endTime - startTime;

				Time time = new Time(n, totalTime);
				lstTimes.add(timeNumber, time);
				System.out.println("Termino ejecución, agregado a la lista, tamaño: " + n);

				timeNumber++;
				n = n * 2;
			}

			lstAlgorithms.get(i).setLstTimes(lstTimes);
			lstAlgorithmsTable.add(new AlgorithmTable(lstAlgorithms.get(i)));
		}

	}

	/*
	 * Método encargado de generar la ruta de cada archivo de matriz
	 * */
	private String getPathFileMatrix(int n) {
		return "./src/files/matriz" + n + "x" + n + ".txt";
	}


	/*
	 * Método que permite actualizar los valores de la tabla
	 * */
	private void refreshValuesTable(int numeroAlgoritmo) {
		/*
		 * Se valida si se dio click en el botón de ejecutar algoritmos
		 * y se actualizan los tiempos de ejecución
		 * */
		if (numeroAlgoritmo == 0 && lstAlgorithmsTable.size() == 15) {
			lstAlgorithms.get(numeroAlgoritmo).setLstTimes(new ArrayList<Time>());;
			lstAlgorithmsTable.clear();
		}
	}

	/**
	 * Método que calcula los datos para las estadisticas
	 * de todos los algoritmos1
	 */
	public void calculateData() {
		for(Algorithm algoritm : lstAlgorithms) {
			if (!algoritm.getLstTimes().isEmpty()) {
				algoritm.calculateData();
			}
		}
	}

}
