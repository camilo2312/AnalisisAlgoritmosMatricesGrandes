package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AdminFiles {

	/*
	 * Método que permite leer el archivo con la matriz generada
	 * */
	public static int[][] readMatrixFile(String path) {
		int[][] matrix = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			String line = br.readLine();

			// Linea encargad de obtener el tamaño de la matriz
			int size = Integer.parseInt(line);
			matrix = new int[size][size];

			// filas d ela matriz
			line = br.readLine();

			// Contador de las filas de la matriz
			int row = 0;
			while (line != null) {

				/*
				 * La variable line contiene todos los enteros de la matriz,
				 * por medio de la función .split se separan con un espacio
				 * y se recorre para guardarlos como un entero
				 * */
				String[] numbers = line.split(" ");
				for (int i = 0; i < numbers.length; i++)
					matrix[row][i] = Integer.parseInt(numbers[i]);

				row++;
				// Se lee la siguiente linea del documento
				line = br.readLine();
			}

			// Cierra el lector del archivo
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Error al convertir a entero");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("No se puedo acceder al archivo");
			e.printStackTrace();
		}
		return matrix;
	}


	/*
	 * Método que permite generar los archivos que contienen las matrices a
	 * ejecutar por los algoritmos dado un n determinado
	 * @param n
	 * */
	public static void generateFileMatrix(int n) {
		String path = "./src/files/matriz"+n+"x"+n+".txt";
		Random random = new Random();

		try {
			FileWriter fw = new FileWriter(path,false);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(n+"\r\n");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					// Genera números de minimo 6 digitos
					bw.write(random.nextInt(900000) + 100000+" ");
				}
				bw.write("\r\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * Método que permite eliminar los archivos de matrices
	 * */
	public static void delteFileMatrix() {

	   // Ruta en la que se encuentran todos los archivos de las matrices
       String pathFolder = "./src/files/";
       File folder = new File(pathFolder);

       // Obtiene la lista de archivos de la carpeta
       File[] filesMatrixes = folder.listFiles();

       // Elimina cada archivo de matriz generado
       for (File fileMatrix : filesMatrixes) {
           fileMatrix.delete();
       }

       System.out.println("Los archivos de matrix han sido eliminados correctamente.");
	}
}
