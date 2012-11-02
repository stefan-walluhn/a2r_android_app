package terminal21.a2r.sensor;

import terminal21.a2r.SliderActivity;
import terminal21.a2r.transmitter.Entity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorBox implements SensorEventListener {
	private SensorManager boxSensorManager ;
	private Sensor accSensor ;
	private Sensor gyrSensor ;
	private SliderActivity context ;
	
	public SensorBox(Context context) {
		this.context = (SliderActivity)context ;
		this.boxSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE) ;
		this.accSensor = this.boxSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ;
		this.gyrSensor = this.boxSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) ;
	}
	
	public void startSensors() {
		startSensor(this.accSensor) ;
		startSensor(this.gyrSensor) ;
	}

	private void startSensor(Sensor sensor) {
		if (sensor != null) {
			this.boxSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL) ;
		}
	}
	
	public void stopSensors() {
		this.boxSensorManager.unregisterListener(this) ;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// I am not interested at all
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float max = event.sensor.getMaximumRange() ;
		
		if (event.sensor == this.accSensor) {
			this.context.transmitter.addEntity(new Entity(Entity.ACCEL_X, (short)(event.values[0] * 32768 / max))) ;
			this.context.transmitter.addEntity(new Entity(Entity.ACCEL_Y, (short)(event.values[1] * 32768 / max))) ;
			this.context.transmitter.addEntity(new Entity(Entity.ACCEL_Z, (short)(event.values[2] * 32768 / max))) ;
		}
		
		if (event.sensor == this.gyrSensor) {
			this.context.transmitter.addEntity(new Entity(Entity.GYR_X, (short)(event.values[0] * 32768 / max))) ;
			this.context.transmitter.addEntity(new Entity(Entity.GYR_Y, (short)(event.values[1] * 32768 / max))) ;
			this.context.transmitter.addEntity(new Entity(Entity.GYR_Z, (short)(event.values[2] * 32768 / max))) ;
		}
		
		this.context.transmitter.emit() ;
	}
}
