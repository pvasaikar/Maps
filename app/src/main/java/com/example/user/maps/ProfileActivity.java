package com.example.user.maps;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;

public class ProfileActivity extends AppCompatActivity {

    private TextView username;
    private TextView useremail;
    private ImageView userphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username= (TextView) findViewById(R.id.userName);
        useremail=(TextView) findViewById(R.id.userEmail);
        userphoto=(ImageView)findViewById(R.id.userPhoto);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //GoogleSignInAccount account = (GoogleSignInAccount) getIntent().getExtras().get("account");

        useremail.setText(account.getEmail());
        username.setText(account.getDisplayName());

        Picasso.with(this)
                .load(account.getPhotoUrl())
                .into(userphoto);

    }

}
