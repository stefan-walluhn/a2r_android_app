package terminal21.a2r;

import terminal21.a2r.R;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.SeekBar;
import android.app.Activity;
import android.content.Context;

import terminal21.a2r.index.Index ;
import terminal21.a2r.index.Session ;
import terminal21.a2r.sensor.SensorBox;
import terminal21.a2r.sensor.SliderBox;
import terminal21.a2r.transmitter.Transmitter ;

public class SliderActivity extends Activity {
	
	private PowerManager.WakeLock wl ;
	private Transmitter transmitter ;
	private SensorBox sensors ;	
	private SliderBox sliders ;
	private Session session ;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.slider);
		
		this.wl = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, getClass().getName()) ;
		
		this.transmitter = Transmitter.getInstance() ;
		
		this.session = Index.getInstance().getCurrentSession() ;
		this.sensors = new SensorBox(this) ;
		
		this.sliders = new SliderBox(this) ;
		
		SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1) ;
		seekBar.setOnSeekBarChangeListener(this.sliders) ;
		seekBar = (SeekBar)findViewById(R.id.seekBar2) ;
		seekBar.setOnSeekBarChangeListener(this.sliders) ;
		seekBar = (SeekBar)findViewById(R.id.seekBar3) ;
		seekBar.setOnSeekBarChangeListener(this.sliders) ;
		seekBar = (SeekBar)findViewById(R.id.seekBar4) ;
		seekBar.setOnSeekBarChangeListener(this.sliders) ;
		seekBar = (SeekBar)findViewById(R.id.seekBar5) ;
		seekBar.setOnSeekBarChangeListener(this.sliders) ;
		seekBar = (SeekBar)findViewById(R.id.seekBar6) ;
		seekBar.setOnSeekBarChangeListener(this.sliders) ;
		
	}
	
	@Override
	protected void onStart() {
		super.onStart() ;
		this.transmitter.connect(this.session.getProxy(), this.session.getPort()) ;
	}
	
	@Override
	protected void onStop() {
		super.onStop() ;
		this.transmitter.disconnect() ;
	}
	
	@Override
	protected void onResume() {
		super.onResume() ;
		this.wl.acquire() ;
		this.sensors.startSensors() ;
		this.transmitter.start() ;
	}
	
	@Override
	protected void onPause() {
		super.onPause() ;
		this.sensors.stopSensors() ;
		this.wl.release() ;
		this.transmitter.interrupt() ;
	}
}