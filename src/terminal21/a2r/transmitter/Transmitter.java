package terminal21.a2r.transmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress ;
import java.net.DatagramSocket ;
import java.net.SocketException ;
import java.util.Iterator;
import java.util.Vector;

public class Transmitter implements Runnable {
	
	private DatagramSocket socket ;
	private Vector<Entity> entityCache ;
	
	public Transmitter(InetAddress host, Integer port) {
		try {
			this.socket = new DatagramSocket() ;
			this.socket.connect(host, port) ;
		} catch (SocketException e) {
			// ToDo: Handle Exception
		}
		this.entityCache = new Vector<Entity>() ;
	}
	
	public void run() {
		pause(200) ;
		emit() ;
	}
	
	public void addEntity(Entity entity) {
		Entity cachedEntity ;
		Iterator<Entity> entityIterator = this.entityCache.iterator() ;
	
		while (entityIterator.hasNext()) {
			cachedEntity = entityIterator.next();
			if (entity.getType() == cachedEntity.getType()) {
				cachedEntity = entity ;
				return ;
			}
		}
		this.entityCache.add(entity) ;
	}
	
	public void emit() {
		
		if (this.entityCache.isEmpty()) return ;
		if (!this.socket.isConnected()) return ;
		
		Entity cachedEntity ;
		Iterator<Entity> entityIterator = this.entityCache.iterator() ;
					
		byte data[] = new byte[this.entityCache.size() * 4] ;
		
		// Todo: This is not thread save!!!
		int i = 0 ;
		while (entityIterator.hasNext()) {
			cachedEntity = entityIterator.next() ;
			System.arraycopy(cachedEntity.toPackage(), 0, data, i * 4, 4) ;
			i++ ;
		}
		this.entityCache.clear() ;
		
		DatagramPacket pack = new DatagramPacket(data, data.length) ;
		try {
			this.socket.send(pack) ;
		}
		catch (IOException e) {
			// ToDo: catch exception
		}
	}
	
	private void pause(int time) {
		try {
			Thread.sleep(time) ;
		} catch(Exception e) {
		}
	}
}
