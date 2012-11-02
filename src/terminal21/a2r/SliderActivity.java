package terminal21.a2r;

import terminal21.a2r.R;
import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;

import terminal21.a2r.index.Index ;
import terminal21.a2r.index.Session ;
import terminal21.a2r.sensor.SensorBox;
import terminal21.a2r.transmitter.Entity;
import terminal21.a2r.transmitter.Transmitter ;

public class SliderActivity extends Activity {
	
	private PowerManager.WakeLock wl ;
	public Transmitter transmitter ;
	private Thread transmitterThread ;
	private SensorBox sensors ;	
	private Session session ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.slider);
		
		this.wl = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, getClass().getName()) ;
		
		this.session = Index.getInstance().getCurrentSession() ;
		this.sensors = new SensorBox(this) ;
		this.transmitter = new Transmitter(this.session.getProxy(), this.session.getPort()) ;
		this.transmitterThread = new Thread(this.transmitter) ;
	}
	
	@Override
	protected void onStart() {
		super.onStart() ;
		this.wl.acquire() ;
	}
	
	@Override
	protected void onStop() {
		super.onStop() ;
		this.wl.release() ;
	}
	
	@Override
	protected void onResume() {
		super.onResume() ;
		this.sensors.startSensors() ;
		//this.transmitterThread.start() ;
	}
	
	@Override
	protected void onPause() {
		super.onPause() ;
		this.sensors.stopSensors() ;
		//this.transmitterThread.stop() ;
	}
}