package com.photo.retroft_signupdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private ProgressDialog pDialog;
    /*@Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)*/
    TextView link_login;
    Button btn_signup;
    EditText name,email,password,reEnterPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);


        name=findViewById(R.id.input_name);
        email=findViewById(R.id.input_email);
        password=findViewById(R.id.input_password);
        reEnterPassword=findViewById(R.id.input_reEnterPassword);

        link_login=findViewById(R.id.link_login);
        btn_signup=findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the<a class="Eyqj4pXnY " style="z-index: 2147483647;" title="Click to Continue > by Advertise" href="#2293366"> registration<img src="http://cdncache-a.akamaihd.net/items/it/img/arrow-10x10.png" /></a> screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (validate() == false) {
            onSignupFailed();
            return;
        }
       // saveToServerDB();

    }


    public void onSignupSuccess() {
        btn_signup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String Name =name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ReEnterPassword = reEnterPassword.getText().toString();

        if (Name.isEmpty() || Name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }


        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (ReEnterPassword.isEmpty() || ReEnterPassword.length() < 4 || ReEnterPassword.length() > 10 || !(ReEnterPassword.equals(password))) {
            reEnterPassword.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPassword.setError(null);
        }

        return valid;
    }

    private void saveToServerDB() {
        pDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();


        APIService service = ApiClinet.getClient().create(APIService.class);
        //User user = new User(name, email,password);


        Call<MSG> userCall = service.userSignUp(Name, Email, Password);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                Log.d("onResponse", "" + response.body().getMessage());


                if(response.body().getSuccess() == 1) {
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));

                    finish();
                }else {
                    Toast.makeText(SignupActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}