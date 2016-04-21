package z5.callforwardremote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.CheckBox;

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
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //intent.getExtras = secondary attribute to an Intent (action)
        //gives additional information not specifically accessed through other
        //methods
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String s = "";

        //retrieve message context and sender
        if (bundle != null)
        {
            //pdus = sms PDU byte array - PDU (protocol description unit)
            //other way to send and receive SMS is in TextMode
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++)
            {
                //second parameter = format extra from SMS_RECEIVED_ACTION intent
                //not sure how to access the format string
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i], bundle.getString("format"));
                s += "SMS from " + msgs[i].getOriginatingAddress();
                s += " :";
                s += msgs[i].getMessageBody().toString();
                s += "n";//why is n added to the end of the message
                //example program from website goes on to use toast to display message
            }

            //TODO: move forward text logic here
            //call forwardText()
        }
    }


    public void forwardText()
    {
        //copied from MainActivity
        //Not set up to work with onReceive
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkBx_CallForwarding);
        checkBox = (CheckBox) findViewById(R.id.chkBx_TextForwarding);
        if (checkBox.isChecked())
        {
            try {
                Intent intentTextForward = new Intent(Intent.ACTION_VIEW);
                intentTextForward.putExtra("sms_body", "default content");//think this is text message text
                intentTextForward.setType("vnd.android-dir/mms-sms");//where did this string come from????
                startActivity(intentTextForward);
            }
            catch (Exception e) {
                //TODO: send message to database that a text was received and failed to forward
                //include text body?
            }

        }
    }
}
