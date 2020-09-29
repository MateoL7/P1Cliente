package control;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import model.ConnectionPossible;
import model.User;
import view.GameWindow;
import view.ConnectionWindow;

public class ConnectionController implements TCPConnection.OnConnectionListener{

	private ConnectionWindow view;
	private TCPConnection connection;


	public ConnectionController(ConnectionWindow view) {
		this.view = view;
		init();
	}

	public void init() {
		view.getIngresarBtt().setOnAction(

				(e)->{
					connection = TCPConnection.getInstance();
					connection.setConnectionListener(this);
					connection.start();
				}

				);
	}

	@Override
	public void onConnection(String msg) {
		Gson gson = new Gson();
		ConnectionPossible cp = gson.fromJson(msg, ConnectionPossible.class);
		if(!cp.isPossible()) {
			connection.closeSocket();
			Platform.runLater(

					()->{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.initStyle(StageStyle.UTILITY);
						alert.setTitle("Information");
						alert.setHeaderText("¡Sala llena!");
						alert.setContentText("Por favor intenta más tarde");

						alert.showAndWait();
					}

					);
			
			TCPConnection.newInstance();
			
		}else {
			//Estamos conectados
			//No se puede usar metodos con resultados graficos en un hilo que no sea el principal
			Platform.runLater(

					()->{
						GameWindow chatWindow = new GameWindow();
						chatWindow.show();
						view.close();
					}

					);
		}
	}

	@Override
	public void proceed() {
		User u = new User();
		Gson gson = new Gson();
		String json = gson.toJson(u);
		connection.getEmisor().sendMessage(json);
	}

	@Override
	public void sendConfirmation(boolean possible) {
		Gson gson = new Gson();
		ConnectionPossible cp = new ConnectionPossible(possible);
		String json = gson.toJson(cp);
		connection.getEmisor().sendMessage(json);
	}
	
	

}
