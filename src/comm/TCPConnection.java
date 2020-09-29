package comm;


import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import model.Attack;



//CLASE OBSERVADA

public class TCPConnection extends Thread {

	//SINGLETON
	private static TCPConnection instance;

	public static synchronized TCPConnection getInstance() {
		if(instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	
	public static synchronized void newInstance() {
		instance = new TCPConnection();
	}


	private Socket socket;
	private String ip = "127.0.1.1";
	private int puerto = 5000;
	private Emisor emisor;
	private Receptor receptor;
	private OnMessageListener listener;
	private OnConnectionListener connectionListener;



	private TCPConnection() {

	}
	@Override
	public void run() {
		try {
			System.out.println("Enviando solicitud");
			socket = new Socket(ip, puerto);
			System.out.println("Solicitud aceptada");

			receptor = new Receptor(socket.getInputStream());
			receptor.setConnectionListener(connectionListener);
			receptor.start();

			emisor = new Emisor(socket.getOutputStream());
			
			connectionListener.proceed();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListenerOfMessages(OnMessageListener listener) {
		this.receptor.setListener(listener);
	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	public Emisor getEmisor() {
		return this.emisor;
	}

	public interface OnConnectionListener{
		public void onConnection(String msg);
		public void proceed();
		public void sendConfirmation(boolean possible);
	}

	public void closeSocket() {
		try {
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
