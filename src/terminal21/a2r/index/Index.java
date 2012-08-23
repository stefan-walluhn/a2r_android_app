package terminal21.a2r.index;

import java.util.Vector ;

// singleton a la http://www.theserverside.de/singleton-pattern-in-java/
public class Index {
	private static Index instance = new Index() ;
	private Vector<Session> sessions ;
	
	private Index() {
		this.sessions = new Vector<Session>() ;
	}
	
	public static Index getInstance() {
		return instance ;
	}
	
	public Vector<Session> getSessions() {
		return this.sessions ;
	}
	
	public void addSession(Session ses) {
		this.sessions.add(ses) ;
	}
	
	public void clear() {
		this.sessions.clear() ;
		
	}
}