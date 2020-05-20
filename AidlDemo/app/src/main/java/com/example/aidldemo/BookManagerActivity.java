package com.example.aidldemo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class BookManagerActivity extends Activity {

    private static final String TAG = "BookManagerActivity";

    //private IBookManager mRemoteBookManager;
    private IBookManager2 mRemoteBookManager2;

    // 健壮性: Binder 是可能意外死亡的,这往往是由于服务端进程意外停止了,这时我们需要重新连接服务。
    // 有两种方法,第一种方法是给 Binder 设置 DeathRecipient; 另一种方法是在 onServiceDisconnected 中重连远程服务
//    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
//        @Override
//        public void binderDied() {
//            if (mRemoteBookManager != null) {
//                mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
//                mRemoteBookManager = null;
//                bindService();
//            }
//
//        }
//    };

    private IBinder.DeathRecipient mDeathRecipient2 = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mRemoteBookManager2 != null) {
                mRemoteBookManager2.asBinder().unlinkToDeath(mDeathRecipient2, 0);
                mRemoteBookManager2 = null;
                bindService();
            }

        }
    };

//    private ServiceConnection mConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            IBookManager bookManager = IBookManager.Stub.asInterface(service);
//            mRemoteBookManager = bookManager;
//            //下面的调用有问题吗？
//            try {
//                mRemoteBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
//                List<Book> list = bookManager.getBookList();
//                // 证明AIDL 中能够使用的 List 只有 ArrayList
//                Log.i(TAG, "query book list, list type:"+ list.getClass().getCanonicalName());
//                Log.i(TAG,"query book list:"+ list.toString());
//            }catch (RemoteException e) {
//                e.printStackTrace();
//            }
//
//            Log.i(TAG,"onServiceConnected. tname:" + Thread.currentThread().getName());
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mRemoteBookManager = null;
//            Log.i(TAG,"onServiceDisconnected. tname:" + Thread.currentThread().getName());
//        }
//    };

    private ServiceConnection mConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager2 bookManager2 = IBookManager2Impl.asInterface(service);
            mRemoteBookManager2 = bookManager2;
            try {
                mRemoteBookManager2.asBinder().linkToDeath(mDeathRecipient2, 1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager2 = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection2, Context.BIND_AUTO_CREATE);
    }

    // why public ?
    public void onButton1Click(View view) {
        Toast.makeText(this, "click button1", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (mRemoteBookManager2 != null) {
                    try {
                        List<Book> newList = mRemoteBookManager2.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection2);
        super.onDestroy();
    }

}
