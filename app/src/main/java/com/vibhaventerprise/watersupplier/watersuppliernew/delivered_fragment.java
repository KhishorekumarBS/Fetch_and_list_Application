package com.vibhaventerprise.watersupplier.watersuppliernew;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.w3c.dom.Text;

public class delivered_fragment extends Fragment {
    TextView NameTV;
    TextView TimeTV;
    TextView AddressTV;
    TextView nila;
    TextView aquafina;
    TextView bisleri;
    String model1,model2,model3;
    Button CallBTN;
    FrameLayout record;
    public delivered_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_delivered, container, false);
        NameTV=(TextView)view.findViewById(R.id.Nametext);
        TimeTV=(TextView)view.findViewById(R.id.Timetext);
        AddressTV=(TextView)view.findViewById(R.id.addresstext);
        CallBTN= (Button) view.findViewById(R.id.callbtn);
        nila=(TextView) view.findViewById(R.id.Model1_done);
        bisleri=(TextView) view.findViewById(R.id.Model3_done);
        aquafina=(TextView) view.findViewById(R.id.Model2_done);
        record=(FrameLayout)view.findViewById(R.id.record_delivered);

        model1="0";
        model2="0";
        model3="0";

        String Name="Name: "+getArguments().getString("Name")+" ( "+getArguments().getString("Number")+" ) ";
        NameTV.setText(Name);
        TimeTV.setText("Time: "+getArguments().getString("Time"));
        AddressTV.setText("Address: "+getArguments().getString("Address"));
        nila.setText("Nila: "+getArguments().getString("Nila"));
        aquafina.setText("Aquafina: "+getArguments().getString("Aquafina"));
        bisleri.setText("Bisleri: "+getArguments().getString("Bisleri"));
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


        return view;

    }
}
