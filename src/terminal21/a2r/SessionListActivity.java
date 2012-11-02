package terminal21.a2r;

import java.util.Iterator ;

import terminal21.a2r.R;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.ViewGroup.LayoutParams ;
import android.widget.LinearLayout;

import terminal21.a2r.index.Session;
import terminal21.a2r.index.Index;
import terminal21.a2r.widget.SessionButton;

public class SessionListActivity extends Activity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.session_list);
		
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.listLayout) ;
		Session ses = null ;
		SessionButton b = null ;
		//Vector<Session> sessions = SessionList.getInstance().sessions ;
		Iterator<Session> sitr = Index.getInstance().getSessions().iterator() ;
		
		while (sitr.hasNext()) {
			ses = sitr.next() ;
			b = new SessionButton(this, ses) ;
			b.setOnClickListener(this) ;
			listLayout.addView(b, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)) ;
		}
	}

	public void onClick(View view) {
		Index.getInstance().setCurrentSession(((SessionButton) view).getSession()) ;
		Intent i = new Intent(view.getContext(), SliderActivity.class);
		startActivity(i);
	}
}
