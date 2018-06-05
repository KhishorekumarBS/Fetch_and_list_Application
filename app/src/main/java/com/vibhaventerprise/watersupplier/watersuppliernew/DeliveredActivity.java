package com.vibhaventerprise.watersupplier.watersuppliernew;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adapters.AsyncResponse;

public class DeliveredActivity extends AppCompatActivity implements AsyncResponse {

    SwipeRefreshLayout refreshview;
    Button Fromdateget,Todateget;
    TextView Fromdatedisplay,Todatedisplay,TotalTV;
    int day_x,month_x,year_x;
    static final int DIALOG_ID=0;
    static final int DIALOG_ID1=1;
    boolean flagset=false;
    String fromdate,todate;
    int nila_total=0,aquafina_total=0,bisleri_total=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered);

        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        adapters.DataFetch process= new adapters.DataFetch();
        process.delegate=this;
        process.execute();

        Fromdateget=(Button)findViewById(R.id.fromselect);
        Todateget=(Button)findViewById(R.id.toselect);
        Fromdatedisplay=(TextView)findViewById(R.id.fromdisplay);
        Todatedisplay=(TextView)findViewById(R.id.todisplay);
        TotalTV=(TextView)findViewById(R.id.total_display);


        Fromdateget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        Todateget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID1);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,fromlistener,year_x,month_x,day_x);
        else if(id==DIALOG_ID1)
            return new DatePickerDialog(this,tolistener,year_x,month_x,day_x);
        else
            return null;
    }

    private DatePickerDialog.OnDateSetListener fromlistener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x=year;
            month_x=month+1;
            day_x=dayOfMonth;
            fromdate=Integer.toString(year_x)+"/"+Integer.toString(month_x)+"/"+Integer.toString(day_x);
            Fromdatedisplay.setText(fromdate);

        }
    };

    private DatePickerDialog.OnDateSetListener tolistener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x=year;
            month_x=month+1;
            day_x=dayOfMonth;
            todate=Integer.toString(year_x)+"/"+Integer.toString(month_x)+"/"+Integer.toString(day_x);
            Todatedisplay.setText(todate);
            flagset=true;
            for(Fragment fragment:getSupportFragmentManager().getFragments())
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            adapters.DataFetch process= new adapters.DataFetch();
            process.delegate=DeliveredActivity.this;
            process.execute();



        }
    };

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
            Fromdatedisplay.setText(" ");
            Todatedisplay.setText(" ");
            flagset=false;
            adapters.DataFetch process= new adapters.DataFetch();
            process.delegate=this;
            process.execute();
        }
        else if(res_id==R.id.undelivered_menu)
        {
            startActivity(new Intent(this,TestActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void processFinish(JSONArray JB) {
        if(flagset)
        {
            nila_total=0;
            aquafina_total=0;
            bisleri_total=0;

            for(int i=0;i<JB.length();i++)
            {
                try {
                    JSONObject JO = (JSONObject) JB.get(i);
                    String Datetemp=JO.get("date").toString().substring(0,10);
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/mm/dd");
                    Date datepresent = dateformat.parse(Datetemp);
                    Date datefrom = dateformat.parse(fromdate);
                    Date dateto = dateformat.parse(todate);

                    if(JO.get("del").toString()=="true" && datepresent.compareTo(datefrom)>0 && datepresent.compareTo(dateto)<0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Number", JO.get("number").toString());
                        bundle.putString("Time", JO.get("date").toString());
                        bundle.putString("Address", JO.get("addr").toString());
                        bundle.putString("Name", JO.get("name").toString());
                        bundle.putString("Id", JO.get("id").toString());
                        bundle.putString("Nila", JO.get("it_one").toString());
                        bundle.putString("Aquafina",JO.get("it_two").toString());
                        bundle.putString("Bisleri",JO.get("it_three").toString());
                        nila_total=nila_total+Integer.parseInt(JO.get("it_one").toString());
                        aquafina_total=aquafina_total+Integer.parseInt(JO.get("it_two").toString());
                        bisleri_total=bisleri_total+Integer.parseInt(JO.get("it_three").toString());
                        bundle.putString("Tag", Integer.toString(i));
                        delivered_fragment frag=new delivered_fragment();
                        frag.setArguments(bundle);
                        android.support.v4.app.FragmentManager fragmanager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragtransac = fragmanager.beginTransaction();
                        fragtransac.add(R.id.fragment_container_done, frag, Integer.toString(i));
                        fragtransac.commit();
                    }
                    TotalTV.setText("Nila: "+Integer.toString(nila_total)+"  Aquafina: "+Integer.toString(aquafina_total)+"  Bisleri: "+Integer.toString(bisleri_total));


                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
            return;

        }
        nila_total=0;
        aquafina_total=0;
        bisleri_total=0;
        for(int i=0;i<JB.length();i++)
        {
            try {
                JSONObject JO = (JSONObject) JB.get(i);
                // singleParsed=   "bool" + " " + JO.get("bool") + "\n" + "date" + " "+JO.get("date") +"\n" + "number"  + " "+ JO.get("number") +"\n";
                //dataParsed = dataParsed + singleParsed;
                if(JO.get("del").toString()=="true") {
                    Bundle bundle = new Bundle();
                    bundle.putString("Number", JO.get("number").toString());
                    bundle.putString("Time", JO.get("date").toString());
                    bundle.putString("Address", JO.get("addr").toString());
                    bundle.putString("Name", JO.get("name").toString());
                    bundle.putString("Id", JO.get("id").toString());
                    bundle.putString("Nila", JO.get("it_one").toString());
                    bundle.putString("Aquafina",JO.get("it_two").toString());
                    bundle.putString("Bisleri",JO.get("it_three").toString());
                    nila_total=nila_total+Integer.parseInt(JO.get("it_one").toString());
                    aquafina_total=aquafina_total+Integer.parseInt(JO.get("it_two").toString());
                    bisleri_total=bisleri_total+Integer.parseInt(JO.get("it_three").toString());
                    bundle.putString("Tag", Integer.toString(i));
                    delivered_fragment frag=new delivered_fragment();
                    frag.setArguments(bundle);
                    android.support.v4.app.FragmentManager fragmanager = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragtransac = fragmanager.beginTransaction();
                    fragtransac.add(R.id.fragment_container_done, frag, Integer.toString(i));
                    fragtransac.commit();
                }
                TotalTV.setText("Nila: "+Integer.toString(nila_total)+"  Aquafina: "+Integer.toString(aquafina_total)+"  Bisleri: "+Integer.toString(bisleri_total));

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
}
