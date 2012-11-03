package terminal21.a2r.index;

import java.net.InetAddress ;

public class Session {
	private CharSequence title ;
	private InetAddress proxy ;
	private int port ;
	
	public Session(CharSequence title, InetAddress proxy, int port) {
		this.title = title ;
		this.proxy = proxy ;
		this.port = port ;
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
}
