package terminal21.a2r.sensor;

import terminal21.a2r.transmitter.Entity;
import terminal21.a2r.transmitter.Transmitter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorBox implements SensorEventListener {
	
	private Transmitter transmitter ;
	
	private SensorManager boxSensorManager ;
	private Sensor accSensor ;
	private Sensor gyroSensor ;
	private Sensor tempSensor ;
	private Sensor lightSensor ;
	
	public SensorBox(Context context) {
		
		this.transmitter = Transmitter.getInstance() ;
		
		this.boxSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE) ;
		this.accSensor = this.boxSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ;
		this.gyroSensor = this.boxSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) ;
		this.tempSensor = this.boxSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) ;
		this.lightSensor = this.boxSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) ;
	}
	
	public void startSensors() {
		startSensor(this.accSensor) ;
		startSensor(this.gyroSensor) ;
		startSensor(this.tempSensor) ;
		startSensor(this.lightSensor) ;
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
			this.transmitter.addEntity(new Entity(Entity.ACCEL_X, (short)(event.values[0] * 32768 / max))) ;
			this.transmitter.addEntity(new Entity(Entity.ACCEL_Y, (short)(event.values[1] * 32768 / max))) ;
			this.transmitter.addEntity(new Entity(Entity.ACCEL_Z, (short)(event.values[2] * 32768 / max))) ;
			return ;
		}
		
		if (event.sensor == this.gyroSensor) {
			this.transmitter.addEntity(new Entity(Entity.GYRO_X, (short)(event.values[0] * 32768 / max))) ;
			this.transmitter.addEntity(new Entity(Entity.GYRO_Y, (short)(event.values[1] * 32768 / max))) ;
			this.transmitter.addEntity(new Entity(Entity.GYRO_Z, (short)(event.values[2] * 32768 / max))) ;
			return ;
		}
		
		if (event.sensor == this.tempSensor) {
			this.transmitter.addEntity(new Entity(Entity.TEMP, (short)(event.values[0]))) ;
		}
		
		if (event.sensor == this.lightSensor) {
			this.transmitter.addEntity(new Entity(Entity.LIGHT, (short)(event.values[0]))) ;
		}
		//this.context.transmitter.emit() ;
	}
}
