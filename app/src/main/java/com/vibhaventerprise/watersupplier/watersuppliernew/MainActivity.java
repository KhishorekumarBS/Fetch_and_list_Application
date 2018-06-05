package com.vibhaventerprise.watersupplier.watersuppliernew;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private EditText PhonenumberET;
    private EditText PasswordET;
    private Button SigninBTN;
    private Button VibhavBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref=getApplicationContext().getSharedPreferences("WaterSupplier",0);
        String Userid=pref.getString("current_user",null);
        if(Userid!=null && Userid.equals("999"))
        {
            startActivity(new Intent(MainActivity.this,TestActivity.class));
            finish();
            System.exit(0);
        }
        setContentView(R.layout.activity_main);
        PhonenumberET =(EditText)findViewById(R.id.phoneno);
        PasswordET =(EditText)findViewById(R.id.password);


        SigninBTN=(Button)findViewById(R.id.sign_in_button);
        SigninBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate(PhonenumberET.getText().toString(),PasswordET.getText().toString());
            }
        });

        VibhavBTN=(Button)findViewById(R.id.vibhavbtn);
        VibhavBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browseropen=new Intent(Intent.ACTION_VIEW, Uri.parse("http://vibhaventerprise.com"));
                startActivity(browseropen);
            }
        });
    }


    private void validate(String phone, String pass) {
        if (phone.equals("999") && pass.equals("water")) {
            SharedPreferences pref=getApplicationContext().getSharedPreferences("WaterSupplier",0);
            SharedPreferences.Editor editor=pref.edit();
            editor.putString("current_user",phone);
            editor.commit();
            startActivity(new Intent(MainActivity.this,TestActivity.class));

        }
        else
            Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show();

    }

}
