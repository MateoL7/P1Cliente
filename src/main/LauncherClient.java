package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ConnectionWindow;

public class LauncherClient extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		ConnectionWindow c = new ConnectionWindow();
		c.show();
	}

}
