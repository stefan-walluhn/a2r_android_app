package terminal21.a2r.widget;

import android.util.AttributeSet;
import android.content.Context;
import android.widget.Button;

import terminal21.a2r.index.Sensor;

public class SensorButton extends Button {
	
	private Sensor sensor ;
	
	public SensorButton(Context context, Sensor sensor) {
		super(context) ;
		init(sensor) ;
	}
	
	public SensorButton(Context context, AttributeSet attrs, Sensor seensor) {
		super(context, attrs) ;
		init(sensor) ;
	}
	
	private void init(Sensor sensor){
		this.sensor = sensor ;
		this.setText(this.sensor.getName()) ;
	}
	
	public Sensor getSensor() {
		return this.sensor ;
	}
}
