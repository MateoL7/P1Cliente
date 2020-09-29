package view;

import java.io.IOException;

import control.ConnectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ConnectionWindow extends Stage{

	//Components
	private Button IngresarBtt;

	//Controller
	public ConnectionController cc;

	//Constructor
	public ConnectionWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnectionWindow.fxml"));
			Parent root = loader.load();
			
			IngresarBtt = (Button) loader.getNamespace().get("IngresarBtt");

			Scene scene = new Scene(root);
			this.setScene(scene);
			
			cc = new ConnectionController(this);

		} catch(IOException e) {
			e.printStackTrace();
		}
	}


	public Button getIngresarBtt() {
		return IngresarBtt;
	}
	
	public Stage getStage() {
		return this;
	}
}
