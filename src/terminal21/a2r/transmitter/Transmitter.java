package terminal21.a2r.transmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress ;
import java.net.DatagramSocket ;
import java.net.SocketException ;

public class Transmitter {
	
	private DatagramSocket socket ;
	
	public Transmitter(InetAddress host, Integer port) {
		try {
			this.socket = new DatagramSocket() ;
			this.socket.connect(host, port) ;
		} catch (SocketException e) {
			// ToDo: Handle Exception
		}
	}
	
	public void emit(Entity[] entities) {
		if (this.socket.isConnected()) {
			
			byte data[] = new byte[entities.length * 4] ;
			
			for (int i=0; i<entities.length; i++) {
				System.arraycopy(entities[i].toPackage(), 0, data, i * 4, 4) ;
			}
			
			DatagramPacket pack = new DatagramPacket(data, data.length) ;
			try {
				this.socket.send(pack) ;
			}
			catch (IOException e) {
				// ToDo: catch exception
			}
		}
		
	}
}
