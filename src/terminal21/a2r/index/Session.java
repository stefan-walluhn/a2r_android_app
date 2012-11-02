package terminal21.a2r.index;

import java.util.Vector ;
import java.net.InetAddress ;

public class Session {
	private CharSequence title ;
	private InetAddress proxy ;
	private Integer port ;
	private Vector<Sensor> sensors ;
	
	public Session(CharSequence title, InetAddress proxy, Integer port) {
		this.title = title ;
		this.proxy = proxy ;
		this.port = port ;
		this.sensors = new Vector<Sensor>() ;
	}
	
	public CharSequence getTitle() {
		return this.title ;
	}
	
	public InetAddress getProxy() {
		return this.proxy ;
	}
	
	public Integer getPort() {
		return this.port ;
	}
	
	public void addSensor(Sensor sensor) {
		this.sensors.add(sensor) ;
	}
	
	public Vector<Sensor> getSensors() {
		return this.sensors ;
	}
	
	public boolean hasSensors() {
		return !sensors.isEmpty() ;
	}
}
