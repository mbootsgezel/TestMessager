import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client implements Runnable{
	
	private int port;
	private String host;
	private Socket socket;
	private DataOutputStream os = null;
    private DataInputStream is = null;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		System.out.println("Client - New client created");
	}

	public void start(){
		this.run();
	}
	
	@Override
	public void run() {
		System.out.println("Client - Client running");
		try {
			System.out.println("Client - Connecting to " + host + ":" + port);
			
			socket = new Socket(host, port);
			os = new DataOutputStream(socket.getOutputStream());
			is = new DataInputStream(socket.getInputStream());
			
			System.out.println("Client - Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
			
		} catch (IOException e) {
			System.err.println("Client - Cant connect to the server: " + e.getMessage());
			//e.printStackTrace();
		}
		
		
	}

}
