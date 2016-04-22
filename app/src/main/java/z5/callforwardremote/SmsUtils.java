package z5.callforwardremote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by amy on 4/21/2016.
 * Program actions set up incorrectly - would only try to forward call
 *  or text upon pressing 'Save Settings' button
 *  Wasn't acting upon received texts/calls
 *  How to receive and forward texts learned from
 *  https://mobiforge.com/design-development/sms-messaging-andriod
 */
public class SmsUtils extends BroadcastReceiver
{
    private boolean textForward = false;
    private boolean callForward = false;

    public SmsUtils(){}

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (getTextForward()) {
            //intent.getExtras = secondary attribute to an Intent (action)
            //gives additional information not specifically accessed through other
            //methods
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String s = "";

            //retrieve message context and sender
            if (bundle != null) {
                //pdus = sms PDU byte array - PDU (protocol description unit)
                //other way to send and receive SMS is in TextMode
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    //second parameter = format extra from SMS_RECEIVED_ACTION intent
                    //not sure how to access the format string
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
                    s += "SMS from " + msgs[i].getOriginatingAddress();
                    s += " :";
                    s += msgs[i].getMessageBody();
                    s += "n";//why is n added to the end of the message
                    //example program from website goes on to use toast to display message
                }

                forwardText(context, s);
            }
        }
    }


    public void forwardText(Context c, String msg)
    {
        try {
            Uri uri = Uri.parse("smsto:14143011053");
            Intent textForwardIntent = new Intent(Intent.ACTION_SENDTO, uri);
            textForwardIntent.putExtra("sms_body", msg);//"Hello - text not forwarding currently");
            c.startActivity(textForwardIntent);
            //----------------
            //Intent intentTextForward = new Intent(Intent.ACTION_VIEW);
            //intentTextForward.putExtra("sms_body", "default content");//think this is text message text
            //intentTextForward.setType("vnd.android-dir/mms-sms");//where did this string come from????
            //startActivity(intentTextForward);
        }
        catch (Exception e) {
            //TODO: send message to database that a text was received and failed to forward
            //include text body?
        }
    }

    public void setTextForward(boolean set)
    {
        textForward = set;
    }

    public boolean getTextForward()
    {
        return textForward;
    }

    //move to phone utils when create
    public void setCallForward(boolean set)
    {
        callForward = set;
    }

    public boolean getCallForward()
    {
        return callForward;
    }
}
