package com.vibhaventerprise.watersupplier.watersuppliernew;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class record_fragment extends Fragment {
    TextView NameTV;
    TextView TimeTV;
    Button CallBTN;
    CheckBox checkbox1;
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
        CallBTN= (Button) view.findViewById(R.id.callbtn);
        checkbox1=(CheckBox)view.findViewById(R.id.delivery_status);

        NameTV.setText(getArguments().getString("Name"));
        TimeTV.setText(getArguments().getString("Time"));

        CallBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:9789531713"));
               /* if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                    if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(getActivity(),String[]{Manifest.permission.CALL_PHONE},Request);
                    }
                 */
                startActivity(callIntent);
            }
        });

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Fragment frag1=getFragmentManager().findFragmentByTag(getArguments().getString("Tag"));
                    if(frag1!=null)
                    {
                        getFragmentManager().beginTransaction().remove(frag1).commit();
                    }

                }
            }
        });
        return view;

    }

}
