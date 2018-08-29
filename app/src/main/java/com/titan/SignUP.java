package com.titan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUP extends AppCompatActivity implements View.OnClickListener {

    Button btnSignup;
    EditText input_email,input_pass;
    LinearLayout activity_sign_up;

    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
    Snackbar snackbar;

    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        input_email = (EditText)findViewById(R.id.signup_email);
        input_pass =(EditText)findViewById(R.id.signup_password);
        btnSignup = (Button)findViewById(R.id.signup_btn_register);
        activity_sign_up = (LinearLayout)findViewById(R.id.activity_sign_up);
        btnSignup.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.signup_btn_register)
        {
            if(input_email.getText().length()==0)
            {
                input_email.setError("Please Enter Your Email");
            }
            else if(input_pass.getText().length()==0)
            {
                input_pass.setError("Please Enter Password");
            }
            else
            {
                signUpUser(input_email.getText().toString(),input_pass.getText().toString());
            }
        }
    }

    private void signUpUser(String email,String password)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            final ProgressDialog pd = new ProgressDialog(SignUP.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Creating User....");
                            pd.setIndeterminate(false);
                            pd.show();
                            progressStatus = 0;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while(progressStatus < 100){
                                        progressStatus +=1;
                                        try
                                        {
                                            Thread.sleep(50);
                                        }catch(InterruptedException e)
                                        {
                                            e.printStackTrace();
                                        }
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.setProgress(progressStatus);

                                                if(progressStatus == 70)
                                                {
                                                    pd.dismiss();
                                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_sign_up),
                                                            R.string.SignUpSuccess, Snackbar.LENGTH_LONG);
                                                    startActivity(new Intent(SignUP.this,DashBoard.class));
                                                    mySnackbar.show();
                                                    input_email.setText("");
                                                    input_pass.setText("");
                                                }
                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                        else
                        {
                            final ProgressDialog pd = new ProgressDialog(SignUP.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Creating User....");
                            pd.setIndeterminate(false);
                            pd.show();
                            progressStatus = 0;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while(progressStatus < 100){
                                        progressStatus +=1;
                                        try
                                        {
                                            Thread.sleep(50);
                                        }catch(InterruptedException e)
                                        {
                                            e.printStackTrace();
                                        }
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.setProgress(progressStatus);

                                                if(progressStatus == 70)
                                                {
                                                    pd.dismiss();
                                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_sign_up),
                                                            R.string.SignUPFailed, Snackbar.LENGTH_SHORT);
                                                    mySnackbar.show();
                                                    input_email.setText("");
                                                    input_pass.setText("");
                                                }
                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                    }
                });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(SignUP.this,Login.class));
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
