package com.desafiolatam.desafioface.views.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desafiolatam.desafioface.R;

public class LoginActivity extends AppCompatActivity implements SessionCallback {

    private TextInputLayout mailWrapper, passWrapper;
    private EditText mailEt, passET;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailWrapper = (TextInputLayout) findViewById(R.id.emailTil);
        passWrapper = (TextInputLayout) findViewById(R.id.passwordTil);
        mailEt = (EditText) findViewById(R.id.emailEt);
        passET = (EditText) findViewById(R.id.passwordEt);
        button = (Button) findViewById(R.id.signBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mailEt.getText().toString();
                String password = passET.getText().toString();
                mailWrapper.setVisibility(View.GONE);
                passWrapper.setVisibility(View.GONE);
                button.setVisibility(View.GONE);

                new Signin(LoginActivity.this).toServer(email, password);

            }
        });


    }


    private void restoreView() {
        mailEt.setError(null);
        passET.setError(null);
        mailWrapper.setVisibility(View.VISIBLE);
        passWrapper.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);

    }


    @Override
    public void requieredField() {
        restoreView();
        mailEt.setError("REQUERIDO");
        passET.setError("REQUERIDO");
    }

    @Override
    public void mailFormat() {
        restoreView();
        mailEt.setError("Formato incorrecto");

    }

    @Override
    public void success() {
        Toast.makeText(this, "Funciono", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void failure() {
        restoreView();
        Toast.makeText(this, "mail o password Incorrecto", Toast.LENGTH_SHORT).show();
    }


}
