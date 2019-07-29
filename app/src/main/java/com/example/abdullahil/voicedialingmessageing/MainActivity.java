/**
 *  Abdullhail Baki Adol
 */

package com.example.abdullahil.voicedialingmessageing;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>this is luncher activity, get all contaucts, get voice and quick call>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>*/public class MainActivity extends AppCompatActivity {/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;
    private String ttext = "";

    private List<getSetContaucts> getPhnCnts;
    private List<getSetContaucts> SrtgetPhnCnts; //take sorted contaucts

    private List<getSetLogDetails> getLogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPhnCnts = new ArrayList<>(); //------------------------ at first get all contaucts
        allPhoneContauct();

        getLogs = new ArrayList<>();
        getAllCallLog();

//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        /*-------------------------------find layout and add fragment with main activity-------------------
         *--------------------------------------------------------------------------------------------------
         * --------*/
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.AddFragment(new searchFragmetnClass(getLogs, getApplicationContext()), "Log");
        adapter.AddFragment(new contauctFragmentClass(SrtgetPhnCnts, getApplicationContext()), "Contauct");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        /*-----------------------------------end of add fragment-------------------------------------------
         * -----------------------------------------------------------------------------------------------*/
//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /*------------------------------get voice button and search name then call--------------------------
    ----------------------------------------------------------------------------------------------------
    ---*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeech(view);

            }
        });
    }

    /*--------------------------------------------------------------------------------------------------
    -----------------------------------------------end of get voice-----------------------------------*/
//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*---------------------------------------------------------------------------------------------------
     * ------------------------------get all contaucts from phone book -----------------------------------
     */
    public void allPhoneContauct() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phnCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                    new String[]{id}, null);
            while (phnCursor.moveToNext()) {

                String number = phnCursor.getString(phnCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (number != null) {
                    getPhnCnts.add(new getSetContaucts(id, name, number));
                    break;
                }
            }

        }
        sortingClass sr = new sortingClass(getPhnCnts);
        SrtgetPhnCnts = sr.Sorting_Data();
    }

    /*-----------------------------------end of get all contaucts----------------------------------------
     * -------------------------------------------------------------------------------------------------*/
    //----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*---------------------------------------------------------------------------------------------------
     * ------------------------------get all  call log from phone book ----------------------
     */
    public void getAllCallLog() {
        try {

        //------------call log permission-----------------------
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        }
        //------------------------------------------------------
        Cursor cursor = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        while (cursor.moveToNext()) {
            String phNumber = "";
            phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            String cName;
            cName = cursor.getString(name);
            if (cName==null) cName = "Unknown";
            Date callDayTime = new Date(Long.valueOf(callDate));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
            String dte = simpleDateFormat.format(callDayTime);
            String callDuration = cursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
                    default:
                        dir = "EXIT";
                        break;
            }
                getLogs.add(new getSetLogDetails(cName,phNumber,dir,dte));
            }
        }catch (Exception e){}

    }
    /*-----------------------------------end of get all call log-------------------------------------------
     * -------------------------------------------------------------------------------------------------*/
//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*-----------------------------------get voice from user methods-------------------------------------
    ---------------------------------------------------------------------------------------------------*/
    public void getSpeech(View view)
    {
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent,10);
            }else{
                Toast.makeText(this, "Your device don't support Speech input",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case 10:
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtv.setText(result.get(0));
                    ttext = null;
                    ttext = result.get(0);

                    break;
            }
        }catch (Exception e){
            ttext = null;
        }
        if (ttext!=""){
            voiceTakeNameCallMethod();
        }
    }
    /*-------------------------------------end of get voice from user------------------------------------
    ---------------------------------------------------------------------------------------------------*/
//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*------------------------------------- finally calling method using voice---------------------------
    * ---------------------------------------------------------------------------------------------------*/
    public void voiceTakeNameCallMethod(){
        String nm;
        int c=0;
        try {
            for (int i=0;i<getPhnCnts.size();i++)
            {
                nm = getPhnCnts.get(i).getPC_name().toString();
                if (ttext.toString().toLowerCase().equals(nm.toLowerCase())){
                    String dial = "tel:"+getPhnCnts.get(i).getPC_phone().toString();
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!=
                            PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {Manifest.permission.CALL_PHONE},1);

                    }else{
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }
                    c=1;
                    ttext="";
                    break;
                }
            }
            if(c==0){
                Toast.makeText(this, ttext+" Not found",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){}
    }
    /*--------------------------------------end of voice calling method-----------------------------------
    * --------------------------------------------------------------------------------------------------*/

    //----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CALL_LOG)==PackageManager.PERMISSION_GRANTED){

                    }else{
                        Toast.makeText(this,"No Permission",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
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

}
