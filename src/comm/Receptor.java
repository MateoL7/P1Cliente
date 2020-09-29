package comm;
import java.io.*;

import comm.TCPConnection.OnConnectionListener;


public class Receptor extends Thread {

	private InputStream is;

	public OnMessageListener listener;

	public OnConnectionListener listenerConnection;

	public Receptor(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(this.is));

			while(true) {
				String msg = br.readLine();
				if(listener!=null) { 
					listener.OnMessage(msg);
				}
				else if(listenerConnection != null) {
					listenerConnection.onConnection(msg);
				}
				else System.out.println("No hay observer");

			}
		} catch(IOException e ) {
			
		}
	}
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public interface OnMessageListener{
		public void OnMessage(String msg);
	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.listenerConnection = connectionListener;

	}
}