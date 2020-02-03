package com.example.myclosetapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.myclosetapp.R;
import com.example.myclosetapp.activities.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        //todo come back here for facebook login

//        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        loginButton.setReadPermissions(Arrays.asList("email","public-profile"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback <LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                String acessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("photo",getFBData(object));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


    }

    private String getFBData(JSONObject object) {
        String pic ="";
        try{
            URL profile_pic = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?type=large");
            pic = "https://graph.facebook.com/"+object.getString("id")+"/picture?type=large";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pic;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
