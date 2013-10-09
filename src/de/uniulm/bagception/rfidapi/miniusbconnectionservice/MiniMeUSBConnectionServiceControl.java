package de.uniulm.bagception.rfidapi.miniusbconnectionservice;

import de.uniulm.bagception.rfidapi.miniusbconnectionservice.service.USBConnectionService;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MiniMeUSBConnectionServiceControl extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mini_me_usbconnection_service_control);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("USB","intent send");
		Intent startServiceIntent = new Intent(this,USBConnectionService.class);
		startService(startServiceIntent);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mini_me_usbconnection_service_control,
				menu);
		return true;
	}

}
