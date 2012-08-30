package terminal21.a2r.index;

public class Sensor {
	
	private CharSequence name = null ;
	private CharSequence type ;
	private int target_port = 0 ;
	private int query_port = 0 ;
	
	public Sensor(CharSequence name, CharSequence type) {
		init(name, type, 0, 0) ;
	}
	
	public Sensor(CharSequence name, CharSequence type, int target_port) {
		init(name, type, target_port, 0) ;
	}
	
	public Sensor(CharSequence name, CharSequence type, int target_port, int query_port) {
		init(name, type, target_port, query_port) ;
	}
	
	private void init (CharSequence name, CharSequence type, int target_port, int query_port) {
		this.name = name ;
		this.type = type ;
		this.target_port = target_port ;
		this.query_port = query_port ;
	}
	
	public CharSequence getName() {
		return this.name ;
	}
	
	public void setTargetPort(int port) {
		this.target_port = port ;
	}
	
	public void setQueryPort(int port) {
		this.query_port = port ;
	}
	
	public int getTargetPort() {
		return this.target_port ;
	}
	
	public int getQueryPort() {
		return this.query_port ;
	}
	
	public CharSequence getType() {
		return this.type ;
	}
}
