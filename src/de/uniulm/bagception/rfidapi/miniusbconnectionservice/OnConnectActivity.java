package de.uniulm.bagception.rfidapi.miniusbconnectionservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import de.uniulm.bagception.services.ServiceNames;

public class OnConnectActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_on_connect);
	
		Intent ss = new Intent(ServiceNames.MASTER_CONTROL_SERVICE);
		startService(ss);
//			
		
		finish();
		
	}

		

}
