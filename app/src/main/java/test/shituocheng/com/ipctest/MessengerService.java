package test.shituocheng.com.ipctest;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Myconstant.MSG_FROM_CLIENT:
                    Log.d(TAG, "receive message from Client:" + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null, Myconstant.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    String replyString = "I have receive your message. catch ya later";
                    bundle.putString("reply", replyString);
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return messenger.getBinder();
    }
}
