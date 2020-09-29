package control;

import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import model.Attack;
import model.ConnectionPossible;
import model.GameStatus;
import model.Generic;
import model.ShotStatus;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import view.GameWindow;

public class GameController implements OnMessageListener{

	private GameWindow view;
	private TCPConnection connection;

	private int weak;
	private Button[] buttons;

	public GameController(GameWindow view) {
		buttons = new Button[9];
		this.view = view;
		buttons[0] = view.getBTT0();
		buttons[1] = view.getBTT1();
		buttons[2] = view.getBTT2();
		buttons[3] = view.getBTT3();
		buttons[4] = view.getBTT4();
		buttons[5] = view.getBTT5();
		buttons[6] = view.getBTT6();
		buttons[7] = view.getBTT7();
		buttons[8] = view.getBTT8();
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);

		assignWeakSpot();
		notReady();
		prepareAttack();
		resetColors();
		Gson gson = new Gson();
		ConnectionPossible cp = new ConnectionPossible(true);
		String confirmation = gson.toJson(cp);
		view.setOnCloseRequest(
				(e)->{
					TCPConnection.getInstance().closeSocket();
				}
				);

		connection.getEmisor().sendMessage(confirmation);

	}



	@Override
	public void OnMessage(String msg) {
		Gson gson = new Gson();
		Generic gen = gson.fromJson(msg, Generic.class);
		switch (gen.getType()) {

		case "GameStatus":
			GameStatus gs = gson.fromJson(msg, GameStatus.class);
			if(gs.getActualStatus().equalsIgnoreCase("Ready")) {
				ready();
				resetColors();

			}
			break;

		case "Attack":
			Attack a = gson.fromJson(msg, Attack.class);
			int target = a.getTo();
			ShotStatus ss;
			ready();
			if(target == weak) {
				ss = new ShotStatus(true);
				String notice = gson.toJson(ss);
				connection.getEmisor().sendMessage(notice);
				Platform.runLater(

						()->{
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.initStyle(StageStyle.UTILITY);
							alert.setTitle("Information");
							alert.setHeaderText("¡TE HAN DADO!");
							alert.setContentText("Has perdido :(");

							alert.showAndWait();
							view.close();
						}

						);
			}
			break;
		case "ShotStatus":
			ShotStatus ss2 = gson.fromJson(msg, ShotStatus.class);
			if(ss2.isBullseye()) {
				Platform.runLater(

						()->{
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.initStyle(StageStyle.UTILITY);
							alert.setTitle("Information");
							alert.setHeaderText("¡HAS GANADO!");
							alert.setContentText("Que buena puntería tienes :D");

							alert.showAndWait();
							notReady();
						}

						);
			}

		}
	}

	public void assignWeakSpot() {
		weak = (int) Math.floor(Math.random()*(8-0+1)+0); 
	}

	public void notReady() {
		for(int i = 0; i < buttons.length;i++) {
			buttons[i].setDisable(true);
		}
		Platform.runLater(

				()->{
					view.getWaitLabel().setVisible(true);
				}

				);
	}
	public void ready() {
		for(int i = 0; i < buttons.length;i++) {
			buttons[i].setDisable(false);
		}
		Platform.runLater(

				()->{
					view.getWaitLabel().setVisible(false);
				}

				);
	}
	public void prepareAttack() {
		for(int i = 0; i < buttons.length;i++) {
			Attack attack = new Attack(i);
			Gson gson = new Gson();
			String msg = gson.toJson(attack);
			Button actual = buttons[i];
			actual.setOnAction(

					(e) -> {
						connection.getEmisor().sendMessage(msg);
						notReady();
						Platform.runLater(

								()->{
									actual.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
								}

								);

					}

					);
		}
	}
	public void resetColors() {
		for(int i = 0; i < buttons.length;i++) {
			Button actual = buttons[i];
			Platform.runLater(

					()->{
						actual.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
					}

					);

		}
	}
}
