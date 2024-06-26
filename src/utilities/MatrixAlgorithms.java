package utilities;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MatrixAlgorithms {
	/**
	 * M�todo 1: NaivOnArray
	 * Este algoritmo llamado "NaivOnArray" realiza la multiplicaci�n
	 * de matrices utilizando un m�todo ingenuo. Recorre cada elemento
	 * de la matriz resultante (matrizC) y calcula su valor
	 * sumando los productos de los elementos correspondientes
	 * de las filas de la matriz A y las columnas de la matriz B.
	 */
    public static void NaivOnArray(int[][] matrizA, int[][] matrizB, int[][] matrizC, int N, int P , int M){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrizC[i][j] = 0;
                for (int k = 0; k < P; k++) {
                    matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }
    }

    /**
     * M�todo 2: NaivLoopUnrollingTwo
     * Las multiplicaciones se suman a la variable auxiliar,
     * que luego se asigna a la posici�n correspondiente en
     * la matriz C. posici�n correspondiente en la matriz C.
     * Esto se hace para garantizar que todos los elementos
     * de la matriz C se cuenten correctamente
     */
    public static void NaivLoopUnrollingTwo(int[][] matrizA, int[][] matrizB, int[][] matrizC, int N, int P, int M) {
        int i, j, k;
        int aux;
        if (P % 2 == 0) {
            for (i = 0; i < N; i++) {
                for (j = 0; j < M; j++) {
                    aux = 0;
                    for (k = 0; k < P; k += 2) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j];
                    }
                    matrizC[i][j] = aux;
                }
            }
        } else {
            int PP = P - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < M; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 2) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j];
                    }
                    matrizC[i][j] = aux + matrizA[i][PP] * matrizB[PP][j];
                }
            }
        }

    }

    /**
     * M�todo 3: NaivLoopUnrollingFour
		Este algoritmo "NaivLoopUnrollingFour" multiplica matrices
		mediante desenrollado de bucles por cuatro. Divide
		la multiplicaci�n en bloques de cuatro elementos y
		los calcula en paralelo para mejorar la eficiencia
		de los procesadores modernos. Si el intervalo (P) es divisible por 4,
		haz la multiplicaci�n como de costumbre. Si este no es el caso, realice
		un procesamiento especial para garantizar que todos los elementos se calculen correctamente.
     */
    public static void NaivLoopUnrollingFour(int[][] A, int[][] B, int[][] Result, int N, int P, int M) {
        int i, j, k;
        int aux;

        if (P % 4 == 0) {
            for (i = 0; i < N; i++) {
                for (j = 0; j < M; j++) {
                    aux = 0;
                    for (k = 0; k < P; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = aux;
                }
            }
        } else if (P % 4 == 1) {
            int PP = P - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < M; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = aux + A[i][PP]*B[PP][j];
                }
            }
        } else if (P % 4 == 2) {

            int PP = P - 2;
            int PPP = P - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < M; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = aux + A[i][PP]*B[PP][j] + A[i][PPP]*B[PPP][j];
                }
            }
        } else {
            int PP = P - 3;
            int PPP = P - 2;
            int PPPP = P - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < M; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = aux + A[i][PP]*B[PP][j] + A[i][PPP]*B[PPP][j]
                            + A[i][PPPP]*B[PPPP][j];
                }
            }
        }
    }

    /**
     * M�todo 4: WinogradOriginal
     * Este algoritmo, "WinogradOriginal", emplea el algoritmo
     * de multiplicaci�n de matrices de Winograd para optimizar
     * la eficiencia de la multiplicaci�n de matrices.
     * Calcula valores auxiliares para las matrices de entrada,
     * reduce la cantidad de operaciones durante la multiplicaci�n y,
     * si es necesario, realiza ajustes adicionales para garantizar la precisi�n.
     */
    public static void WinogradOriginal(int[][] A, int[][] B, int[][] Result, int N, int P, int M) {
        int i, j, k;
        int aux;
        //constantes necesarias para el M�todo
        int upsilon = P % 2; //Residuo del tama�o / 2 -> 1(impar) o 0(par)
        int gamma = P - upsilon; //El tama�o menos el resultado del residuo del tama�o / 2

        //arreglos auxiliares
        int[] y = new int[M];
        int[] z = new int[N];


        for (i = 0; i < M; i++) {
            aux = 0;
            for (j = 0; j < gamma; j += 2) {
                aux += A[i][j] * A[i][j+1];
            }
            y[i] = aux;
        }

        for (i = 0; i < N; i++) {
            aux = 0;
            for (j = 0; j < gamma; j += 2) {
                aux += B[j][i] * B[j+1][i];
            }
            z[i] = aux;
        }

        if (upsilon == 1) {
            /*
             * P es impar
             * El valor A[i][P]*B[P][k] falta en las sumas auxiliares anteriores.
             */
            int PP = P - 1;
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0;
                    for (j = 0; j < gamma; j += 2) {
                        aux += (A[i][j] + B[j+1][k]) * (A[i][j+1] + B[j][k]);
                    }
                    //Aca se agregan
                    Result[i][k] = aux - y[i] - z[k] + A[i][PP] * B[PP][k];
                }
            }
        } else {
            /*
             * P es par
             * No hace falta el valor A[i][P]*B[P][k], con las sumas auxiliares es suficiente.
             */
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0;
                    for (j = 0; j < gamma; j += 2) {
                        aux += (A[i][j] + B[j+1][k]) * (A[i][j+1] + B[j][k]);
                    }
                    Result[i][k] = aux - y[i] - z[k];
                }
            }
        }

        // Liberar memoria
        y = null;
        z = null;
    }

    /**
     * M�todo 5: WinogradScaled
     * Este algoritmo "WinogradScaled" mejora la multiplicaci�n de matrices mediante
     * el uso de una versi�n escalada del algoritmo Winograd.
	   Escalado de matriz:

	   Calcula la norma infinita de las matrices de entrada para determinar el factor de escala.
	   Cree copias de matrices escaladas seg�n este factor.
	   Multiplicaci�n escalar:
	   Define una funci�n que multiplica una matriz por un escalar.
	   Multiplicaci�n de matrices escalares con Winograd:
	   Utiliza la implementaci�n original del algoritmo de Winograd para multiplicar matrices escalares.
     */
    public static void WinogradScaled(int[][] A, int[][] B, int[][] Result, int N, int P, int M) {
        /* Create scaled copies of A and B */
        int[][] CopyA = new int[N][P];
        int[][] CopyB = new int[P][M];
        /* Scaling factors */
        double a = NormInf(A, N, P);
        double b = NormInf(B, P, M);
        double lambda = Math.floor(0.5 + Math.log(b/a)/Math.log(4));
        /* Scaling */
        MultiplyWithScalar(A, CopyA, N, P, (int) Math.pow(2, lambda));
        MultiplyWithScalar(B, CopyB, P, M, (int) Math.pow(2, -lambda));
        /* Using Winograd with scaled matrices */
        WinogradOriginal(CopyA, CopyB, Result, N, P, M);
    }

    public static void MultiplyWithScalar(int[][] A, int[][] B, int N, int M, int scalar) {
        int i, j;
        for (i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                B[i][j] = A[i][j] * scalar;
            }
        }
    }

    public static int NormInf(int[][] A, int N, int M) {
        int i, j;
        double max = Double.NEGATIVE_INFINITY;
        for (i = 0; i < N; i++) {
            double sum = 0.0;
            for (j = 0; j < M; j++) {
                sum += Math.abs(A[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }
        return (int) max;
    }

    /**
     * M�todo 6: StrassenNaiv
     * El algoritmo "StrassenNaiv" simplifica el m�todo
     * de Strassen para multiplicar matrices. Comienza determinando
     * el tama�o m�ximo de las matrices y ajust�ndolas para que sean
     * cuadradas y tengan dimensiones potencias de 2. Luego, divide
     * las matrices en submatrices m�s peque�as y las multiplica utilizando
     *  el algoritmo de Strassen. Despu�s, recomponen los resultados en una
     *  matriz de salida. Si las matrices son lo suficientemente peque�as,
		emplea el m�todo est�ndar de multiplicaci�n.
     */
    public static void StrassenNaiv(int[][] matrizA, int[][] matrizB, int[][] matrizC, int N, int P, int M) {

        int MaxSize, k, m, NewSize, i, j;
        //MaxSize = max(N,P);
        MaxSize = max(N,P);
        //MaxSize = max(MaxSize,M);

        if (MaxSize < 16) {
            MaxSize = 16; // otherwise it is not possible to compute k
        }
        k = (int) Math.floor(Math.log(MaxSize)/Math.log(2)) - 4;
        m = (int) Math.floor(MaxSize * Math.pow(2,-k)) + 1;

        NewSize = m * (int) Math.pow(2,k);

        // add zero rows and columns to use Strassens algorithm
        int[][] NewA = new int[NewSize][];
        int[][] NewB = new int[NewSize][];
        int[][] AuxResult = new int[NewSize][];
        for (i = 0; i < NewSize; i++){
            NewA[i] = new int[NewSize];
            NewB[i] = new int[NewSize];
            AuxResult[i] = new int[NewSize];
        }


        for (i = 0; i < NewSize; i++) {
            for (j = 0; j < NewSize; j++) {
                NewA[i][j] = 0;
                NewB[i][j] = 0;
            }
        }
        for (i = 0; i < N; i++) {
            for (j = 0; j < P; j++) {
                NewA[i][j] = matrizA[i][j];
            }
        }
        for (i = 0; i < P; i++) {
            for (j = 0; j < M; j++) {
                NewB[i][j] = matrizB[i][j];
            }
        }
        StrassenNaivStep(NewA, NewB, AuxResult, NewSize, m);
        // extract the result
        for (i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                matrizC[i][j] = AuxResult[i][j]; //Result
            }
        }
    }

    public static int max (int N, int P){
        if (N < P){
            return P;
        } else {
            return N;
        }

    }

    private static void Minus(int[][] A, int[][] B, int[][] Result, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Result[i][j] = A[i][j] - B[i][j];
            }
        }
    }

    private static void Plus(int[][] A, int[][] B, int[][] Result, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Result[i][j] = A[i][j] + B[i][j];
            }
        }
    }

    public static void StrassenNaivStep(int[][] A, int[][] B, int[][] Result, int N, int m) {
        int i, j, NewSize;

        if ((N % 2 == 0) && (N > m)){
            NewSize = N / 2;

            // decompose A and B
            // create ResultPart, Aux1,...,Aux7 and Helper1, Helper2
            int[][] A11 = new int[NewSize][];
            int[][] A12 = new int[NewSize][];
            int[][] A21 = new int[NewSize][];
            int[][] A22 = new int[NewSize][];
            int[][] B11 = new int[NewSize][];
            int[][] B12 = new int[NewSize][];
            int[][] B21 = new int[NewSize][];
            int[][] B22 = new int[NewSize][];

            int[][] ResultPart11 = new int[NewSize][];
            int[][] ResultPart12 = new int[NewSize][];
            int[][] ResultPart21 = new int[NewSize][];
            int[][] ResultPart22 = new int[NewSize][];

            int[][] Helper1 = new int[NewSize][];
            int[][] Helper2 = new int[NewSize][];

            int[][] Aux1 = new int[NewSize][];
            int[][] Aux2 = new int[NewSize][];
            int[][] Aux3 = new int[NewSize][];
            int[][] Aux4 = new int[NewSize][];
            int[][] Aux5 = new int[NewSize][];
            int[][] Aux6 = new int[NewSize][];
            int[][] Aux7 = new int[NewSize][];

            for (i = 0; i < NewSize; i++){

                A11[i] = new int[NewSize];
                A12[i] = new int[NewSize];
                A21[i] = new int[NewSize];
                A22[i] = new int[NewSize];
                B11[i] = new int[NewSize];
                B12[i] = new int[NewSize];
                B21[i] = new int[NewSize];
                B22[i] = new int[NewSize];

                ResultPart11[i] = new int[NewSize];
                ResultPart12[i] = new int[NewSize];
                ResultPart21[i] = new int[NewSize];
                ResultPart22[i] = new int[NewSize];

                Helper1[i] = new int[NewSize];
                Helper2[i] = new int[NewSize];

                Aux1[i] = new int[NewSize];
                Aux2[i] = new int[NewSize];
                Aux3[i] = new int[NewSize];
                Aux4[i] = new int[NewSize];
                Aux5[i] = new int[NewSize];
                Aux6[i] = new int[NewSize];
                Aux7[i] = new int[NewSize];
            }

            //fill new matrices
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A11[i][j] = A[i][j];
                }
            }

            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A12[i][j] = A[i][NewSize + j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A21[i][j] = A[NewSize + i][j];
                }
            }

            for( i = 0; i < NewSize; i++) {
                for (j = 0; j < NewSize; j++) {
                    A22[i][j] = A[NewSize + i][NewSize + j];
                }
            }

            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B11[i][j] = B[i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B12[i][j] = B[i][NewSize + j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B21[i][j] = B[NewSize + i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B22[i][j] = B[NewSize + i][NewSize + j];
                }
            }

            // computing the seven aux. variables
            Plus(A11, A22, Helper1, NewSize, NewSize);
            Plus(B11, B22, Helper2, NewSize, NewSize);
            StrassenNaivStep(Helper1, Helper2, Aux1, NewSize, m);

            Plus(A21, A22, Helper1, NewSize, NewSize);
            StrassenNaivStep(Helper1, B11, Aux2, NewSize, m);

            Minus(B12, B22, Helper1, NewSize, NewSize);
            StrassenNaivStep(A11, Helper1, Aux3, NewSize, m);

            Minus(B21, B11, Helper1, NewSize, NewSize);
            StrassenNaivStep(A22, Helper1, Aux4, NewSize, m);

            Plus(A11, A12, Helper1, NewSize, NewSize);
            StrassenNaivStep(Helper1, B22, Aux5, NewSize, m);

            Minus(A21, A11, Helper1, NewSize, NewSize);
            Plus(B11, B12, Helper2, NewSize, NewSize);
            StrassenNaivStep(Helper1, Helper2, Aux6, NewSize, m);

            Minus(A12, A22, Helper1, NewSize, NewSize);
            Plus(B21, B22, Helper2, NewSize, NewSize);
            StrassenNaivStep(Helper1, Helper2, Aux7, NewSize, m);

            // computing the four parts of the result
            Plus(Aux1, Aux4, ResultPart11, NewSize, NewSize);
            Minus(ResultPart11, Aux5, ResultPart11, NewSize, NewSize);
            Plus(ResultPart11, Aux7, ResultPart11, NewSize, NewSize);

            Plus(Aux3, Aux5, ResultPart12, NewSize, NewSize);

            Plus(Aux2, Aux4, ResultPart21, NewSize, NewSize);

            Plus(Aux1, Aux3, ResultPart22, NewSize, NewSize);
            Minus(ResultPart22, Aux2, ResultPart22, NewSize, NewSize);
            Plus(ResultPart22, Aux6, ResultPart22, NewSize, NewSize);

            // store results in the "result matrix"
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[i][j] = ResultPart11[i][j];
                }
            }

            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[i][NewSize + j] = ResultPart12[i][j];
                }
            }

            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[NewSize + i][j] = ResultPart21[i][j];
                }
            }

            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[NewSize + i][NewSize + j] = ResultPart22[i][j];
                }
            }

            // free helper variables
            A11 = null;
            A12 = null;
            A21 = null;
            A22 = null;

            B11 = null;
            B12 = null;
            B21 = null;
            B22 = null;

            ResultPart11 = null;
            ResultPart12 = null;
            ResultPart21 = null;
            ResultPart22 = null;

            Helper1 = null;
            Helper2 = null;

            Aux1 = null;
            Aux2 = null;
            Aux3 = null;
            Aux4 = null;
            Aux5 = null;
            Aux6 = null;
            Aux7 = null;

        } else {
            // use naiv algorithm
             NaivStandard(A, B, Result, N, N, N);
        }
    }

    /**
	 * M�todo auxiliar: NaivStandard
	 */
	public static void NaivStandard(int[][] matrizA, int[][] matrizB, int[][] matrizC, int N, int P, int M){
        int aux;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                aux = 0;
                for (int k = 0; k < P; k++) {
                    aux += matrizA[i][k] * matrizB[k][j];
                }
                matrizC[i][j] = aux;
            }
        }
    }

	/**
     * M�todo 7: StrassenWinograd
     * El m�todo "StrassenWinograd" implementa una variante
     * del algoritmo de Strassen para multiplicar matrices.
     * Comienza ajustando las dimensiones de las matrices de
     * entrada para que sean cuadradas y potencias de 2.
     * Luego, divide las matrices en submatrices m�s peque�as
     * y las multiplica recursivamente utilizando una versi�n
     * modificada del algoritmo de Strassen. Despu�s, recomponen
     * los resultados en una matriz de salida. Si las matrices
     * son lo suficientemente peque�as, emplea el m�todo est�ndar de multiplicaci�n.
     */
    public static void StrassenWinograd(int[][] matrizA, int[][] matrizB, int[][] matrizC, int N, int P, int M) {

        int MaxSize, k, m, NewSize, i, j;
        MaxSize = max(N,P);
        MaxSize = max(MaxSize,M);
        if ( MaxSize < 16){
            MaxSize = 16; // otherwise it is not possible to compute k
        }
        k = (int) Math.floor(Math.log(MaxSize)/Math.log(2)) - 4;
        m = (int) Math.floor(MaxSize * Math.pow(2,-k)) + 1;
        NewSize = m * (int) Math.pow(2,k);


        // add zero rows and columns to use Strassens algorithm
        int[][] NewA = new int[NewSize][];
        int[][] NewB = new int[NewSize][];
        int[][] AuxResult = new int[NewSize][];
        for (i = 0; i < NewSize; i++){
            NewA[i] = new int[NewSize];
            NewB[i] = new int[NewSize];
            AuxResult[i] = new int[NewSize];
        }

        for( i = 0; i < NewSize; i++){
            for( j = 0; j < NewSize; j++){
                NewA[i][j] = 0;
                NewB[i][j] = 0;
            }
        }
        for( i = 0; i < N; i++){
            for( j = 0; j < P; j++){
                NewA[i][j] = matrizA[i][j];
            }
        }
        for( i = 0; i < P; i++){
            for( j = 0; j < M; j++){
                NewB[i][j] = matrizB[i][j];
            }
        }

        StrassenWinogradStep(NewA, NewB, AuxResult, NewSize, m);

        // extract the result
        for( i = 0; i < N; i++){
            for( j = 0; j < M; j++){
                matrizC[i][j] = AuxResult[i][j];
            }
        }
    }

    private static void StrassenWinogradStep(int[][] A, int[][] B, int[][] Result, int N, int m) {
        int i, j , NewSize;

        if( (N % 2 == 0) && (N > m) ) { // recursive use of StrassenNaivStep
            NewSize = N / 2;

            // decompose A and B
            // create ResultPart, Aux1,...,Aux7 and Helper1, Helper2
            int[][] A1 = new int[NewSize][];
            int[][] A2 = new int[NewSize][];
            int[][] B1 = new int[NewSize][];
            int[][] B2 = new int[NewSize][];

            int[][] A11 = new int[NewSize][];
            int[][] A12 = new int[NewSize][];
            int[][] A21 = new int[NewSize][];
            int[][] A22 = new int[NewSize][];
            int[][] B11 = new int[NewSize][];
            int[][] B12 = new int[NewSize][];
            int[][] B21 = new int[NewSize][];
            int[][] B22 = new int[NewSize][];

            int[][] ResultPart11 = new int[NewSize][];
            int[][] ResultPart12 = new int[NewSize][];
            int[][] ResultPart21 = new int[NewSize][];
            int[][] ResultPart22 = new int[NewSize][];

            int[][] Helper1 = new int[NewSize][];
            int[][] Helper2 = new int[NewSize][];

            int[][] Aux1 = new int[NewSize][];
            int[][] Aux2 = new int[NewSize][];
            int[][] Aux3 = new int[NewSize][];
            int[][] Aux4 = new int[NewSize][];
            int[][] Aux5 = new int[NewSize][];
            int[][] Aux6 = new int[NewSize][];
            int[][] Aux7 = new int[NewSize][];
            int[][] Aux8 = new int[NewSize][];
            int[][] Aux9 = new int[NewSize][];

            for (i = 0; i < NewSize; i++){
                A1[i] = new int[NewSize];
                A2[i] = new int[NewSize];
                B1[i] = new int[NewSize];
                B2[i] = new int[NewSize];
                A11[i] = new int[NewSize];
                A12[i] = new int[NewSize];
                A21[i] = new int[NewSize];
                A22[i] = new int[NewSize];
                B11[i] = new int[NewSize];
                B12[i] = new int[NewSize];
                B21[i] = new int[NewSize];
                B22[i] = new int[NewSize];

                ResultPart11[i] = new int[NewSize];
                ResultPart12[i] = new int[NewSize];
                ResultPart21[i] = new int[NewSize];
                ResultPart22[i] = new int[NewSize];

                Helper1[i] = new int[NewSize];
                Helper2[i] = new int[NewSize];

                Aux1[i] = new int[NewSize];
                Aux2[i] = new int[NewSize];
                Aux3[i] = new int[NewSize];
                Aux4[i] = new int[NewSize];
                Aux5[i] = new int[NewSize];
                Aux6[i] = new int[NewSize];
                Aux7[i] = new int[NewSize];
                Aux8[i] = new int[NewSize];
                Aux9[i] = new int[NewSize];
            }

            // fill new matrices
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A11[i][j] = A[i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A12[i][j] = A[i][NewSize + j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A21[i][j] = A[NewSize + i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    A22[i][j] = A[NewSize + i][NewSize + j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B11[i][j] = B[i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B12[i][j] = B[i][NewSize + j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B21[i][j] = B[NewSize + i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    B22[i][j] = B[NewSize + i][NewSize + j];
                }
            }

            // computing the 4 + 9 aux. variables
            Minus(A11, A21, A1, NewSize, NewSize);
            Minus(A22, A1, A2, NewSize, NewSize);
            Minus(B22, B12, B1, NewSize, NewSize);
            Plus(B1, B11, B2, NewSize, NewSize);

            StrassenWinogradStep(A11, B11, Aux1, NewSize, m);
            StrassenWinogradStep(A12, B21, Aux2, NewSize, m);
            StrassenWinogradStep(A2, B2, Aux3, NewSize, m);
            Plus(A21, A22, Helper1, NewSize, NewSize);
            Minus(B12, B11, Helper2, NewSize, NewSize);
            StrassenWinogradStep(Helper1, Helper2, Aux4, NewSize, m);
            StrassenWinogradStep(A1, B1, Aux5, NewSize, m);
            Minus(A12, A2, Helper1, NewSize, NewSize);
            StrassenWinogradStep(Helper1, B22, Aux6, NewSize, m);
            Minus(B21, B2, Helper1, NewSize, NewSize);
            StrassenWinogradStep(A22, Helper1, Aux7, NewSize, m);
            Plus(Aux1, Aux3, Aux8, NewSize, NewSize);
            Plus(Aux8, Aux4, Aux9, NewSize, NewSize);

            // computing the four parts of the result
            Plus(Aux1, Aux2, ResultPart11, NewSize, NewSize);
            Plus(Aux9, Aux6, ResultPart12, NewSize, NewSize);
            Plus(Aux8, Aux5, Helper1, NewSize, NewSize);
            Plus(Helper1, Aux7, ResultPart21, NewSize, NewSize);
            Plus(Aux9, Aux5, ResultPart22, NewSize, NewSize);

            // store results in the "result matrix"
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[i][j] = ResultPart11[i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[i][NewSize + j] = ResultPart12[i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[NewSize + i][j] = ResultPart21[i][j];
                }
            }
            for( i = 0; i < NewSize; i++){
                for( j = 0; j < NewSize; j++){
                    Result[NewSize + i][NewSize + j] = ResultPart22[i][j];
                }
            }

            // free helper variables
            A1 = null;
            A2 = null;
            B1 = null;
            B2 = null;

            A11 = null;
            A12 = null;
            A21 = null;
            A22 = null;

            B11 = null;
            B12 = null;
            B21 = null;
            B22 = null;

            ResultPart11 = null;
            ResultPart12 = null;
            ResultPart21 = null;
            ResultPart22 = null;

            Helper1 = null;
            Helper2 = null;

            Aux1 = null;
            Aux2 = null;
            Aux3 = null;
            Aux4 = null;
            Aux5 = null;
            Aux6 = null;
            Aux7 = null;

        } else {
            // use naiv algorithm
            NaivStandard(A, B, Result, N, N, N);
        }
    }

    /**
	 * M�todo 8: iii3SequentialBlock
	 * El m�todo "IIISequentialBlock" implementa la
	 * multiplicaci�n de matrices mediante un
	 * enfoque de bloque secuencial, dividiendo
	 * las matrices de entrada en bloques y
	 * realizando multiplicaciones dentro de estos bloques.
	 * Comienza calculando el tama�o de cada bloque como
	 * la ra�z cuadrada del tama�o de las matrices y luego
	 * itera sobre los bloques en las matrices de entrada,
	 * calculando el producto punto entre las submatrices
	 * correspondientes de la matriz A y la matriz B.
	 * Este m�todo busca mejorar el rendimiento al reducir
	 * la necesidad de acceder repetidamente a los elementos
	 * de las matrices grandes en la memoria principal,
	 * aprovechando la localidad de los datos, aunque
	 * introduce una sobrecarga adicional debido a los bucles
	 * adicionales y la gesti�n de los l�mites de los bloques.
	 */
	public static void IIISequentialBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2){
		int bsize = (int) Math.sqrt(size);

		for (int i1 = 0; i1 < size; i1 += bsize) {
            for (int j1 = 0; j1 < size; j1 += bsize) {
                for (int k1 = 0; k1 < size; k1 += bsize) {
                    for (int i = i1; i < i1 + bsize && i < size; i++) {
                        for (int j = j1; j < j1 + bsize && j < size; j++) {
                            for (int k = k1; k < k1 + bsize && k < size; k++) {
                                matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
                            }
                        }
                    }
                }
            }
        }
    }

	/**
	 * M�todo 9: iii4ParallelBlock
	 * El m�todo "IIIParallelBlock" tambi�n implementa
	 * la multiplicaci�n de matrices mediante un enfoque
	 * de bloque, pero en paralelo utilizando Streams de Java.
	 * Divide las matrices de entrada en bloques del mismo
	 *  tama�o calculado como la ra�z cuadrada del tama�o
	 *  total de las matrices. Luego, utiliza un bucle paralelo
	 *  para iterar sobre estos bloques, calculando el producto
	 *  punto entre las submatrices correspondientes de la matriz
	 *  A y la matriz B. Este enfoque busca mejorar el rendimiento
	 *  al aprovechar el paralelismo de m�ltiples n�cleos de la
	 *  CPU para realizar c�lculos simult�neos en los bloques de las matrices,
		reduciendo as� el tiempo total de ejecuci�n. Sin embargo, puede introducir
		cierta complejidad adicional debido a la necesidad de manejar la concurrencia
		y la sincronizaci�n de los datos compartidos entre los hilos de ejecuci�n.
	 */
	public static void IIIParallelBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2){
		int bsize = (int) Math.sqrt(size);

		Arrays.stream(new int[]{0}).parallel().forEach(i1 -> {
            for (i1 = 0; i1 <size; i1 += bsize) {
                for (int j1 = 0; j1 < size; j1 += bsize) {
                    for (int k1 = 0; k1 < size; k1 += bsize) {
                        for (int i = i1; i < i1 + bsize && i < size; i++) {
                            for (int j = j1; j < j1 + bsize && j < size; j++) {
                                for (int k = k1; k < k1 + bsize && k < size; k++) {
                                    matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
                                }
                            }
                        }
                    }
                }
            }
        });
    }

	/**
	 * M�todo 10: III Enhanced Parallel Block
	 * El m�todo "IIIEnhancedParallelBlock" realiza la multiplicaci�n
	 * de matrices utilizando un enfoque de bloque paralelo mejorado.
	 * Divide las matrices de entrada en bloques del mismo tama�o,
	 * calculado como la ra�z cuadrada del tama�o total de las matrices.
	 * Luego, utiliza dos flujos paralelos de IntStream para distribuir
	 * el trabajo entre m�ltiples hilos de ejecuci�n de manera m�s eficiente.
	 * Cada flujo de IntStream aborda la mitad de las filas de la matriz C.
	 * Dentro de cada iteraci�n de estos flujos paralelos, se realiza el
	 * c�lculo del producto punto entre las submatrices correspondientes
	 * de la matriz A y la matriz B. Esto permite aprovechar el paralelismo
	 * de m�ltiples n�cleos de la CPU para realizar c�lculos simult�neos
	 * en diferentes partes de las matrices, reduciendo as� el tiempo total
	 * de ejecuci�n. Sin embargo, este enfoque puede introducir cierta
	 * complejidad adicional debido a la necesidad de manejar la concurrencia
	 * y la sincronizaci�n de los datos compartidos entre los hilos de ejecuci�n.
	 */
	public static void IIIEnhancedParallelBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2) {
		int bsize = (int) Math.sqrt(size);

		IntStream.range(0, size / 2).parallel().forEach(i1 -> {
		    for (int j1 = 0; j1 < size; j1 += bsize) {
		        for (int k1 = 0; k1 < size; k1 += bsize) {
		            for (int i = i1; i < i1 + bsize && i < size; i++) {
		                for (int j = j1; j < j1 + bsize && j < size; j++) {
		                    for (int k = k1; k < k1 + bsize && k < size; k++) {
		                        matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
		                    }
		                }
		            }
		        }
		    }
		});

		IntStream.range(size / 2, size).parallel().forEach(i1 -> {
		    for (int j1 = 0; j1 < size; j1 += bsize) {
		        for (int k1 = 0; k1 < size; k1 += bsize) {
		            for (int i = i1; i < i1 + bsize && i < size; i++) {
		                for (int j = j1; j < j1 + bsize && j < size; j++) {
		                    for (int k = k1; k < k1 + bsize && k < size; k++) {
		                        matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
		                    }
		                }
		            }
		        }
		    }
		});
	}

	/**
	 * M�todo 11: iv3SequentialBlock
	 * El m�todo "IVSequentialBlock" implementa la multiplicaci�n
	 * de matrices utilizando un enfoque secuencial de bloques.
	 * Divide las matrices de entrada en bloques cuadrados del
	 * mismo tama�o, calculado como la ra�z cuadrada del tama�o
	 * total de las matrices. Luego, utiliza cuatro bucles
	 * anidados para recorrer los bloques de las matrices A, B y C.
	 * En cada iteraci�n de los bucles, se calcula el producto
	 * punto entre los elementos correspondientes de las submatrices
	 * de A y B y se acumula en la submatriz correspondiente de C.
	 * Este enfoque aprovecha la localidad de los datos y reduce
	 * los accesos a la memoria al operar con bloques de datos contiguos,
	 * lo que puede mejorar el rendimiento en comparaci�n con un enfoque
		ingenuo que opera directamente en elementos individuales de las matrices.
	 */
	public static void IVSequentialBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2){
		int bsize = (int) Math.sqrt(size);

		for (int i1 = 0; i1 < size; i1 += bsize) {
            for (int j1 = 0; j1 < size; j1 += bsize) {
                for (int k1 = 0; k1 < size; k1 += bsize) {
                	for (int i = i1 * bsize; i < (i1 + 1) * bsize && i < size; i++) {
                        for (int j = j1; j < j1 + bsize && j < size; j++) {
                            for (int k = k1; k < k1 + bsize && k < size; k++) {
                                matrizC[i][k] += matrizA[i][j] * matrizB[j][k];
                            }
                        }
                    }
                }
            }
        }
    }

	/**
	 * M�todo 12: iv4ParallelBlock
	 * El m�todo "IVParallelBlock" implementa la multiplicaci�n de
	 * matrices utilizando un enfoque paralelo de bloques.
	 * Divide las matrices de entrada en bloques cuadrados
	 * del mismo tama�o, calculado como la ra�z cuadrada del
	 * tama�o total de las matrices. Luego, utiliza un bucle
	 * paralelo para recorrer los bloques de la matriz A.
	 * Dentro de este bucle, se ejecutan cuatro bucles anidados
	 * para recorrer los bloques de las matrices A, B y C,
	 * calculando el producto punto entre los elementos correspondientes
	 * de las submatrices de A y B y acumul�ndolo en la submatriz
	 * correspondiente de C. Este enfoque paralelo puede mejorar
	 * el rendimiento al procesar m�ltiples bloques de datos simult�neamente,
	 * aprovechando as� mejor los recursos del sistema.
	 */
	public static void IVParallelBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2){
		int bsize = (int) Math.sqrt(size);

		IntStream.range(0, size / bsize).parallel().forEach(i1 -> {
            for (int j1 = 0; j1 < size; j1 += bsize) {
                for (int k1 = 0; k1 < size; k1 += bsize) {
                    for (int i = i1 * bsize; i < (i1 + 1) * bsize && i < size; i++) {
                        for (int j = j1; j < j1 + bsize && j < size; j++) {
                            for (int k = k1; k < k1 + bsize && k < size; k++) {
                                matrizC[i][k] += matrizA[i][j] * matrizB[j][k];
                            }
                        }
                    }
                }
            }
        });
	}

	/**
	 * M�todo 13: IV Enhanced Parallel Block
	 * El m�todo "IVEnhancedParallelBlock" implementa una
	 * multiplicaci�n de matrices utilizando un enfoque
	 * paralelo mejorado de bloques. Divide las matrices
	 * de entrada en bloques cuadrados del mismo tama�o,
	 * calculado como la ra�z cuadrada del tama�o total de las matrices.
	 * Luego, utiliza dos flujos de enteros paralelos para procesar
	 * las mitades de las filas de la matriz C. Dentro de cada flujo,
	 * se ejecutan cuatro bucles anidados para recorrer los bloques de
	 * las matrices A, B y C, calculando el producto punto entre
	 * los elementos correspondientes de las submatrices de A y B y
	 * acumul�ndolo en la submatriz correspondiente de C.
	 * Este enfoque paralelo mejorado puede proporcionar un mejor
	 * rendimiento al distribuir la carga de trabajo entre m�ltiples
	 * subprocesos y aprovechar al m�ximo los recursos del sistema.
	 */
	public static void IVEnhancedParallelBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2) {

		int bsize = (int) Math.sqrt(size);

		IntStream.range(0, size / 2).parallel().forEach(i1 -> {
		    for (int j1 = 0; j1 < size; j1 += bsize) {
		        for (int k1 = 0; k1 < size; k1 += bsize) {
		            for (int i = i1; i < i1 + bsize && i < size; i++) {
		                for (int j = j1; j < j1 + bsize && j < size; j++) {
		                    for (int k = k1; k < k1 + bsize && k < size; k++) {
		                        matrizC[i][k] += matrizA[i][j] * matrizB[j][k];
		                    }
		                }
		            }
		        }
		    }
		});

		IntStream.range(size / 2, size).parallel().forEach(i1 -> {
		    for (int j1 = 0; j1 < size; j1 += bsize) {
		        for (int k1 = 0; k1 < size; k1 += bsize) {
		            for (int i = i1; i < i1 + bsize && i < size; i++) {
		                for (int j = j1; j < j1 + bsize && j < size; j++) {
		                    for (int k = k1; k < k1 + bsize && k < size; k++) {
		                        matrizC[i][k] += matrizA[i][j] * matrizC[j][k];
		                    }
		                }
		            }
		        }
		    }
		});
	}

	/**
	 * M�todo 14: v3SequentialBlock
	 * El m�todo "VSequentialBlock" implementa la multiplicaci�n de
	 * matrices utilizando un enfoque secuencial basado en bloques.
	 * Divide las matrices de entrada en bloques cuadrados del mismo
	 * tama�o, calculado como la ra�z cuadrada del tama�o total
	 * de las matrices. Luego, utiliza cuatro bucles anidados para
	 * recorrer los bloques de las matrices A, B y C, calculando
	 * el producto punto entre los elementos correspondientes de
	 * las submatrices de A y B y acumul�ndolo en la submatriz
	 * correspondiente de C. Este enfoque secuencial basado en bloques
	 * puede mejorar el rendimiento al reducir la carga de trabajo
	 * de los bucles internos y aprovechar la localidad de datos.
	 */
	public static void VSequentialBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2){
		int bsize = (int) Math.sqrt(size);

		for (int i1 = 0; i1 < size; i1 += bsize) {
            for (int j1 = 0; j1 < size; j1 += bsize) {
                for (int k1 = 0; k1 < size; k1 += bsize) {
                    for (int i = i1; i < i1 + bsize && i < size; i++) {
                        for (int j = j1; j < j1 + bsize && j < size; j++) {
                            for (int k = k1; k < k1 + bsize && k < size; k++) {
                                matrizC[k][i] += matrizA[k][j] * matrizB[j][i];
                            }
                        }
                    }
                }
            }
		}
	}

	/**
	 * M�todo 15: v4ParallelBlock
	 * El m�todo "VParallelBlock" implementa una versi�n
	 * paralela del algoritmo de multiplicaci�n de matrices
	 * basado en bloques. Utiliza el framework de Streams de
	 * Java para distribuir el trabajo en m�ltiples hilos.
	 * Divide las matrices de entrada en bloques cuadrados
	 * del mismo tama�o, calculado como la ra�z cuadrada del
	 * tama�o total de las matrices. Luego, utiliza cinco
	 * bucles anidados para recorrer los bloques de las matrices A, B y C,
	 * calculando el producto punto entre los elementos correspondientes
	 * de las submatrices de A y B y acumul�ndolo en la submatriz correspondiente de C.
	 * Este enfoque paralelo puede mejorar el rendimiento al ejecutar m�ltiples
	 * c�lculos simult�neamente en diferentes hilos, aprovechando as� mejor los recursos del procesador.
	 */
	public static void VParallelBlock(int[][] matrizA, int[][] matrizB, int[][] matrizC, int size, int aux1, int aux2){

		int bsize = (int) Math.sqrt(size);

		IntStream.range(0, 1).parallel().forEach(_i -> {
            for (int i1 = 0; i1 < size; i1 += bsize) {
                for (int j1 = 0; j1 < size; j1 += bsize) {
                    for (int k1 = 0; k1 < size; k1 += bsize) {
                        for (int i = i1; i < i1 + bsize && i < size; i++) {
                            for (int j = j1; j < j1 + bsize && j < size; j++) {
                                for (int k = k1; k < k1 + bsize && k < size; k++) {
                                    matrizC[k][i] += matrizA[k][j] * matrizB[j][i];
                                }
                            }
                        }
                    }
                }
            }
        });
	}
}
