//Del Freo, Di Dio, Parenti, Simoncini

package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Commands{

	private static int sequence=1;

	private synchronized int getSequence() {
		return Commands.sequence++;
	}
	
	/** This function return take off command
	 * 
	 * @param sequenceNumber Increasing number
	 * @return Return the sequence of bytes that identify take off command
	 */

	public synchronized byte[] takeOff() {
		String command = "AT*REF="+(this.getSequence())+",290718208\r";
		System.out.println(command);
		return command.getBytes();
	}

	/**
	 * 
	 * @param sequenceNumber
	 * @return
	 */

	public byte[] land() {
		String command = "AT*REF="+(this.getSequence())+",290717696\r";
		return command.getBytes();
	}

	/**
	 * 
	 * @param sequenceNumber
	 * @return
	 */

	public synchronized byte[] emergency() {
		String command = "AT*REF="+(this.getSequence())+",290717952\r";
		return command.getBytes();
	}

	/**
	 * 
	 * @param command
	 * @param IP
	 * @param port
	 * @return
	 * @throws IOException
	 */

	public synchronized boolean sendCommand(byte[] command, InetAddress IP, int port) {
		try {
			 DatagramSocket clientSocket = new DatagramSocket();
			 DatagramPacket sendPacket = new DatagramPacket(command, command.length, IP, port);
			clientSocket.send(sendPacket);
			clientSocket.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param sequenceNumber
	 * @param IP
	 * @param port
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public void watchDog(InetAddress IP, int port) throws IOException, InterruptedException {
		 DatagramSocket clientSocket = new DatagramSocket();
		String command = "";
		while (true) {
			command = "AT*COMWDG="+(this.getSequence())+"\r";
			DatagramPacket sendPacket = new DatagramPacket(command.getBytes(), command.length(), IP, port);
			clientSocket.send(sendPacket);
			Thread.sleep(30);
		}
	}
	
	

}
