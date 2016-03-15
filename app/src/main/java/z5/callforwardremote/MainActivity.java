package z5.callforwardremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.net.Uri;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.onClickListener() {

            public void onClick(View v) {
                goToSetForwarding();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToSetForwarding() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkBx_CallForwarding);
        if (checkBox.isChecked())
        {
            String callForwardString = "**21*4143011053#";
            Intent intentCallForward = new Intent(Intent.ACTION_DIAL); // ACTION_CALL
            Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
            intentCallForward.setData(uri2);
            startActivity(intentCallForward);

            //Intent intent = new Intent();//this, SecondActivity.class); Figure out how to go to call settings and forward each text
            //startActivity(intent);
        }

        checkBox = (CheckBox) findViewById(R.id.chkBx_TextForwarding);
        if (checkBox.isChecked())
        {
        }
    }
}
