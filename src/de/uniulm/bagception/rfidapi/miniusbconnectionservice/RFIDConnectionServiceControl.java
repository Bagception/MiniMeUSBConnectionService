package de.uniulm.bagception.rfidapi.miniusbconnectionservice;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RFIDConnectionServiceControl extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rfidconnection_service_control);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rfidconnection_service_control, menu);
		return true;
	}

}
