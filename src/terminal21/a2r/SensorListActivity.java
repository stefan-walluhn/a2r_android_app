package terminal21.a2r;

import java.util.Iterator ;

import terminal21.a2r.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener ;
import android.view.ViewGroup.LayoutParams ;
import android.widget.LinearLayout;

import terminal21.a2r.widget.SensorButton ;
import terminal21.a2r.index.Sensor;
import terminal21.a2r.index.Index;

public class SensorListActivity extends Activity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.sensor_list);
		
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.listLayout) ;
		Sensor sensor = null ;
		SensorButton b = null ;
		//Vector<Session> sessions = SessionList.getInstance().sessions ;
		Iterator<Sensor> sitr = Index.getInstance().getCurrentSession().getSensors().iterator() ;
		
		while (sitr.hasNext()) {
			sensor = sitr.next() ;
			b = new SensorButton(this, sensor) ;
			b.setOnClickListener(this) ;
			listLayout.addView(b, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)) ;
		}
	}
	
	public void onClick(View view) {
		Index.getInstance().setCurrentSensor(((SensorButton)view).getSensor()) ;
		Intent i = new Intent(view.getContext(), PadActivity.class);
		startActivity(i);
	}
}
