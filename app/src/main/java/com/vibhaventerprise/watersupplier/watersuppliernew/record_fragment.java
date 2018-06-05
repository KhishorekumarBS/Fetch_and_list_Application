package com.vibhaventerprise.watersupplier.watersuppliernew;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class record_fragment extends Fragment {
    TextView NameTV;
    TextView TimeTV;
    TextView AddressTV;
    Button CallBTN;
    CheckBox checkbox1;
    ElegantNumberButton nila;
    ElegantNumberButton aquafina;
    ElegantNumberButton bisleri;
    String model1,model2,model3;
    FrameLayout record;
    public record_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_record, container, false);
        NameTV=(TextView)view.findViewById(R.id.Nametext);
        TimeTV=(TextView)view.findViewById(R.id.Timetext);
        AddressTV=(TextView)view.findViewById(R.id.addresstext);
        CallBTN= (Button) view.findViewById(R.id.callbtn);
        checkbox1=(CheckBox)view.findViewById(R.id.delivery_status);
        nila=(ElegantNumberButton) view.findViewById(R.id.Model1);
        bisleri=(ElegantNumberButton) view.findViewById(R.id.Model3);
        aquafina=(ElegantNumberButton) view.findViewById(R.id.Model2);
        record=(FrameLayout)view.findViewById(R.id.record);
        model1="0";
        model2="0";
        model3="0";

        String Name="Name: "+getArguments().getString("Name")+" ( "+getArguments().getString("Number")+" ) ";
        NameTV.setText(Name);
        TimeTV.setText("Time: "+getArguments().getString("Time"));
        AddressTV.setText("Address: "+getArguments().getString("Address"));
        if(Integer.parseInt(getArguments().getString("Tag"))%2==0)
            record.setBackgroundColor(Color.WHITE);


        CallBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                String Phonecall="tel:"+getArguments().getString("Number");
                callIntent.setData(Uri.parse(Phonecall));
               /* if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                    if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(getActivity(),String[]{Manifest.permission.CALL_PHONE},Request);
                    }
                 */
                startActivity(callIntent);
            }
        });

        nila.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                model1=nila.getNumber();

            }
        });

        aquafina.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                model2=aquafina.getNumber();

            }
        });

        bisleri.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                model3=bisleri.getNumber();

            }
        });

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {

                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you delivered "+model1+" nila, "+model2+" aquafina and "+model3+" bisleri cans to "+getArguments().getString("Name")+"?")
                           .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Fragment frag1=getFragmentManager().findFragmentByTag(getArguments().getString("Tag"));
                                        getFragmentManager().beginTransaction().remove(frag1).commit();
                                        sendData(getArguments().getString("Id"));
                                    }
                                })
                           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   checkbox1.toggle();
                               }
                           });
                    AlertDialog alert=builder.create();
                    alert.show();

                }
            }
        });
        return view;

    }
    void sendData(String id)
    {
        try
        {
            String URL="http://35.198.198.84:31960/update/webresources/testParam/passpath/"+id+"/"+model1+"/"+model2+"/"+model3;
            final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            StringRequest stringrequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("helo----------","Done");
                    queue.stop();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    queue.stop();
                }
            });
            queue.add(stringrequest);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



}
