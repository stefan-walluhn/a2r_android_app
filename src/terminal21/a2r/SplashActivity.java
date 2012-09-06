package terminal21.a2r;


import java.util.Observer;
import java.util.Observable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Dialog ;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import terminal21.a2r.R;
import terminal21.a2r.index.Loader ;


public class SplashActivity extends Activity implements Observer, OnClickListener {
	
	static final int DIALOG_NO_NET_ID = 0 ;
	static final int DIALOG_HOST_ID = 1 ;
	
	private Dialog dialog ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo() ;
        
        if (activeNetwork == null || !activeNetwork.isConnected()) {
        	showDialog(DIALOG_NO_NET_ID) ;
        	return ;
        }
        
        showDialog(DIALOG_HOST_ID) ;
    }
    
    public void onClick(View view) {
    	
    	CharSequence index_server = ((EditText)dialog.findViewById(R.id.a2rIndexServer)).getText().toString() ;
    	this.dialog.dismiss() ;
    	
    	this.dialog = ProgressDialog.show(this, getResources().getString(R.string.index_init), "Loading active sessions", true) ;
    	this.dialog.setCancelable(false) ;
    	
    	Loader loader = new Loader(index_server) ;
    	loader.addObserver(this) ;
    	(new Thread(loader)).start() ;
    }
    
    public void update(Observable o, Object arg) {
    	Integer status = (Integer)arg ;
    	
    	if (status == Loader.ERROR) {
    		this.runOnUiThread(new Runnable() {	// push to ui thread, http://developer.android.com/guide/components/processes-and-threads.html
    			public void run() {
    				dialog.setTitle(getResources().getString(R.string.index_error)) ;
    				dialog.setCancelable(true) ;
    			}
    		}) ;
    		
    		return ;
    	}
    	
    	if (status == Loader.FETCH) {
    		this.runOnUiThread(new Runnable() {	// push to ui thread, http://developer.android.com/guide/components/processes-and-threads.html
    			public void run() {
    				dialog.setTitle(getResources().getString(R.string.index_fetch)) ;
    			}
    		}) ;

    		return ;
    	}
    	
    	if (status == Loader.PARSE) {
    		this.runOnUiThread(new Runnable() {	// push to ui thread, http://developer.android.com/guide/components/processes-and-threads.html
    			public void run() {
    				dialog.setTitle(getResources().getString(R.string.index_parse)) ;
    			}
    		}) ;

    		return ;
    	}

    	if (status == Loader.DONE) {
    		this.runOnUiThread(new Runnable() {	// push to ui thread, http://developer.android.com/guide/components/processes-and-threads.html
    			public void run() {
		    		dialog.dismiss() ;
    			}
    		}) ;

    		Intent i = new Intent(this, SessionListActivity.class) ;
			startActivity(i) ;
			finish() ;

    		return ;
    	}
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
   	
    	switch(id) {
    	case DIALOG_HOST_ID:
    		
    		dialog = new Dialog(this) ;
    		dialog.setContentView(R.layout.host_dialog) ;
    		dialog.setTitle("Host") ;
    		
    		Button fetchButton = (Button)dialog.findViewById(R.id.fetchButton) ;
    		fetchButton.setOnClickListener(this) ;

    		break ;
    	case DIALOG_NO_NET_ID:
    		
    		dialog = new Dialog(this) ;
    		dialog.setContentView(R.layout.no_net_dialog) ;
    		dialog.setTitle("no connection") ;
    		break ;
    	}
    	
    	return this.dialog ;
    }
}