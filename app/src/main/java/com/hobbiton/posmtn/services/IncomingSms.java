package com.hobbiton.posmtn.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMS_BROADCAST_RECEIVER";
    public String receivedMessage = "";
    public String contact_no = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        Log.i(TAG, "Intent Received: " + intent.getAction());
//        if (intent.getAction() != null){
//            if (intent.getAction().equals(SMS_RECEIVED)){
//                Bundle dataBundle = intent.getExtras();
//                if (dataBundle != null){
//                    Object[] myPDU = (Object[])dataBundle.get("myPDU");
//                    final SmsMessage[] message = new SmsMessage[myPDU.length];
//
//                    for (int i = 0; i<myPDU.length;i++){
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                            String format = dataBundle.getString("format");
//                            message[i] = SmsMessage.createFromPdu((byte[])myPDU[i],format);
//                        }else {
//                            receivedMessage = message[i].getMessageBody();
//                            contact_no = message[i].getOriginatingAddress();
//                        }
//                    }
//                    Toast.makeText(context, "Message: "+ receivedMessage + "\n"+contact_no, Toast.LENGTH_LONG).show();
//                }
//            }
//        }
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                if (pdusObj != null) {
                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        contact_no = phoneNumber;
                        receivedMessage += currentMessage.getDisplayMessageBody();

                        Log.i("SmsReceiver", "senderNum: " + contact_no + "; message: " + receivedMessage);

                        // Show Alert
                        int duration = Toast.LENGTH_LONG;
//                        Toast toast = Toast.makeText(context, "senderNum: " + contact_no + ", message: " + receivedMessage, duration);toast.show();

                    }
                }

                // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
