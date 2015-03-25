package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Server implements Runnable {
	
	private static Server instance;
	
	private static int score;
	private static int health;
	private static int stage;

	private static int uniqueId;
	private int port;
	private int uniqueid;
	private boolean keepGoing;
	
	private ServerSocket serverSocket;
	
	private Socket socket = null;
	
	private ObjectInputStream in;
	
	private ArrayList <ClientConnection> clients;
	private CurrentDate d = new CurrentDate();

	private Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		keepGoing = true;
		clients = new ArrayList <ClientConnection>();
		try{
			
			serverSocket = new ServerSocket(port);
			
			display("Waiting for Clients on port " + port + ".");
			
			display("-------------- Server initiated --------------");
			
			while(keepGoing){

				clients.add(new ClientConnection(serverSocket.accept(), ++uniqueid));
				
				if(!keepGoing){
					break;
				}
			}
			

		} catch (IOException e) {
			display(e.toString());
		}
		
	}
	
	public static int getScore(){
		return score;
	}
	
	public static int getHealth(){
		return health;
	}
	
	public static int getStage(){
		return stage;
	}
	
	public static void addScore(int newscore){
		score = score + newscore;
	}
	
	public static void setHealth(int newhealth){
		health = newhealth;
		System.out.println("health updated: " + getHealth());
	}
	
	public static void setStage(int newstage){
		stage = newstage;
	}
	
	public boolean broadcastClick(Entity e){
		try {
			for(int i = 0; i < clients.size(); i++){
				clients.get(i).sendClick(e);
			}
			addScore(1);
			return true;
		} catch (Exception exc){
			return false;
		}
	}

	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {

		}
	}

	public void display(String s){
		System.out.println(d.now() + " - Server - " + s);
	}
	
	public static Server newInstance(int port){
		if(instance == null){
			instance = new Server(port);
		}
		return instance;
	}
	
	public static Server getInstance(){
		return instance;
	}

}
