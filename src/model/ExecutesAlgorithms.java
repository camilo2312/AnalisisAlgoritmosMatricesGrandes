package model;

import java.lang.reflect.Method;
import java.util.ArrayList;

import utilities.AdminFiles;
import utilities.MatrixAlgorithms;

/*
 * Clase que permite ejecutar los algoritmos
 * esta clase es la principal, puest�o que ser� la encargada
 * de ejecutar los algoritmos de multiplicaci�n de matrices, adem�s
 * de guardar los resultados de tiempo de ejecuci�n de los mismos
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
	 * M�todo que permite registrar los algoritmos de la aplicaci�n
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
	 * M�todo que ejecuta todos los algoritmos por cada matriz
	 * */
	public void executeAlgorithms() {

		for (int i = 0; i < 15; i++) {
			ArrayList<Time> lstTimes = new ArrayList<Time>();
			int n = 4;
			int timeNumber = 0;

			refreshValuesTable(i);

			for (int j = 0; j < 8; j++) {

				// Variables para calcular el tiempo de ejecuci�n de cada algoritmo
				Long startTime = (long)0;
				Long endTime = (long)0;
				String pathFile = getPathFileMatrix(n);

				int[][] firstMatrix = AdminFiles.readMatrixFile(pathFile);
				int[][] secondMatrix = firstMatrix;

				// Matriz que almacena el resultado de la multiplicaci�n
				int[][] resultMatrix = new int[n][n];

				// Se obtiene el nombre del m�todo basado en la lista creada
				// anteriormente
				String methodName = lstAlgorithms.get(i).getName();
				System.out.println("Algoritmo " + methodName);

				Method method;

				try {

					/*
					 * Se obtiene el m�todo a ejecutar de la clase ExecutesAlgorithms utilizando
					 * el nombre del m�todo
					 * */
					method = MatrixAlgorithms.class.getMethod(methodName, int[][].class, int[][].class, int[][].class, int.class, int.class, int.class);

					startTime = System.nanoTime();
			        method.invoke(null, firstMatrix, secondMatrix, resultMatrix, n, n, n);
			        endTime = System.nanoTime();

				} catch (Exception e) {
					e.printStackTrace();
				}

				// se calcula el tiempo de ejecuci�n total del algortimo
				Long totalTime = endTime - startTime;

				Time time = new Time(n, totalTime);
				lstTimes.add(timeNumber, time);
				System.out.println("Termino ejecuci�n, agregado a la lista, tama�o: " + n);

				timeNumber++;
				n = n * 2;
			}

			lstAlgorithms.get(i).setLstTimes(lstTimes);
			lstAlgorithmsTable.add(new AlgorithmTable(lstAlgorithms.get(i)));
		}

	}

	/*
	 * M�todo encargado de generar la ruta de cada archivo de matriz
	 * */
	private String getPathFileMatrix(int n) {
		return "./src/files/matriz" + n + "x" + n + ".txt";
	}


	/*
	 * M�todo que permite actualizar los valores de la tabla
	 * */
	private void refreshValuesTable(int numeroAlgoritmo) {
		/*
		 * Se valida si se dio click en el bot�n de ejecutar algoritmos
		 * y se actualizan los tiempos de ejecuci�n
		 * */
		if (numeroAlgoritmo == 0 && lstAlgorithmsTable.size() == 15) {
			lstAlgorithms.get(numeroAlgoritmo).setLstTimes(new ArrayList<Time>());;
			lstAlgorithmsTable.clear();
		}
	}

	/**
	 * M�todo que calcula los datos para las estadisticas
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
