/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>this activity used for send message>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>*/public class MessagingActivity extends AppCompatActivity {/*>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    private String phone="";
    private String name="";
    private EditText phoneedittext;
    private EditText message;
    private ImageButton getVms;
    private ImageView sendMsg;
    private String ttext = "";
    private String fullMessage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        try{
            Intent intent = getIntent();
            name = intent.getStringExtra(RecyclerViewClass.ContauctsName);
            phone = intent.getStringExtra(RecyclerViewClass.ContauctsPhone);
        }catch (Exception e){}

        phoneedittext = (EditText)findViewById(R.id.name_edittext_fromvoice);
        message = (EditText) findViewById(R.id.text_message_id);
        getVms = (ImageButton) findViewById(R.id.get_voice_message_id);
        sendMsg = (ImageView) findViewById(R.id.message_send_button_id);

        phoneedittext.setText(phone+"("+name+")");

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callingAndMessegingClass msg = new callingAndMessegingClass(fullMessage,phone,
                        MessagingActivity.this);
                msg.messagingMethod();
            }
        });

        getVms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().isEmpty()) fullMessage="";
                getSpeech(view);
            }
        });

    }
    public void getSpeech(View view)
    {
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            if(intent.resolveActivity(this.getPackageManager()) != null){
                startActivityForResult(intent,10);
            }else{
                Toast.makeText(this, "Your device don't support Speech input",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case 10:
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtv.setText(result.get(0));
                    ttext = "";
                    ttext = result.get(0);
                    break;
            }
        }catch (Exception e){
            ttext = "";
        }

        if(fullMessage=="") fullMessage = ttext;
        else fullMessage += " "+ttext;
        message.setText(fullMessage);
    }
    /*-------------------------------------end of get voice from user------------------------------------
    ---------------------------------------------------------------------------------------------------*/
//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
