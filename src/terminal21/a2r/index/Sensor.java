package terminal21.a2r.index;

public class Sensor {
	
	public static final int HARMONIC = 1 ;
	public static final int FFT = 2 ;
	
	private CharSequence name = null ;
	private int type ;
	private int target_port = 0 ;
	private int query_port = 0 ;
	
	public Sensor(CharSequence name, int type) {
		init(name, type, 0, 0) ;
	}
	
	public Sensor(CharSequence name, int type, int target_port) {
		init(name, type, target_port, 0) ;
	}
	
	public Sensor(CharSequence name, int type, int target_port, int query_port) {
		init(name, type, target_port, query_port) ;
	}
	
	private void init (CharSequence name, int type, int target_port, int query_port) {
		this.name = name ;
		this.type = type ;
		this.target_port = target_port ;
		this.query_port = target_port ;
	}
	
	public CharSequence getName() {
		return this.name ;
	}
	
	public int getTargetPort() {
		return this.target_port ;
	}
	
	public int getQueryPort() {
		return this.query_port ;
	}
	
	public int getType() {
		return this.type ;
	}
}
