package com.vibhaventerprise.watersupplier.watersuppliernew;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import adapters.AsyncResponse;


public class TestActivity extends AppCompatActivity implements AsyncResponse{

   public static TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        adapters.DataFetch process= new adapters.DataFetch();
        process.delegate=this;
        process.execute();


    }

    @Override
    public void processFinish(JSONArray JB) {
        Bundle bundle=new Bundle();
        bundle.putString("Name","Hello");
        bundle.putString("Time","HI");
        bundle.putString("Tag","1");
        record_fragment frag = new record_fragment();
        frag.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmanager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragtransac=fragmanager.beginTransaction();
        fragtransac.add(R.id.fragment_container, frag,"1");
        fragtransac.commit();
/*
        Bundle bundle1=new Bundle();
        bundle.putString("Name","Hello2");
        bundle.putString("Time","HI2");
        bundle.putString("Tag","2");
        record_fragment frag2 = new record_fragment();
        android.support.v4.app.FragmentTransaction fragtransac2=fragmanager.beginTransaction();
        frag.setArguments(bundle1);
        fragtransac2.add(R.id.fragment_container, frag2,"2");
        fragtransac2.commit();

        /*for(int i=0;i<JB.length();i++)
        {
            try {
                JSONObject JO = (JSONObject) JB.get(i);
                // singleParsed=   "bool" + " " + JO.get("bool") + "\n" + "date" + " "+JO.get("date") +"\n" + "number"  + " "+ JO.get("number") +"\n";
                //dataParsed = dataParsed + singleParsed;
                Bundle bundle=new Bundle();
                bundle.putString("Name",JO.get("number").toString());
                bundle.putString("Time",JO.get("date").toString());
                record_fragment frag = new record_fragment();
                frag.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmanager=getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragtransac=fragmanager.beginTransaction();
                fragtransac.add(R.id.fragment_container, frag);
                fragtransac.commit();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }*/

        /*try {
            JSONObject JO = (JSONObject) JB.get(3);
            Toast.makeText(this, JO.get("number").toString(), Toast.LENGTH_SHORT).show();
            frag.changetext(JO.get("date").toString(), JO.get("number").toString());
            fragtransac.add(R.id.fragment_container, frag);
            fragtransac.commit();
        }
        catch(Exception e)
        {

        }
        for(int i=0;i<JB.length();i++)
        {
            try {
                JSONObject JO = (JSONObject) JB.get(i);
                // singleParsed=   "bool" + " " + JO.get("bool") + "\n" + "date" + " "+JO.get("date") +"\n" + "number"  + " "+ JO.get("number") +"\n";
                //dataParsed = dataParsed + singleParsed;
                record_fragment frag = new record_fragment();
                Toast.makeText(this,JO.get("number").toString(), Toast.LENGTH_SHORT).show();
                frag.changetext(JO.get("date").toString(), JO.get("number").toString());
                fragtransac.add(R.id.fragment_container, frag);
                fragtransac.commit();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }*/

    }
}
