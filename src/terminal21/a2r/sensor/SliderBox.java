package terminal21.a2r.sensor;

import terminal21.a2r.transmitter.Entity;
import terminal21.a2r.transmitter.Transmitter;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SliderBox implements OnSeekBarChangeListener {

	private Transmitter transmitter ;
	
	public SliderBox(Context context) {
		this.transmitter = Transmitter.getInstance() ;
	}
	
	public void onStartTrackingTouch(SeekBar seekBar) {
		// not now
	}
	
	public void onStopTrackingTouch(SeekBar seekBar) {
		// not now
	}
	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		
		String bar = seekBar.getResources().getResourceEntryName(seekBar.getId()) ;
		Entity entity = null ;
		
		if (bar.contentEquals("seekBar1")) entity = new Entity(Entity.A, (short)progress) ;
		if (bar.contentEquals("seekBar2")) entity = new Entity(Entity.B, (short)progress) ;
		if (bar.contentEquals("seekBar3")) entity = new Entity(Entity.C, (short)progress) ;
		if (bar.contentEquals("seekBar4")) entity = new Entity(Entity.D, (short)progress) ;
		if (bar.contentEquals("seekBar5")) entity = new Entity(Entity.E, (short)progress) ;
		if (bar.contentEquals("seekBar6")) entity = new Entity(Entity.F, (short)progress) ;
		
		if (entity != null) {
			this.transmitter.addEntity(entity) ;
			this.transmitter.emit() ;
		}
	}
}
