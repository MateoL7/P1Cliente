package view;



import java.io.IOException;

import control.GameController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class GameWindow extends Stage{

	//Components
    private Button BTT0;
    private Button BTT1;
    private Button BTT2;
    private Button BTT3;
    private Button BTT4;
    private Button BTT5;
    private Button BTT6;
    private Button BTT7;
    private Button BTT8;
    private Label WaitLabel;



	//Controller
	private GameController control;

	//Constructor
	public GameWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent root = loader.load();

			
			BTT0 = (Button) loader.getNamespace().get("BTT0");
			BTT1 = (Button) loader.getNamespace().get("BTT1");
			BTT2 = (Button) loader.getNamespace().get("BTT2");
			BTT3 = (Button) loader.getNamespace().get("BTT3");
			BTT4 = (Button) loader.getNamespace().get("BTT4");
			BTT5 = (Button) loader.getNamespace().get("BTT5");
			BTT6 = (Button) loader.getNamespace().get("BTT6");
			BTT7 = (Button) loader.getNamespace().get("BTT7");
			BTT8 = (Button) loader.getNamespace().get("BTT8");
			WaitLabel = (Label) loader.getNamespace().get("WaitLabel");


			Scene scene = new Scene(root);
			this.setScene(scene);
			
			control = new GameController(this);

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public GameController getControl() {
		return control;
	}

	public Button getBTT0() {
		return BTT0;
	}

	public Button getBTT1() {
		return BTT1;
	}

	public Button getBTT2() {
		return BTT2;
	}

	public Button getBTT3() {
		return BTT3;
	}

	public Button getBTT4() {
		return BTT4;
	}

	public Button getBTT5() {
		return BTT5;
	}

	public Button getBTT6() {
		return BTT6;
	}

	public Button getBTT7() {
		return BTT7;
	}

	public Button getBTT8() {
		return BTT8;
	}

	public Label getWaitLabel() {
		return WaitLabel;
	}
	
	

}
