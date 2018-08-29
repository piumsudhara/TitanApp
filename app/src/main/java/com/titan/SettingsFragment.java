package com.titan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    View v;
    Button btn,ebtn;
    EditText pass;
    EditText email;
    Snackbar snackbar;
    LinearLayout linearLayout;

    FirebaseAuth auth;


    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v =  inflater.inflate(R.layout.fragment_settings, container, false);
        pass = (EditText)v.findViewById(R.id.change_pass);
        email = (EditText)v.findViewById(R.id.change_email);
        btn = (Button) v.findViewById(R.id.change_btn);
        ebtn = (Button)v.findViewById(R.id.change_email_btn);
        linearLayout = (LinearLayout)v.findViewById(R.id.settings_frame);
        btn.setOnClickListener(this);
        ebtn.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        return v;
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.change_email_btn)
        {
            if(email.getText().length()==0)
            {
                email.setError("Please Enter Your Email");
            }
            else
            {
                changeEmail(email.getText().toString());
            }
        }
        if(view.getId() == R.id.change_btn)
        {
            if(pass.getText().length()==0)
            {
                pass.setError("Please Enter Your Password");
            }
            else
            {
                changePassword(pass.getText().toString());
            }
        }
    }
    private void changeEmail(String newEmail)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            snackbar = Snackbar.make(linearLayout,"Email Changed Successfully",4000);
                            snackbar.show();
                            email.setText("");
                        }
                    }
                });
    }
    private void changePassword(String newPassword)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            snackbar = Snackbar.make(linearLayout,"Password Changed Successfully",4000);
                            snackbar.show();
                            pass.setText("");
                        }
                    }
                });
    }
}
