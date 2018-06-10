package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.ServerProtocol;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.provatsoft.apps.flag71.blls.OnProfileFetchListener;
import com.provatsoft.apps.flag71.blls.ProfileManager;
import com.provatsoft.apps.flag71.blls.ProfileManager.OnFetchProfilePictureListener;
import com.provatsoft.apps.flag71.models.UserProfile;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements OnFetchProfilePictureListener, OnProfileFetchListener {
    private CallbackManager callbackManager;
    private ImageView imageView;
    private LoginButton loginButton;
    private TextView messageTextView;
    ProfileManager propicManager;

    /* renamed from: com.provatsoft.apps.flag71.LoginActivity.1 */
    class C07591 implements FacebookCallback<LoginResult> {
        C07591() {
        }

        public void onSuccess(LoginResult loginResult) {
            LoginActivity.this.setResult(-1, new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }

        public void onCancel() {
            LoginActivity.this.messageTextView.setText("Login attempt canceled.");
        }

        public void onError(FacebookException e) {
            String message = e.getMessage();
            if (message.contains(ServerProtocol.errorConnectionFailure)) {
                LoginActivity.this.messageTextView.setText("Failed to connect with Facebook.");
            } else {
                LoginActivity.this.messageTextView.setText(message);
            }
        }
    }

    public LoginActivity() {
        this.propicManager = new ProfileManager(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.callbackManager = Factory.create();
        setContentView((int) C0467R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.imageView = (ImageView) findViewById(C0467R.id.imageView);
        this.loginButton = (LoginButton) findViewById(C0467R.id.login_button);
        this.loginButton.setReadPermissions(Arrays.asList(new String[]{"public_profile"}));
        this.messageTextView = (TextView) findViewById(C0467R.id.messageTextView);
        this.loginButton.registerCallback(this.callbackManager, new C07591());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void nextActivity(UserProfile profile) {
        Intent i = new Intent(this, MainActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(AppConfigs.kPROFILE, profile);
        i.putExtras(mBundle);
        startActivity(i);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void postPropic(View view) {
        this.propicManager.postPropic(((BitmapDrawable) this.imageView.getDrawable()).getBitmap());
    }

    public void didFetchPropic(Bitmap propic) {
        this.imageView.setImageBitmap(propic);
    }

    public void didFetch(UserProfile userProfile) {
        this.propicManager.saveUserProfile(userProfile);
        nextActivity(userProfile);
    }

    public void didError(String message) {
        Toast.makeText(this, message, 0).show();
    }
}
