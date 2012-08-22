package terminal21.a2r;


import java.util.Observer;
import java.util.Observable;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams ;
import android.widget.LinearLayout;
import android.widget.EditText;

import terminal21.a2r.R;
import terminal21.a2r.index.Loader ;
import terminal21.a2r.widget.IndexLoader;


public class SplashActivity extends Activity implements Observer {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }
    
    public void fetchIndex(View view) {
    	CharSequence index_server = ((EditText)findViewById(R.id.a2rIndexServer)).getText().toString() ;
    	LinearLayout fetchLayout = (LinearLayout)findViewById(R.id.fetchSettingsLayout) ;
    	IndexLoader loader = new IndexLoader(this) ;
    	
    	fetchLayout.removeAllViews() ;
    	fetchLayout.addView(loader, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)) ;
    	
    	Loader l = new Loader(index_server) ;
    	l.addObserver(this) ;
    	l.addObserver(loader) ;
    	(new Thread(l)).start() ;
    }
    
    public void update(Observable o, Object arg) {
    	if (arg == Loader.DONE) {
			Intent i = new Intent(this, SessionListActivity.class) ;
			startActivity(i) ;
			finish() ;
		}
    }
}