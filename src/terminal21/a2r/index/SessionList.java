package terminal21.a2r.index;

import java.util.Vector ;

// singleton a la http://www.theserverside.de/singleton-pattern-in-java/
public class SessionList {
	private static SessionList instance = new SessionList() ;
	public Vector<Session> sessions ;
	
	private SessionList() {
		this.sessions = new Vector<Session>() ;
	}
	
	public static SessionList getInstance() {
		return instance ;
	}
	
	public void add(Session ses) {
		this.sessions.add(ses) ;
	}
	
	public void clear() {
		this.sessions.clear() ;
		
	}
}