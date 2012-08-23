package terminal21.a2r;

import java.util.Vector ;
import java.util.Iterator ;

import terminal21.a2r.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams ;
import android.widget.LinearLayout;
import android.widget.Button ;

import terminal21.a2r.index.Sensor;
import terminal21.a2r.index.Index;

public class SensorListActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.sensor_list);
		
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.listLayout) ;
		Sensor sensor = null ;
		Button b = null ;
		//Vector<Session> sessions = SessionList.getInstance().sessions ;
		Iterator<Sensor> sitr = Index.getInstance().getCurrentSession().getSensors().iterator() ;
		
		while (sitr.hasNext()) {
			sensor = sitr.next() ;
			b = new Button(this) ;
			b.setText(sensor.getName()) ;
			listLayout.addView(b, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)) ;
		}
	}
}
