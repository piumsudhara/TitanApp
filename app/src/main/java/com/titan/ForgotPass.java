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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private Button btnResetPass;
    private LinearLayout activity_forgot;

    private FirebaseAuth auth;
    private Snackbar snackbar;

    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        input_email = (EditText)findViewById(R.id.forgot_email);
        btnResetPass = (Button)findViewById(R.id.forgot_btn_reset);
        activity_forgot = (LinearLayout)findViewById(R.id.activity_forgot_pass);
        btnResetPass.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.forgot_btn_reset)
        {
            if(input_email.getText().length()==0)
            {
                input_email.setError("Please Enter Your Email");
            }
            else
            {
                resetPassword(input_email.getText().toString());
            }
        }
    }
    private void resetPassword(String email)
    {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            final ProgressDialog pd = new ProgressDialog(ForgotPass.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Sending....");
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
                                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_forgot_pass),
                                                            R.string.ForgotSuccess, Snackbar.LENGTH_SHORT);
                                                    mySnackbar.show();
                                                    input_email.setText("");
                                                }
                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                        else
                        {
                            final ProgressDialog pd = new ProgressDialog(ForgotPass.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Sending....");
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
                                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_forgot_pass),
                                                            R.string.ForgotFailed, Snackbar.LENGTH_SHORT);
                                                    mySnackbar.show();
                                                    input_email.setText("");
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
            startActivity(new Intent(ForgotPass.this,Login.class));
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
