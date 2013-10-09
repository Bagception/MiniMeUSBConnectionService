package de.uniulm.bagception.rfidapi.miniusbconnectionservice.service;

import java.util.HashMap;
import java.util.Iterator;

import de.uniulm.bagception.rfidapi.RFIDMiniMe;
import de.uniulm.bagception.rfidapi.UsbCommunication;




import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class USBConnectionService extends Service{

	private volatile boolean init=false;
	
	public static final String USB_CONNECTION_BROADCAST = "de.uniulm.bagception.service.broadcast.usbconnection";
	public static final String USB_CONNECTION_BROADCAST_CONNECTED = "de.uniulm.bagception.service.broadcast.usbconnection.connected";
	public static final String USB_CONNECTION_BROADCAST_DISCONNECTED = "de.uniulm.bagception.service.broadcast.usbconnection.disconnected";
	public static final String USB_CONNECTION_BROADCAST_RESCAN = "de.uniulm.bagception.service.broadcast.usbconnection.rescan";
	
	private PendingIntent mPermissionIntent;
	private static final String ACTION_USB_PERMISSION = "com.mti.rfid.minime.USB_PERMISSION";

	private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();

	
	private UsbManager mManager;

	
	private boolean DEBUG = true;

	
	private static final int PID = 49193;
	private static final int VID = 4901;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("USB","Start command");
		if (init){
			//already started
			Log.d("USB","already started");
			return 0;
		}
		Log.d("USB","started");
		mManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				ACTION_USB_PERMISSION), 0);
		
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED); // will
																	// intercept
																	// by system
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
		filter.addAction(ACTION_USB_PERMISSION);
		registerReceiver(usbReceiver, filter);
		
		init = true;
		
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void foceRescanUSBState(){
		scanDevice();
	}
	

	
	private void scanDevice(){
		Log.d("USB","Scanning...");
		HashMap<String, UsbDevice> deviceList = mManager.getDeviceList();
		Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
		while (deviceIterator.hasNext()) {
			UsbDevice device = deviceIterator.next();
			if (device.getProductId() == PID && device.getVendorId() == VID) {
				usbStateChanged(true);
				if (!mManager.hasPermission(device)){
					Log.d("USB","No Permission.. requesting");
					mManager.requestPermission(device, mPermissionIntent);
				}else{
					Log.d("USB", "PERMISSION");
					break;
				}
				
			}
		}
	}
	
	BroadcastReceiver usbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			scanDevice();
			Log.d("USB", "1");
			String action = intent.getAction();
			if (DEBUG)
				Toast.makeText(context, "Broadcast Receiver",
						Toast.LENGTH_SHORT).show();

			if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) { // will
				Log.d("USB", "2"); // intercept
				// by
				// system
				if (DEBUG)
					Toast.makeText(context, "USB Attached", Toast.LENGTH_SHORT)
							.show();
				UsbDevice device = intent
						.getParcelableExtra(UsbManager.EXTRA_DEVICE);
				
				mUsbCommunication.setUsbInterface(mManager, device);	
				
				usbStateChanged(true);

			} else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
				Log.d("USB", "3");
				if (DEBUG)
					Toast.makeText(context, "USB Detached", Toast.LENGTH_SHORT)
							.show();
				//mUsbCommunication.setUsbInterface(null, null);//TODO causes nullpointerexception
				usbStateChanged(false);
				// getReaderSn(false);

			} else if (ACTION_USB_PERMISSION.equals(action)) {
				Log.d("USB", "4");
				if (DEBUG)
					Toast.makeText(context, "USB Permission",
							Toast.LENGTH_SHORT).show();
				Log.d(UsbCommunication.TAG, "permission");
				synchronized (this) {
					UsbDevice device = intent
							.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					if (intent.getBooleanExtra(
							UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						mUsbCommunication.setUsbInterface(mManager, device);
						usbStateChanged(true);

						RFIDMiniMe.setPowerLevelTo18();
						RFIDMiniMe.sleepMode();
					} else {
						//finish(); TODO?
					}
				}
			}
		}
	};

	
	BroadcastReceiver rescanrecv = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			scanDevice();
		}
	};
	
	private void usbStateChanged(boolean connected){
		Log.d("USB","USB Dongle connected: "+connected);
	}

}
