package utilities;

/*
 * Clase encargada de generar los archivos para las diferentes matrices
 * */
public class MatrixTest {

	/*
	 * Método encargado de generar las matrices de prueba para los 8 casos
	 * inciando desde una matriz de 512x512
	 * */
	public static void generateMatrixTest() {
		System.out.println("Generar matrices de prueba");
		int n = 4;

		for (int i = 0; i < 8; i++) {
			AdminFiles.generateFileMatrix(n);
			System.out.println("Matriz: matriz"+n+"x"+n+".txt generada exitosamente");
			n = n * 2;
		}
	}
}
