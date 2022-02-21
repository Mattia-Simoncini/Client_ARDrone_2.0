//Del Freo, Di Dio, Parenti, Simoncini

package client;

import java.io.*;
import java.net.*;

class Client{
	
	public static void main(String args[]) throws Exception {
		int port = 5556; //Porta per inviare comandi AT
		InetAddress IP = InetAddress.getByName("192.168.1.1"); // Indirizzo IP del drone
		byte[] buffer_IN = new byte[1024];
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// creazione del socket 
		DatagramSocket clientSocket = new DatagramSocket();

		boolean exit=false, result;
		int choise;
		
		CustomizedThread customizedThread = new CustomizedThread();
		customizedThread.start();
		Commands commands = new Commands();
		 // Avviamo il watchdog del drone
		
		do {
			System.out.println("Premi 1 per alzare il drone");
			System.out.println("Premi 2 per far atterrare il drone");
			System.out.println("Premi 3 per il comando di emergenza");
			// prendiamo il valore di input
			try {
				choise = Integer.parseInt(input.readLine());
				switch (choise) {
				case 1:
					System.out.println(IP);
					System.out.println(port);
					result = commands.sendCommand(commands.takeOff(), IP, port);
					if (result) 
						System.out.println("Operazione di take off in corso..."); 
					else
						commands.sendCommand(commands.emergency(), IP, port);
					break;
				case 2:
					result = commands.sendCommand(commands.land(), IP, port);
					
					if (result) 
						System.out.println("Operazione di land off in corso..."); 
					else
						commands.sendCommand(commands.emergency(), IP, port);
					break;
				case 3:
					result = commands.sendCommand(commands.emergency(), IP, port);
					
					if (result) 
						System.out.println("Operazione di emergency in corso..."); 
					else
						commands.sendCommand(commands.emergency(), IP, port);
					break;
				default:
					System.out.println("Comando non valido");
					break;
				}
			} catch (Exception e) {
				System.out.println("Si è verificato un problema");
				exit=true;
			} 
		} while (exit==false);
		if (exit){
			result = commands.sendCommand(commands.land(), IP, port);
			
			if (result) 
				System.out.println("Operazione di land in corso..."); 
			else
				commands.sendCommand(commands.emergency(), IP, port);
		}

		// ricezione del dato dal server
		DatagramPacket receivePacket = new DatagramPacket(buffer_IN, buffer_IN.length);
		clientSocket.receive(receivePacket);
		String ricevuto = new String(receivePacket.getData());

		System.out.println("Abbiamo ricevuto -> " + ricevuto);    

		

		// termine elaborazione
		clientSocket.close();
	}
}
