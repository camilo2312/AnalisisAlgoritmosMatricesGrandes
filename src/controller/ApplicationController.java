
package controller;

import application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Algorithm;
import model.AlgorithmTable;
import utilities.AdminFiles;
import utilities.MatrixTest;

public class ApplicationController {

	Application application;
	ObservableList<AlgorithmTable> lstAlgorithms = FXCollections.observableArrayList();

	/**
	 * Columnas generales de la tabla
	 */
	@FXML
    private TableColumn<Algorithm, ?> column1;

	@FXML
	private TableColumn<Algorithm, ?> column2;

	@FXML
	private TableColumn<Algorithm, ?> column3;

	@FXML
	private TableColumn<Algorithm, ?> column4;

	@FXML
    private TableColumn<Algorithm, ?> column5;

	@FXML
	private TableColumn<AlgorithmTable, ?> column6;

	@FXML
	private TableColumn<AlgorithmTable, ?> column7;

    @FXML
    private TableColumn<AlgorithmTable, ?> column8;


    /**
     * Columnas internas de cada columna general
     */
    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN1;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN2;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN3;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN4;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN5;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN6;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN7;

    @FXML
    private TableColumn<AlgorithmTable, Integer> columnN8;


    /**
     * Columnas de tiempos de ejecución
     */
    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes1;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes2;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes3;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes4;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes5;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes6;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes7;

    @FXML
    private TableColumn<AlgorithmTable, Long> columnTimes8;



    @FXML
    private Button btnEjecutar;

    @FXML
    private Button btnGenerateMatrixes;

    @FXML
    private Button btnDeleteMatrix;

    @FXML
    private Button btnInfoApp;

    @FXML
    private TableView<AlgorithmTable> tableTiempos;


    @FXML
	void initialize() {
		this.columnN1.setCellValueFactory(new PropertyValueFactory<>("size1"));
		this.columnTimes1.setCellValueFactory(new PropertyValueFactory<>("time1"));
		this.columnN2.setCellValueFactory(new PropertyValueFactory<>("size2"));
		this.columnTimes2.setCellValueFactory(new PropertyValueFactory<>("time2"));
		this.columnN3.setCellValueFactory(new PropertyValueFactory<>("size3"));
		this.columnTimes3.setCellValueFactory(new PropertyValueFactory<>("time3"));
		this.columnN4.setCellValueFactory(new PropertyValueFactory<>("size4"));
		this.columnTimes4.setCellValueFactory(new PropertyValueFactory<>("time4"));
		this.columnN5.setCellValueFactory(new PropertyValueFactory<>("size5"));
		this.columnTimes5.setCellValueFactory(new PropertyValueFactory<>("time5"));
		this.columnN6.setCellValueFactory(new PropertyValueFactory<>("size6"));
		this.columnTimes6.setCellValueFactory(new PropertyValueFactory<>("time6"));
		this.columnN7.setCellValueFactory(new PropertyValueFactory<>("size7"));
		this.columnTimes7.setCellValueFactory(new PropertyValueFactory<>("time7"));
		this.columnN8.setCellValueFactory(new PropertyValueFactory<>("size8"));
		this.columnTimes8.setCellValueFactory(new PropertyValueFactory<>("time8"));

//		this.btnEjecutar.setDisable(true);
//		this.btnDeleteMatrix.setDisable(true);

	}

    /*
     * Método que permite ejecutar los algoritmos
     * de las matrices
     * */
    @FXML
    void executeAlgorithms(ActionEvent event) {
    	application.executeAlgorithms();


    	updateTable();
    	System.out.println("Termino la ejecución");
    	application.calculateData();
    	application.showWindowStatistics();
//		updateData();
//		main.calcularDatosEstadisticos();
//		inicializarTablaEstadisticas();
    }

    /**
     * Método encargado de actualizar la tabla de los
     * algoritmos
     */
    void updateTable(){
    	tableTiempos.getItems().clear();
    	tableTiempos.setItems(getLstAlgorithmsTable());
    }

    /**
     * Método que permite obtener la lista de algoritmos
     * para mostrar en la tabla
     * @return
     */
    private ObservableList<AlgorithmTable> getLstAlgorithmsTable() {
		 lstAlgorithms.clear();
		 lstAlgorithms.addAll(application.getLstAlgorithmsTable());
		 return lstAlgorithms;
	}

    /*
     * Método que permite generar los archivos
     * con las matrices de prueba
     * */
    @FXML
    void generateMatrixFiles(ActionEvent event) {
    	MatrixTest.generateMatrixTest();
    	this.btnDeleteMatrix.setDisable(false);
    	this.btnEjecutar.setDisable(false);
    }

    /*
     * Método que permite eliminar los archivos
     * de matriz generados
     * */
    @FXML
    void deleteFilesMatrix(ActionEvent event) {
    	AdminFiles.delteFileMatrix();
    	System.out.println("Archivos eliminados correctamente");
    }

    /**
     * Método que permite abrir la ventana de
     * información de la app
     * @param event
     */
    @FXML
    void showInfoApp(ActionEvent event) {
    	this.application.showWindowInfoApp();
    }

    /*
     * Método que asigna la clase aplicación al controlador
     * */
    public void setAplicacion(Application application) {
		this.application = application;
	}
}
