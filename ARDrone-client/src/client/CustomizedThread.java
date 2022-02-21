//Del Freo, Di Dio, Parenti, Simoncini

package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CustomizedThread extends Thread {

	private int port;
	private InetAddress IP;
	
	public void setPort() {
		this.port=5556;
	}
	
	public void setIP() throws UnknownHostException {
		this.IP= InetAddress.getByName("192.168.1.1");
	}
	
	public CustomizedThread () throws UnknownHostException {
		setPort();
		 setIP();
	}
	
	/**
	 * 
	 */
	
	@Override
	public void run() {
		try {
			Commands commands = new Commands();
			commands.watchDog(this.IP, this.port);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
