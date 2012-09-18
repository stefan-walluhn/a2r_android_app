package terminal21.a2r.index;

import java.util.Observable;
import java.net.InetAddress;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONArray ;
import org.json.JSONObject ;
import org.json.JSONException ;

public class Loader extends Observable implements Runnable {
	
	public static final Integer ERROR = -1 ;
	public static final Integer INIT = 0 ;
	public static final Integer FETCH = 1 ;
	public static final Integer PARSE = 2 ;
	public static final Integer DONE = 10 ;
	
	private CharSequence index_server = "index.addicted2random.eu" ;
	private Index index = Index.getInstance() ;
	
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
			url = new URL("http", this.index_server.toString(), 7000, "/") ;
		} catch (MalformedURLException e) {
			error(e) ;
			return json ;
		}
		
		try {
			con = (HttpURLConnection)url.openConnection() ;
			con.setRequestProperty("Accept", "application/json") ;
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
		
		this.index.clear() ;
		
		try {
			JSONArray jSessions = new JSONArray(json) ;
			JSONArray jSensors ;
			JSONObject jSession ;
			JSONObject jSensor ;
			Session session ;
			Sensor sensor ;
			
			for (int i=0; i<jSessions.length(); i++) {
				try {
					jSession = jSessions.getJSONObject(i) ;
					session = new Session(jSession.getString("title"), InetAddress.getByName(jSession.getString("proxy"))) ;
					jSensors = jSession.getJSONArray("sensors") ;
					
					for (int j=0; j<jSensors.length(); j++) {
						jSensor = jSensors.getJSONObject(j) ;
						
						sensor = new Sensor(jSensor.getString("name"), jSensor.getString("type")) ;
						if (jSensor.has("port")) sensor.setTargetPort(jSensor.getInt("port")) ;
						if (jSensor.has("query_port")) sensor.setQueryPort(jSensor.getInt("query_port")) ;
						session.addSensor(sensor) ;
					}	
					
					this.index.addSession(session) ;
	
				} catch (JSONException e) {
					continue ;
				}
			}
		} catch (Exception e) {
			setChanged() ;
			notifyObservers(Loader.ERROR) ;
			e.printStackTrace();
		}
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
