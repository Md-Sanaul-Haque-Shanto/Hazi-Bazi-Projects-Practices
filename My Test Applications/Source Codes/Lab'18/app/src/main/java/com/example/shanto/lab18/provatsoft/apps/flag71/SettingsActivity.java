package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.ServerProtocol;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer.Result;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private TextView greeting;
    private LoginButton loginButton;
    private TextView messageTextView;
    private ProfilePictureView profilePictureView;
    private ProfileTracker profileTracker;
    private FacebookCallback<Result> shareCallback;

    /* renamed from: com.provatsoft.apps.flag71.SettingsActivity.1 */
    class C07621 implements FacebookCallback<Result> {
        C07621() {
        }

        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", new Object[]{error.toString()}));
            showResult(SettingsActivity.this.getString(C0467R.string.error), error.getMessage());
        }

        public void onSuccess(Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = SettingsActivity.this.getString(C0467R.string.success);
                String id = result.getPostId();
                showResult(title, SettingsActivity.this.getString(C0467R.string.successfully_posted_post, new Object[]{id}));
            }
        }

        private void showResult(String title, String alertMessage) {
            new Builder(SettingsActivity.this).setTitle(title).setMessage(alertMessage).setPositiveButton(C0467R.string.ok, null).show();
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.SettingsActivity.2 */
    class C07632 extends ProfileTracker {
        C07632() {
        }

        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            SettingsActivity.this.updateUI();
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.SettingsActivity.3 */
    class C07643 implements FacebookCallback<LoginResult> {
        C07643() {
        }

        public void onSuccess(LoginResult loginResult) {
            SettingsActivity.this.updateUI();
        }

        public void onCancel() {
            SettingsActivity.this.updateUI();
        }

        public void onError(FacebookException exception) {
            SettingsActivity.this.updateUI();
            String message = exception.getMessage();
            if (message.contains(ServerProtocol.errorConnectionFailure)) {
                SettingsActivity.this.messageTextView.setText("Failed to connect with Facebook.");
            } else {
                SettingsActivity.this.messageTextView.setText(message);
            }
        }
    }

    public SettingsActivity() {
        this.shareCallback = new C07621();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.callbackManager = Factory.create();
        setContentView((int) C0467R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.profileTracker = new C07632();
        this.profilePictureView = (ProfilePictureView) findViewById(C0467R.id.profilePicture);
        this.greeting = (TextView) findViewById(C0467R.id.greeting);
        this.loginButton = (LoginButton) findViewById(C0467R.id.login_button);
        this.loginButton.setReadPermissions(Arrays.asList(new String[]{"public_profile"}));
        this.messageTextView = (TextView) findViewById(C0467R.id.messageTextView);
        this.callbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new C07643());
        updateUI();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (!enableButtons || profile == null) {
            this.profilePictureView.setProfileId(null);
            this.greeting.setText(null);
            return;
        }
        this.profilePictureView.setProfileId(profile.getId());
        this.greeting.setText(profile.getFirstName() + " " + profile.getLastName());
    }
}
