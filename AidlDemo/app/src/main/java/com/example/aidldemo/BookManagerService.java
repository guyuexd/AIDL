package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService  extends Service {

    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    //支持并发读/写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(6000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };

    private Binder mBinder2 = new IBookManager2Impl() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        //方法二：在onTransact中做权限校验
//        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
//                throws RemoteException {
//            int check = checkCallingOrSelfPermission("com.ryg.chapter_2.permission.ACCESS_BOOK_SERVICE");
//            Log.d(TAG, "check=" + check);
//            if (check == PackageManager.PERMISSION_DENIED) {
//                return false;
//            }
//
//            String packageName = null;
//            String[] packages = getPackageManager().getPackagesForUid(
//                    getCallingUid());
//            if (packages != null && packages.length > 0) {
//                packageName = packages[0];
//            }
//            Log.d(TAG, "onTransact: " + packageName);
//            if (!packageName.startsWith("com.ryg")) {
//                return false;
//            }
//
//            return super.onTransact(code, data, reply, flags);
//        }

    };



    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "ios"));
    }

    //方法一：在onBind中做权限校验
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("com.example.aidldemo.permission.ACCESS_BOOK_SERVICE");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            return null;
//        }
        return mBinder2;
    }

    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }
}
