package by.zti.dnd.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.utils.Serealizator;

public class Connector {
	public static ServerSocket server;
	public static boolean running = true;
	public static int port = 1487;
	

	public static void itit() throws IOException, ClassNotFoundException{
		server = new ServerSocket(port);
		while(running){
			Socket socket = server.accept();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			String token = (String) in.readObject();
			CharSheet sheet = (CharSheet) Serealizator.deSerealize("res/sheets/"+token+".chr");
			out.writeObject(sheet);
			in.close();
			out.close();
			socket.close();
		}
	}
}
