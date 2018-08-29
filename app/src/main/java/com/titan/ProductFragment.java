package com.titan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;


public class ProductFragment extends Fragment implements View.OnClickListener{

    View v;
    Button btn;
    FrameLayout frameLayout;


    public ProductFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v =  inflater.inflate(R.layout.fragment_product, container, false);
        btn = (Button) v.findViewById(R.id.elec);
        frameLayout = (FrameLayout) v.findViewById(R.id.product_frame);
        btn.setOnClickListener(this);
        return v;
    }
    public void onClick(View view)
    {
        if(view.getId() == R.id.elec)
        {
            Intent intent = new Intent(getActivity(),Electrical.class);
            startActivity(intent);

        }
    }

}
