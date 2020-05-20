# AIDL
android aldl binder
AIDL使用步骤： 1.服务端： 服务端首先要创建一个Service用来监听客户端的链接请求，然后创建一个AIDL文件，将暴露给客户端的接口在这个AIDL文件中声明，最后在Servcie中实现这个AIDL接口即可。 2.客户端： 客户端首先需要绑定服务端Service，绑定成功后，将服务端返回的Binder对象转成AIDL接口所属的类型，接着就可以调用AIDL中的方法了。

不使用AIDL，手动实现一个Binder的步骤 1.声明一个AIDL性质的接口，只需要继承IInterface接口即可，IInterface接口只有一个asBinder方法。接口包括：一个 Binder 描述符，接口方法和方法id 2.实现Stub类和Stub类的Proxy代理类。

接下来，使用了Binder的两个很重要的方法 linkToDeath 和 unlinkToDeath

todo：验证权限
in out inout 数据走向标识
