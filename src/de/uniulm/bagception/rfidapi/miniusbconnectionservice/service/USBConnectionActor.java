package de.uniulm.bagception.rfidapi.miniusbconnectionservice.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import de.philipphock.android.lib.BroadcastActor;

/**
 * 
 * @author phil
 *
 */
public class USBConnectionActor extends BroadcastActor<USBConnectionReactor>{


	public USBConnectionActor(USBConnectionReactor reactor) {
		super(reactor);
	}

	@Override
	public void register(Context context) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(USBConnectionService.USB_CONNECTION_BROADCAST_CONNECTED);
		filter.addAction(USBConnectionService.USB_CONNECTION_BROADCAST_DISCONNECTED);
		context.registerReceiver(this,filter);
		
	}


	@Override
	public void onReceive(Context context, Intent intent) {
		if (USBConnectionService.USB_CONNECTION_BROADCAST_CONNECTED.equals(intent.getAction())){
			reactor.onUSBConnected();
		} else if (USBConnectionService.USB_CONNECTION_BROADCAST_DISCONNECTED.equals(intent.getAction())){
			reactor.onUSBDisconnected();
		}
	}
	
	
}
