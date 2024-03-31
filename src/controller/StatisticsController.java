package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Algorithm;
import model.Time;
public class StatisticsController {

	private Application application;
	ObservableList<Algorithm> lstAlgorithms = FXCollections.observableArrayList();
	// Valores para eje x de la grafica de tiempos de ejecuci�n
	private ObservableList<String> lstSizesMatrixes = FXCollections.observableArrayList();

	// Valores para el eje x de la gr�fica de promedios vs algoritmos
	private ObservableList<String> lstAxisXTimes = FXCollections.observableArrayList();

	@FXML
    private LineChart<String, Long> lineChartPoints;

	@FXML
	private CategoryAxis sizesMatrixesLineChart;

	@FXML
    private TableColumn<Algorithm, Double> columnDevitation;

    @FXML
    private TableView<Algorithm> tablePoints;

    @FXML
    private TableColumn<Algorithm, Double> columnRank;

    @FXML
    private TableColumn<Algorithm, String> columnName;

    @FXML
    private TableColumn<Algorithm, Double> columnAverage;

    @FXML
    private TableColumn<Algorithm, Double> columnVariance;

    @FXML
    private Button btnGraphAverage;

    @FXML
    private Button btnGraphAverageSorted;

    @FXML
    private BarChart<String, Long> barChartsortedAverage;

    @FXML
    private BarChart<String, Long> barchartAverages;

    @FXML
    private CategoryAxis xAxisAveragesSorted;

    @FXML
    private CategoryAxis xAxisAverages;

    @FXML
    void initialize() {
    	this.columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    	this.columnAverage.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
    	this.columnDevitation.setCellValueFactory(new PropertyValueFactory<>("standarDeviationTime"));
    	this.columnRank.setCellValueFactory(new PropertyValueFactory<>("rankTime"));
    	this.columnVariance.setCellValueFactory(new PropertyValueFactory<>("varianceTime"));

    	setDataAxis();

		// Escucha los cambios de seleccion de elementos en la tabla de estad�sticas.
        tablePoints.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> graphAlgorithm(newValue));

    }

    /**
     * M�todo que permite setear los tama�os de las matrices
     * al eje x de la gr�fica e el tab uno y tambi�n setear los valores
     * para el x de la gr�fica del tab dos
     */
    private void setDataAxis() {
    	int n = 16;
    	for (int i = 0; i < 8; i++) {
			n = n * 2;
			lstSizesMatrixes.add(n + "");
		}
		sizesMatrixesLineChart.setCategories(lstSizesMatrixes);

		for (int i = 0; i < 15; i++) {
			lstAxisXTimes.add(i + "");
		}

		xAxisAverages.setCategories(lstAxisXTimes);
    }

    /**
     * M�todo que permite graficar el algoritmo seleccionado de la tabla de
     * estadisticas
     * @param selectedAlgorithm
     */
    private void graphAlgorithm(Algorithm selectedAlgorithm) {
    	lineChartPoints.getData().clear();

		XYChart.Series<String, Long> series = new XYChart.Series<>();

		for (Algorithm algorithm : lstAlgorithms) {
			if (algorithm == selectedAlgorithm) {
				int i = 0;
				for (Time time : algorithm.getLstTimes()) {
					series.getData().add(new XYChart.Data<>(lstSizesMatrixes.get(i)+"", time.getTime()));
					i++;
				}
			}
		}

		lineChartPoints.getData().add(series);

    }

    /**
     * M�todo que permite obtener los datos
     * estadisticos de los algoritmos
     */
    private void getDataAlgorithms() {
		 lstAlgorithms.clear();
		 lstAlgorithms.addAll(application.getLstAlgorithms());

		 tablePoints.setItems(lstAlgorithms);
		 tablePoints.refresh();
    }

    /**
     * M�todo que permite gr�fica los promedios
     * de los tiempos de ejecuci�n de todos los algoritmos
     * de manera ordenada (menor a mayor)
     * @param event
     */
    @FXML
    void graphAverageSorted(ActionEvent event) {
    	ArrayList<Algorithm> lstSortedAlgortihm = new ArrayList<>();
		lstSortedAlgortihm.addAll(lstAlgorithms);

		Collections.sort(lstSortedAlgortihm, new Comparator<Algorithm>() {
		    @Override
		    public int compare(Algorithm o1, Algorithm o2) {
		        return o1.getAverageTime().compareTo(o2.getAverageTime());
		    }
		});

		ObservableList<String> xAxis = FXCollections.observableArrayList();

		for (Algorithm algorithm : lstSortedAlgortihm) {
			xAxis.add(algorithm.getId()+"");
		}

		xAxisAveragesSorted.setCategories(xAxis);

		barChartsortedAverage.getData().clear();

		XYChart.Series<String, Long> series = new XYChart.Series<>();

		for (Algorithm algorithm : lstSortedAlgortihm) {
			series.getData().add(new XYChart.Data<>(algorithm.getId()+"", Double.valueOf(algorithm.getAverageTime()).longValue()));
		}

		barChartsortedAverage.getData().add(series);
    }

    /**
     * M�todo encargado de graficar los promedios obtenidos
     * despu�s de la ejecuci�n de cada algoritmo
     * @param event
     */
    @FXML
    void graphAverage(ActionEvent event) {
    	barchartAverages.getData().clear();
		XYChart.Series<String, Long> series = new XYChart.Series<>();

		for (Algorithm algorithm : lstAlgorithms) {
			series.getData().add(new XYChart.Data<>(algorithm.getId()+"", Double.valueOf(algorithm.getAverageTime()).longValue()));
		}

		barchartAverages.getData().add(series);
    }

    /**
     * M�todo que asigna la clase principal application
     * al contorlador de estadisticas
     * @param application
     */
	public void setAplicacion(Application application) {
		this.application = application;
		getDataAlgorithms();

	}

}
