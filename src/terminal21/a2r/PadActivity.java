package terminal21.a2r;

import java.net.InetAddress ;
import java.net.DatagramSocket ;
import java.net.DatagramPacket ;
import java.io.IOException ;
import java.net.UnknownHostException ;
import java.net.SocketException ;

import terminal21.a2r.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.View ;
import android.view.View.OnTouchListener ;
import android.view.MotionEvent ;
import android.widget.LinearLayout ;
import android.widget.TextView ;

public class PadActivity extends Activity implements OnTouchListener {
	
	private TextView coordinates ;
	private DatagramSocket socket ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.pad);
		
		try {
			this.socket = new DatagramSocket() ;
			this.socket.connect(InetAddress.getByName("172.26.0.171"), 7069) ;	// ToDo: fetch this from index
		} catch(SocketException e) {
			// ToDo: message bad socket
		} catch (UnknownHostException e) {
			// ToDo: message unknown host
		}
		
		this.coordinates = (TextView)findViewById(R.id.coordinates) ;
		LinearLayout layout = (LinearLayout)findViewById(R.id.padLayout) ;
		layout.setOnTouchListener(this) ;
	}
	
	public boolean onTouch(View view, MotionEvent event) {
		this.coordinates.setText(Float.toString(event.getX()) + ":" + Float.toString(event.getY())) ;
		
		if (this.socket.isConnected()) {
			byte data[] = new byte[4] ;
			data[0] = (byte)0x81 ;
			data[1] = (byte)(short)event.getX() ;
			data[2] = (byte)((short)(event.getX())>>8) ;
			data[3] = (byte)(data[0] ^ data[1] ^ data[2]) ;
			
			DatagramPacket pack = new DatagramPacket(data, 4) ;
			try {
				this.socket.send(pack) ;
			}
			catch (IOException e) {
				// ToDo: catch exception
			}
		}
		return true ;
	}
}