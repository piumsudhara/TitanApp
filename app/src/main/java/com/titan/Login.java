package com.titan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText input_email, input_password;
    TextView btnSignup, btnForgotPass;
    Button btnLogin;
    LinearLayout activity_login;
    private FirebaseAuth auth;
    Snackbar snackbar;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_email = (EditText) findViewById(R.id.login_email);
        input_password = (EditText) findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnForgotPass = (TextView) findViewById(R.id.login_btn_forgot_password);
        btnSignup = (TextView) findViewById(R.id.login_btn_signup);

        btnSignup.setPaintFlags(btnSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnForgotPass.setPaintFlags(btnForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null)
        {
            startActivity(new Intent(Login.this,DashBoard.class));
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.login_btn_login)
        {
            if (input_email.getText().length() == 0)
            {
                input_email.setError("Please Enter Your Email");
            }
            else if (input_password.getText().length() == 0)
            {
                input_password.setError("Please Enter Your Password");
            }
            else {
                loginUser(input_email.getText().toString(), input_password.getText().toString());
            }
        }
        else if(view.getId()==R.id.login_btn_signup)
        {
            startActivity(new Intent(Login.this,SignUP.class));
            finish();
        }
        else if(view.getId()==R.id.login_btn_forgot_password)
        {
            startActivity(new Intent(Login.this,ForgotPass.class));
            finish();
        }
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final ProgressDialog pd = new ProgressDialog(Login.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Authenticating....");
                            pd.setIndeterminate(false);
                            pd.show();
                            progressStatus = 0;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (progressStatus < 100) {
                                        progressStatus += 1;
                                        try {
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.setProgress(progressStatus);

                                                if (progressStatus == 50) {
                                                    pd.dismiss();
                                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_login),
                                                            R.string.AuthenticationSuccess, Snackbar.LENGTH_SHORT);
                                                    mySnackbar.show();
                                                    startActivity(new Intent(Login.this,DashBoard.class));
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            }).start();
                        } else {
                            final ProgressDialog pd = new ProgressDialog(Login.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Authenticating....");
                            pd.setIndeterminate(false);
                            pd.show();
                            progressStatus = 0;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (progressStatus < 100) {
                                        progressStatus += 1;
                                        try {
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.setProgress(progressStatus);

                                                if (progressStatus == 50) {
                                                    pd.dismiss();
                                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_login),
                                                            R.string.AuthenticationFailed, Snackbar.LENGTH_SHORT);
                                                    mySnackbar.show();
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }
}
