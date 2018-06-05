package com.vibhaventerprise.watersupplier.watersuppliernew;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import adapters.AsyncResponse;


public class TestActivity extends AppCompatActivity implements AsyncResponse{

   SwipeRefreshLayout refreshview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //refreshview=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        adapters.DataFetch process= new adapters.DataFetch();
        if(isConnectedToInternet())
        {
            process.delegate=this;
            process.execute();
        }
        else
        {
            Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
        }


        /*
        refreshview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapters.DataFetch process= new adapters.DataFetch();
                process.delegate=TestActivity.this;
                process.execute();
            }
        });
*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflator=getMenuInflater();
        menuinflator.inflate(R.menu.main_menu,menu);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id=item.getItemId();
        if(res_id==R.id.refresh_menu)
        {
            for(Fragment fragment:getSupportFragmentManager().getFragments())
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            adapters.DataFetch process= new adapters.DataFetch();
            if(isConnectedToInternet())
            {
                process.delegate=this;
                process.execute();
            }
            else
            {
                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
            }

        }
        else if(res_id==R.id.delivered_menu)
        {
           startActivity(new Intent(this,DeliveredActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void processFinish(JSONArray JB) {
       /* Bundle bundle=new Bundle();
        bundle.putString("Name","Hello");
        bundle.putString("Time","HI");
        bundle.putString("Tag","1");
        record_fragment frag = new record_fragment();
        frag.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmanager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragtransac=fragmanager.beginTransaction();
        fragtransac.add(R.id.fragment_container, frag,"1");
        fragtransac.commit();
*/

        for(int i=0;i<JB.length();i++)
        {
            try {
                JSONObject JO = (JSONObject) JB.get(i);
                // singleParsed=   "bool" + " " + JO.get("bool") + "\n" + "date" + " "+JO.get("date") +"\n" + "number"  + " "+ JO.get("number") +"\n";
                //dataParsed = dataParsed + singleParsed;
                if(JO.get("del").toString()=="false") {
                    Bundle bundle = new Bundle();
                    bundle.putString("Number", JO.get("number").toString());
                    bundle.putString("Time", JO.get("date").toString());
                    bundle.putString("Address", JO.get("addr").toString());
                    bundle.putString("Name", JO.get("name").toString());
                    bundle.putString("Id", JO.get("id").toString());
                    bundle.putString("Tag", Integer.toString(i));
                    record_fragment frag = new record_fragment();
                    frag.setArguments(bundle);
                    android.support.v4.app.FragmentManager fragmanager = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragtransac = fragmanager.beginTransaction();
                    fragtransac.add(R.id.fragment_container, frag, Integer.toString(i));
                    fragtransac.commit();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

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

    public boolean isConnectedToInternet(){
        ConnectivityManager cm=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm!=null)
        {
            NetworkInfo[] info=cm.getAllNetworkInfo();
            if(info!=null)
                for(int i=0;i<info.length;i++)
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
        }
        return false;
    }
}
