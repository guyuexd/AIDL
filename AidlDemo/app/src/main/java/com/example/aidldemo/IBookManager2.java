package com.example.aidldemo;

public interface IBookManager2  extends  android.os.IInterface {

    java.lang.String DESCRIPTOR = "com.example.aidldemo.IBookManager";

    int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    java.util.List<com.example.aidldemo.Book> getBookList() throws android.os.RemoteException;
    void addBook(com.example.aidldemo.Book book) throws android.os.RemoteException;
}
