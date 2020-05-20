package com.example.aidldemo;

import android.os.Binder;
import android.util.Log;

public abstract class IBookManager2Impl  extends Binder implements IBookManager2{
    public static final String TAG = "IBookManager2Impl";

    public static com.example.aidldemo.IBookManager2 getDefaultImpl() {
        return IBookManager2Impl.Proxy.sDefaultImpl;
    }

    public IBookManager2Impl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static com.example.aidldemo.IBookManager2 asInterface(android.os.IBinder obj)
    {
        if ((obj==null)) {
            return null;
        }
        // 查找本地对象
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin!=null)&&(iin instanceof com.example.aidldemo.IBookManager2))) {
            Log.e(TAG, "线程：" + Thread.currentThread().getName() + "————" + "返回本地对象");
            return ((com.example.aidldemo.IBookManager2)iin);
        }
        Log.e(TAG, "线程：" + Thread.currentThread().getName() + "————" + "返回代理对象");
        return new com.example.aidldemo.IBookManager2Impl.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
        return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
        java.lang.String descriptor = DESCRIPTOR;
        switch (code)
        {
            case INTERFACE_TRANSACTION:
            {
                reply.writeString(descriptor);
                return true;
            }
            case TRANSACTION_getBookList:
            {
                Log.e(TAG, "线程：" + Thread.currentThread().getName() + "————" + "本地对象通过 Binder 执行 getBookList");
                data.enforceInterface(descriptor);
                java.util.List<com.example.aidldemo.Book> _result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addBook:
            {
                Log.e(TAG, "线程：" + Thread.currentThread().getName() + "————" + "本地对象通过 Binder 执行 addBook");
                data.enforceInterface(descriptor);
                com.example.aidldemo.Book _arg0;
                if ((0!=data.readInt())) {
                    _arg0 = com.example.aidldemo.Book.CREATOR.createFromParcel(data);
                }
                else {
                    _arg0 = null;
                }
                this.addBook(_arg0);
                reply.writeNoException();
                return true;
            }
            default:
            {
                return super.onTransact(code, data, reply, flags);
            }
        }
    }

    private static class Proxy implements com.example.aidldemo.IBookManager2
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
        @Override public java.util.List<com.example.aidldemo.Book> getBookList() throws android.os.RemoteException
        {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<com.example.aidldemo.Book> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                boolean _status = mRemote.transact(IBookManager2Impl.TRANSACTION_getBookList, _data, _reply, 0);
                if (!_status && getDefaultImpl() != null) {
                    return getDefaultImpl().getBookList();
                }
                _reply.readException();
                _result = _reply.createTypedArrayList(com.example.aidldemo.Book.CREATOR);
            }
            finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }
        @Override public void addBook(com.example.aidldemo.Book book) throws android.os.RemoteException
        {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((book!=null)) {
                    _data.writeInt(1);
                    book.writeToParcel(_data, 0);
                }
                else {
                    _data.writeInt(0);
                }
                boolean _status = mRemote.transact(IBookManager2Impl.TRANSACTION_addBook, _data, _reply, 0);
                if (!_status && getDefaultImpl() != null) {
                    getDefaultImpl().addBook(book);
                    return;
                }
                _reply.readException();
            }
            finally {
                _reply.recycle();
                _data.recycle();
            }
        }
        public static com.example.aidldemo.IBookManager2 sDefaultImpl;
    }
}
