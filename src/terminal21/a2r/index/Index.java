package terminal21.a2r.index;

import java.util.ArrayList;

// singleton a la http://www.theserverside.de/singleton-pattern-in-java/
public class Index {
	private static Index instance = new Index() ;
	private ArrayList<Session> sessions ;
	private Session currentSession ;
	
	private Index() {
		this.sessions = new ArrayList<Session>() ;
	}
	
	public static Index getInstance() {
		return instance ;
	}
	
	public ArrayList<Session> getSessions() {
		return this.sessions ;
	}
	
	public void addSession(Session ses) {
		this.sessions.add(ses) ;
	}
	
	public Session getCurrentSession() {
		return this.currentSession ;
	}
	
	public void setCurrentSession(Session session) {
		this.currentSession = session ;
	}
		
	public void clear() {
		this.sessions.clear() ;
		
	}
}