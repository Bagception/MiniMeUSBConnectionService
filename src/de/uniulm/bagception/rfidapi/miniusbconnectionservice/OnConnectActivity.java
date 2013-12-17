package de.uniulm.bagception.rfidapi.miniusbconnectionservice;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import de.uniulm.bagception.rfidapi.RFIDMiniMe;
import de.uniulm.bagception.services.ServiceNames;

public class OnConnectActivity extends Activity {
	private UsbManager mManager;
	private PendingIntent mPermissionIntent;
	private static final String ACTION_USB_PERMISSION = "com.mti.rfid.minime.USB_PERMISSION";
	private static final int PID = 49193;
	private static final int VID = 4901;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_on_connect);
		mManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				ACTION_USB_PERMISSION), 0);

		Intent ss = new Intent(ServiceNames.MASTER_CONTROL_SERVICE);
		startService(ss);
			
		//scanDevice();
		
		finish();
		
	}

	private void scanDevice() {
		HashMap<String, UsbDevice> deviceList = mManager.getDeviceList();
		Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
		while (deviceIterator.hasNext()) {
			UsbDevice device = deviceIterator.next();
			if (device.getProductId() == PID && device.getVendorId() == VID) {
				if (!mManager.hasPermission(device)) {
					mManager.requestPermission(device, mPermissionIntent);
				}
				RFIDMiniMe.disableBlinking(this);
				return;
			}
		}
	}
	

}
