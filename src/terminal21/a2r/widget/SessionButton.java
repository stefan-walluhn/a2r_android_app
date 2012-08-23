package terminal21.a2r.widget;

import android.util.AttributeSet;
import android.content.Context;
import android.widget.Button;

import terminal21.a2r.index.Session;

public class SessionButton extends Button {
	
	private Session session ;
	
	public SessionButton(Context context, Session session) {
		super(context) ;
		init(session) ;
	}
	
	public SessionButton(Context context, AttributeSet attrs, Session session) {
		super(context, attrs) ;
		init(session) ;
	}
	
	private void init(Session session){
		this.session = session ;
		this.setText(this.session.getTitle()) ;
	}
	
	public Session getSession() {
		return this.session ;
	}
}
