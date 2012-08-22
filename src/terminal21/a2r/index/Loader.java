package terminal21.a2r.index;

import java.util.Observable;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.util.Log;

public class Loader extends Observable implements Runnable {
	
	public static Integer ERROR = -1 ;
	public static Integer INIT = 0 ;
	public static Integer FETCH = 1 ;
	public static Integer PARSE = 2 ;
	public static Integer DONE = 10 ;
	
	private CharSequence index_server = "index.addicted2random.eu" ;
	private SessionList sessions = SessionList.getInstance() ;
	
	public Loader() {
	}
	
	public Loader(CharSequence server) {
		this.index_server = server ;
	}
	
	public void run() {
		
		String json = fetch_json() ;
		if (json == "") return ;
		pause(500) ;
		parse_json(json) ;
		pause(500) ;
		done() ;
		
	}
	
	private String fetch_json() {
		
		String json = "" ;
		URL url ;
		HttpURLConnection con ;
		
		setChanged() ;
		notifyObservers(Loader.FETCH) ;

		// before edit this read http://android-developers.blogspot.de/2011/09/androids-http-clients.html
		System.setProperty("http.keepAlive", "false");
			
		try {
			url = new URL("http", this.index_server.toString(), 7000, "/show.json") ;
		} catch (MalformedURLException e) {
			error(e) ;
			return json ;
		}
		
		try {
			con = (HttpURLConnection)url.openConnection() ;
		} catch (IOException e) {
			error(e) ;
			return json ;
		}
		
		try {
			InputStream in = new BufferedInputStream(con.getInputStream()) ;
			json = readStream(in) ;
		} catch (IOException e) {
			error(e) ;
		} finally {
		    con.disconnect();
		}
		
		return json ;
	}
	
	private void parse_json(String json) {
		
		setChanged() ;
		notifyObservers(Loader.PARSE) ;
		
		this.sessions.clear() ;
		
		Session s ;
		s = new Session("Hallo") ;
		this.sessions.add(s) ;
		s = new Session("Welt") ;
		this.sessions.add(s) ;
	}
	
	private void done() {
		setChanged() ;
		notifyObservers(Loader.DONE) ;
	}
	
	private String readStream(InputStream is) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			int i = is.read();
			while(i != -1) {
				bo.write(i);
				i = is.read();
			}
			return bo.toString();
	    } catch (IOException e) {
	    	error(e) ;
	    	return "";
	    }
	}

	private void error(Exception e) {
		Log.e("A2R Index Loader", e.toString()) ;
		setChanged() ;
		notifyObservers(Loader.ERROR) ;
	}
	
	private void pause(int time) {
		try {
			Thread.sleep(time) ;
		} catch(Exception e) {
		}
	}
}
