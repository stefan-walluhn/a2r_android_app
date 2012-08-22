package terminal21.a2r.widget;

import java.util.Observer;
import java.util.Observable;

import android.util.AttributeSet;
import android.content.Context;
import android.view.Gravity ;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

import terminal21.a2r.R;
import terminal21.a2r.index.Loader ;

public class IndexLoader extends LinearLayout implements Observer {
	
	private TextView status ;
	private ProgressBar progress ;
	
	public IndexLoader(Context context) {
		super(context) ;
		init() ;
	}
	
	public IndexLoader(Context context, AttributeSet attrs) {
		super(context, attrs) ;
		init() ;
	}
	
	private void init() {
		this.setOrientation(VERTICAL) ;
		this.setGravity(Gravity.CENTER) ;
		
		this.status = new TextView(getContext()) ;
		this.progress = new ProgressBar(getContext()) ;
		
		addView(progress, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)) ;
		addView(status, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)) ;
		
		setStatus(getContext().getString(R.string.index_init)) ;
	}
	
	private void setStatus(CharSequence status) {
		this.status.setText(status) ;
	}
	
	public void update(Observable o, Object arg) {

		final CharSequence status ;
		CharSequence stat = "unknown" ;
		
		if (arg == Loader.INIT) stat = getContext().getString(R.string.index_init) ;
		if (arg == Loader.FETCH) stat = getContext().getString(R.string.index_fetch) ;
		if (arg == Loader.PARSE) stat = getContext().getString(R.string.index_parse) ;
		if (arg == Loader.ERROR) stat = getContext().getString(R.string.index_error) ;
		if (arg == Loader.DONE) stat = getContext().getString(R.string.index_done) ;
		
		status = stat ;
		
		this.post(new Runnable() {	// push to ui thread, http://developer.android.com/guide/components/processes-and-threads.html
			public void run() {
				setStatus(status) ;
			}
		}) ;
	}
}

