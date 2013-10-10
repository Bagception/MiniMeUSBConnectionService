package de.uniulm.bagception.rfidapi.miniusbconnectionservice;

import de.philipphock.android.lib.services.ServiceUtil;
import de.philipphock.android.lib.services.observation.ServiceObservationActor;
import de.philipphock.android.lib.services.observation.ServiceObservationReactor;
import de.uniulm.bagception.rfidapi.miniusbconnectionservice.service.USBConnectionService;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MiniMeUSBConnectionServiceControl extends Activity implements ServiceObservationReactor{

	private boolean serviceOnline = false;
	private ServiceObservationActor observationActor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mini_me_usbconnection_service_control);
		observationActor = new ServiceObservationActor(this,USBConnectionService.SERVICE_NAME);
	}

	
	
	@Override
	protected void onResume() {
		super.onResume();
		onServiceStopped(USBConnectionService.SERVICE_NAME);
		observationActor.register(this);
		ServiceUtil.requestStatusForServiceObservable(this, USBConnectionService.SERVICE_NAME);
		
		
	}
	@Override
	protected void onPause() {
		observationActor.unregister(this);
		super.onPause();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mini_me_usbconnection_service_control,
				menu);
		return true;
	}

	
	@Override
	public void onServiceStarted(String serviceName) {
		Log.d("REACTOR",serviceName+" started");
		TextView status = (TextView) findViewById(R.id.serviceStatus);
		status.setText("online");
		status.setTextColor(Color.GREEN);
		
		Button startStopBtn = (Button) findViewById(R.id.startStopService);
		startStopBtn.setText("stop service");
		startStopBtn.setEnabled(true);
		serviceOnline=true;
	}

	@Override
	public void onServiceStopped(String serviceName) {
		
		Log.d("REACTOR",serviceName+" stopped");
		TextView status = (TextView) findViewById(R.id.serviceStatus);
		status.setText("offline");
		status.setTextColor(Color.RED);
		
		Button startStopBtn = (Button) findViewById(R.id.startStopService);
		startStopBtn.setText("start service");
		startStopBtn.setEnabled(true);
		serviceOnline=false;
	}
	
	public void onStartStopService(View v){
		Button startStopBtn = (Button) findViewById(R.id.startStopService);
		startStopBtn.setEnabled(false);
		
		if (!serviceOnline){
			Intent startServiceIntent = new Intent(this,USBConnectionService.class);
			startService(startServiceIntent);	
		}else{
			Intent startServiceIntent = new Intent(this,USBConnectionService.class);
			stopService(startServiceIntent);
		}
		
	}

}
