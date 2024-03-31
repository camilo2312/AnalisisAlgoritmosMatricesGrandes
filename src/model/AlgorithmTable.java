package model;

/*
 * Clase que manipula la tabla de la vista
 * */
public class AlgorithmTable {
	private Algorithm algorithm;
	private int size1, size2, size3, size4, size5, size6, size7, size8;
	private long time1, time2, time3, time4, time5, time6, time7, time8;

	public AlgorithmTable(Algorithm algorithm) {
		super();
		this.algorithm = algorithm;
		setProperties();
	}

	private void setProperties() {
		size1 = algorithm.getLstTimes().get(0).getSizeMatrix();
		time1 = algorithm.getLstTimes().get(0).getTime();

		size2 = algorithm.getLstTimes().get(1).getSizeMatrix();
		time2 = algorithm.getLstTimes().get(1).getTime();

		size3 = algorithm.getLstTimes().get(2).getSizeMatrix();
		time3 = algorithm.getLstTimes().get(2).getTime();

		size4 = algorithm.getLstTimes().get(3).getSizeMatrix();
		time4 = algorithm.getLstTimes().get(3).getTime();

		size5 = algorithm.getLstTimes().get(4).getSizeMatrix();
		time5 = algorithm.getLstTimes().get(4).getTime();

		size6 = algorithm.getLstTimes().get(5).getSizeMatrix();
		time6 = algorithm.getLstTimes().get(5).getTime();

		size7 = algorithm.getLstTimes().get(6).getSizeMatrix();
		time7 = algorithm.getLstTimes().get(6).getTime();

		size8 = algorithm.getLstTimes().get(7).getSizeMatrix();
		time8 = algorithm.getLstTimes().get(7).getTime();
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public int getSize1() {
		return size1;
	}

	public void setSize1(int size1) {
		this.size1 = size1;
	}

	public int getSize2() {
		return size2;
	}

	public void setSize2(int size2) {
		this.size2 = size2;
	}

	public int getSize3() {
		return size3;
	}

	public void setSize3(int size3) {
		this.size3 = size3;
	}

	public int getSize4() {
		return size4;
	}

	public void setSize4(int size4) {
		this.size4 = size4;
	}

	public int getSize5() {
		return size5;
	}

	public void setSize5(int size5) {
		this.size5 = size5;
	}

	public int getSize6() {
		return size6;
	}

	public void setSize6(int size6) {
		this.size6 = size6;
	}

	public int getSize7() {
		return size7;
	}

	public void setSize7(int size7) {
		this.size7 = size7;
	}

	public int getSize8() {
		return size8;
	}

	public void setSize8(int size8) {
		this.size8 = size8;
	}

	public long getTime1() {
		return time1;
	}

	public void setTime1(long time1) {
		this.time1 = time1;
	}

	public long getTime2() {
		return time2;
	}

	public void setTime2(long time2) {
		this.time2 = time2;
	}

	public long getTime3() {
		return time3;
	}

	public void setTime3(long time3) {
		this.time3 = time3;
	}

	public long getTime4() {
		return time4;
	}

	public void setTime4(long time4) {
		this.time4 = time4;
	}

	public long getTime5() {
		return time5;
	}

	public void setTime5(long time5) {
		this.time5 = time5;
	}

	public long getTime6() {
		return time6;
	}

	public void setTime6(long time6) {
		this.time6 = time6;
	}

	public long getTime7() {
		return time7;
	}

	public void setTime7(long time7) {
		this.time7 = time7;
	}

	public long getTime8() {
		return time8;
	}

	public void setTime8(long time8) {
		this.time8 = time8;
	}




}
