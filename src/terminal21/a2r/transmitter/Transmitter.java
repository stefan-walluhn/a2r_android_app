package terminal21.a2r.transmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress ;
import java.net.DatagramSocket ;
import java.net.SocketException ;
import java.util.ArrayList;
import java.util.Iterator;

public class Transmitter implements Runnable {
	
	private static Transmitter instance = new Transmitter() ;
	private DatagramSocket socket ;
	private ArrayList<Entity> entityCache ;
	private Thread thread ;
	
	private Transmitter() {
		this.entityCache = new ArrayList<Entity>() ;		
	}
	
	public static Transmitter getInstance() {
		return instance ;
	}
	
	public boolean connect(InetAddress host, int port) {
		try {
			this.socket = new DatagramSocket() ;
			this.socket.connect(host, port) ;
		} catch (SocketException e) {
			return false ;
		}
		return true ;
	}
	
	public void disconnect() {
		this.socket.disconnect() ;
	}
	
	public synchronized void start() {
		if (thread == null) {
			thread = new Thread(this) ;
			thread.start() ;
		}
	}
	
	private void stop() {
		this.thread = null ;
	}
	
	public synchronized void interrupt() {
		if (thread != null) {
			thread.interrupt() ;
			this.stop() ;
		}
	}
	
	public void run() {
		while (thread != null) {
			try {
				Thread.sleep(200) ;
				emit() ;
			} catch (InterruptedException e) {
				break ;
			}
		}
	}
	
	public synchronized void addEntity(Entity entity) {
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
	
	public synchronized void emit() {
		
		if (this.entityCache.isEmpty()) return ;
		if (!this.socket.isConnected()) return ;
		
		Object entities[] = this.entityCache.toArray() ;
		this.entityCache.clear() ;
					
		byte data[] = new byte[entities.length * 4] ;
		
		for (int i=0; i<entities.length; i++) {
			System.arraycopy(((Entity)entities[i]).toPackage(), 0, data, i * 4, 4) ;
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
