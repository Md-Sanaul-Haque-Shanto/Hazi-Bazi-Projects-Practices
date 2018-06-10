package com.example.shanto.lab18.provatsoft.apps.flag71.blls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.internal.ShareConstants;
import com.provatsoft.apps.flag71.C0467R;
import com.provatsoft.apps.flag71.models.UserProfile;
import com.provatsoft.apps.flag71.utils.ConnectionDetector;
import com.provatsoft.apps.flag71.utils.OnErrorListener;
import com.provatsoft.apps.flag71.utils.ProgressDialogManager;
import com.provatsoft.apps.simplecorplib.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileManager {
    private final Context context;

    private class DownloadFilesTask extends AsyncTask<String, String, Bitmap> implements OnCancelListener {
        private static final int SIZE = 512;
        private ProgressDialogManager dialogManager;
        private final OnFetchProfilePictureListener listener;

        public DownloadFilesTask(OnFetchProfilePictureListener listener) {
            this.listener = listener;
            this.dialogManager = new ProgressDialogManager(ProfileManager.this.context, "Fetching...");
            this.dialogManager.setCancelable(true);
            this.dialogManager.setOnCancelListener(this);
        }

        protected void onPreExecute() {
            this.dialogManager.show();
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... params) {
            return ProfileManager.this.getFacebookProfilePicture("http://graph.facebook.com/" + params[0] + "/picture?type=large&width=" + SIZE + "&height=" + SIZE);
        }

        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                this.listener.didError("Failed to connect facebook.");
            } else {
                this.listener.didFetchPropic(result);
            }
            this.dialogManager.dismiss();
        }

        public void onCancel(DialogInterface dialog) {
            cancel(true);
            this.dialogManager.dismiss();
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.blls.ProfileManager.1 */
    class C07651 implements Callback {
        C07651() {
        }

        public void onCompleted(GraphResponse graphResponse) {
            ProfileManager.this.setAsProfilePicture(graphResponse);
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.blls.ProfileManager.2 */
    class C07662 implements Callback {
        C07662() {
        }

        public void onCompleted(GraphResponse graphResponse) {
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.blls.ProfileManager.3 */
    class C07673 implements GraphJSONObjectCallback {
        final /* synthetic */ OnProfileFetchListener val$listener;

        C07673(OnProfileFetchListener onProfileFetchListener) {
            this.val$listener = onProfileFetchListener;
        }

        public void onCompleted(JSONObject object, GraphResponse response) {
            UserProfile userProfile = new UserProfile();
            try {
                userProfile.setGender(object.getString("gender"));
                this.val$listener.didFetch(userProfile);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e2) {
                e2.printStackTrace();
                this.val$listener.didError("Failed to connect Facebook!");
            }
        }
    }

    public interface OnFetchProfilePictureListener extends OnErrorListener {
        void didFetchPropic(Bitmap bitmap);
    }

    public ProfileManager(Context context) {
        this.context = context;
    }

    public void fetchProfilePicture(OnFetchProfilePictureListener listener, String userId) {
        if (new ConnectionDetector(this.context).isConnectingToInternet()) {
            new DownloadFilesTask(listener).execute(new String[]{userId});
            return;
        }
        listener.didError("Internet connection not available.");
    }

    private Bitmap getFacebookProfilePicture(String url) {
        Bitmap bitmap = null;
        HttpGet httpRequest = new HttpGet(URI.create(url));
        try {
            bitmap = BitmapFactory.decodeStream(new BufferedHttpEntity(new DefaultHttpClient().execute(httpRequest).getEntity()).getContent());
            httpRequest.abort();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public void postPropic(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Bundle bundle = new Bundle();
        bundle.putByteArray("object_attachment", byteArray);
        bundle.putString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, "some message here");
        new GraphRequest(AccessToken.getCurrentAccessToken(), String.format("%s/photos", new Object[]{AccessToken.getCurrentAccessToken().getUserId()}), bundle, HttpMethod.POST, new C07651()).executeAsync();
    }

    public void getAlbums() {
        Bundle bundle = new Bundle();
        new GraphRequest(AccessToken.getCurrentAccessToken(), String.format("%s/albums", new Object[]{AccessToken.getCurrentAccessToken().getUserId()}), bundle, HttpMethod.POST, new C07662()).executeAsync();
    }

    private void setAsProfilePicture(GraphResponse graphResponse) {
        try {
            String photoId = graphResponse.getJSONObject().getString(ShareConstants.WEB_DIALOG_PARAM_ID);
            String userId = AccessToken.getCurrentAccessToken().getUserId();
            this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://m.facebook.com/photo.php?fbid=" + photoId + "&id=" + userId + "&prof&__user=" + userId)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchProfile(OnProfileFetchListener listener) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new C07673(listener));
        Bundle parameters = new Bundle();
        parameters.putString(GraphRequest.FIELDS_PARAM, "id,name,email,gender, birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void saveUserProfile(UserProfile userProfile) {
        Editor editor = this.context.getSharedPreferences(this.context.getString(C0467R.string.preference_file_key), 0).edit();
        editor.putString(this.context.getString(C0467R.string.user_first_name), userProfile.getFirstName());
        editor.putString(this.context.getString(C0467R.string.user_last_name), userProfile.getLastName());
        editor.putString(this.context.getString(C0467R.string.user_gender), userProfile.getGender());
        editor.commit();
    }

    public UserProfile getUserProfile() {
        SharedPreferences sharedPref = this.context.getSharedPreferences(this.context.getString(C0467R.string.preference_file_key), 0);
        String firstName = sharedPref.getString(this.context.getString(C0467R.string.user_first_name), BuildConfig.FLAVOR);
        String lastName = sharedPref.getString(this.context.getString(C0467R.string.user_last_name), BuildConfig.FLAVOR);
        String gender = sharedPref.getString(this.context.getString(C0467R.string.user_gender), BuildConfig.FLAVOR);
        UserProfile userProfile = new UserProfile();
        userProfile.setFistName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setGender(gender);
        return userProfile;
    }
}
