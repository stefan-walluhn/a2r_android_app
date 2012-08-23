package terminal21.a2r;

import java.util.Vector ;
import java.util.Iterator ;

import terminal21.a2r.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams ;
import android.widget.Button;
import android.widget.LinearLayout;

import terminal21.a2r.index.Session;
import terminal21.a2r.index.Index;

public class SessionListActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.session_list);
		
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.listLayout) ;
		Session ses = null ;
		Button b = null ;
		//Vector<Session> sessions = SessionList.getInstance().sessions ;
		Iterator<Session> sitr = Index.getInstance().getSessions().iterator() ;
		
		while (sitr.hasNext()) {
			ses = sitr.next() ;
			b = new Button(this) ;
			b.setText(ses.getTitle()) ;
			listLayout.addView(b, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)) ;
		}
	}

}
