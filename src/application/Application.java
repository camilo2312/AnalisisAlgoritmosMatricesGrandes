package application;

import java.io.IOException;
import java.util.ArrayList;

import controller.ApplicationController;
import controller.InfoAppController;
import controller.StatisticsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Algorithm;
import model.AlgorithmTable;
import model.ExecutesAlgorithms;

public class Application extends javafx.application.Application {

	private Stage primaryStage;
	private static ExecutesAlgorithms executes = new ExecutesAlgorithms();

	public static void main(String[] args) {
		executes.registerAlgorithms();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Ejecución de algoritmos");
		viewWindowPrincipal();
	}

	/*
	 * Método que permite visualizar la pantalla principal
	 * de la aplicación
	 * */
	private void viewWindowPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Application.class.getResource("/view/ApplicationView.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			ApplicationController appController = loader.getController();
			appController.setAplicacion(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Método que permite ejecutar los algoritmos de
	 * las matrices
	 * */
	public void executeAlgorithms() {
		executes.executeAlgorithms();
	}

	/**
	 * Método que permite obtener la lista de
	 * algoritmos a mostrar en la tabla
	 * @return
	 */
	public ArrayList<AlgorithmTable> getLstAlgorithmsTable() {
		return executes.getLstAlgorithmsTable();
	}

	/**
	 * Método que permite calcular los datos
	 * que se utilizaran para las estadisticas de los algoritmos
	 * una vez se termine de ejecutar todos los algoritmos
	 */
	public void calculateData() {
		executes.calculateData();
	}

	/**
	 * Método que permite abrir la venta con las estadisticas recolectadas por la
	 * ejecución de los algoritmos
	 */
	public void showWindowStatistics() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Application.class.getResource("/view/StatisticsView.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			StatisticsController statisticsController = loader.getController();
			statisticsController.setAplicacion(this);

			Scene scene = new Scene(rootLayout);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(primaryStage);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Método que permite abrir la venta con la información de la app
	 */
	public void showWindowInfoApp() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Application.class.getResource("/view/InfoAppView.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			InfoAppController infoAppController = loader.getController();
			infoAppController.setApplication(this);

			Scene scene = new Scene(rootLayout);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(primaryStage);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Método que permite obtener los datos de los
	 * algoritmos
	 * @return
	 */
	public ArrayList<Algorithm> getLstAlgorithms() {
		return executes.getLstAlgorithms();
	}
}
