/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/phil/workspace/MiniMeUSBConnectionService/src/de/uniulm/bagception/service/USBConnectionServiceRemote.aidl
 */
package de.uniulm.bagception.service;
public interface USBConnectionServiceRemote extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements de.uniulm.bagception.service.USBConnectionServiceRemote
{
private static final java.lang.String DESCRIPTOR = "de.uniulm.bagception.service.USBConnectionServiceRemote";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an de.uniulm.bagception.service.USBConnectionServiceRemote interface,
 * generating a proxy if needed.
 */
public static de.uniulm.bagception.service.USBConnectionServiceRemote asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof de.uniulm.bagception.service.USBConnectionServiceRemote))) {
return ((de.uniulm.bagception.service.USBConnectionServiceRemote)iin);
}
return new de.uniulm.bagception.service.USBConnectionServiceRemote.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_isConnected:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isConnected();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements de.uniulm.bagception.service.USBConnectionServiceRemote
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public boolean isConnected() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isConnected, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_isConnected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public boolean isConnected() throws android.os.RemoteException;
}
