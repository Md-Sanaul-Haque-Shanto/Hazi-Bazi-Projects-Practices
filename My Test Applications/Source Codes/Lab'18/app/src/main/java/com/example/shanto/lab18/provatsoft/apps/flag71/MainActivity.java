package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.provatsoft.apps.flag71.blls.AdManager;
import com.provatsoft.apps.flag71.blls.FileManager;
import com.provatsoft.apps.flag71.blls.ProfileManager;
import com.provatsoft.apps.flag71.blls.ProfileManager.OnFetchProfilePictureListener;
import com.provatsoft.apps.picatorlib.AnyFlagPainter;
import com.provatsoft.apps.picatorlib.BdFlagPainter;
import com.provatsoft.apps.picatorlib.FlagPainter;
import com.provatsoft.apps.picatorlib.ImageManager;
import com.provatsoft.apps.simplecorplib.CropImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnFetchProfilePictureListener {
    private static final String PERMISSION = "publish_actions";
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    public static final int SIZE = 512;
    private static final String TAG = "Main";
    private final String PENDING_ACTION_BUNDLE_KEY;
    private SeekBar alphaSeekBar;
    private Bitmap background;
    private CallbackManager callbackManager;
    private boolean canPresentShareDialog;
    private boolean canPresentShareDialogWithPhotos;
    private OnClickListener clickListener;
    private FlagPainter flagPainter;
    private TextView greeting;
    ImageManager imageManager;
    private ImageView imageView;
    private boolean mPicking;
    private MessengerThreadParams mThreadParams;
    private FloatingActionMenu menuAddPhoto;
    private FloatingActionMenu menuSharePhoto;
    private TextView messageTextView;
    private PendingAction pendingAction;
    private Photographer photographer;
    private Uri picUri;
    private Button postPhotoButton;
    private Button postStatusUpdateButton;
    private ProfilePictureView profilePictureView;
    private ProfileTracker profileTracker;
    private FacebookCallback<Result> shareCallback;
    private ShareDialog shareDialog;

    /* renamed from: com.provatsoft.apps.flag71.MainActivity.1 */
    class C04641 implements OnClickListener {
        C04641() {
        }

        public void onClick(View v) {
            FileManager fileManager = new FileManager();
            switch (v.getId()) {
                case C0467R.id.facebook_propic /*2131492993*/:
                    MainActivity.this.loadFacebookProfilePic();
                    break;
                case C0467R.id.photo_gallery /*2131492994*/:
                    MainActivity.this.photographer.openGallery();
                    break;
                case C0467R.id.photo_camera /*2131492995*/:
                    MainActivity.this.photographer.takePicture();
                    break;
                case C0467R.id.menu_fb_message /*2131492997*/:
                    MainActivity.this.onMessengerButtonClicked();
                    break;
                case C0467R.id.share_on_facebook /*2131492998*/:
                    MainActivity.this.onClickPostPhoto();
                    break;
                case C0467R.id.save_photo /*2131492999*/:
                    try {
                        Toast.makeText(MainActivity.this, "Saved: " + fileManager.saveImage(((BitmapDrawable) MainActivity.this.imageView.getDrawable()).getBitmap()), 0).show();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                default:
                    Toast.makeText(MainActivity.this, "Not implemented.", 0).show();
                    break;
            }
            MainActivity.this.menuAddPhoto.close(true);
            MainActivity.this.menuSharePhoto.close(true);
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.MainActivity.4 */
    static /* synthetic */ class C04654 {
        static final /* synthetic */ int[] $SwitchMap$com$provatsoft$apps$flag71$MainActivity$PendingAction;

        static {
            $SwitchMap$com$provatsoft$apps$flag71$MainActivity$PendingAction = new int[PendingAction.values().length];
            try {
                $SwitchMap$com$provatsoft$apps$flag71$MainActivity$PendingAction[PendingAction.NONE.ordinal()] = MainActivity.REQUEST_CODE_SHARE_TO_MESSENGER;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$provatsoft$apps$flag71$MainActivity$PendingAction[PendingAction.POST_PHOTO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private enum PendingAction {
        NONE,
        POST_PHOTO
    }

    /* renamed from: com.provatsoft.apps.flag71.MainActivity.2 */
    class C07602 implements FacebookCallback<Result> {
        C07602() {
        }

        public void onCancel() {
            MessageManager.show(MainActivity.this.messageTextView, "User canceled");
        }

        public void onError(FacebookException error) {
            showResult(MainActivity.this.getString(C0467R.string.error), error.getMessage());
        }

        public void onSuccess(Result result) {
            if (result.getPostId() != null) {
                String title = MainActivity.this.getString(C0467R.string.success);
                String id = result.getPostId();
                MainActivity mainActivity = MainActivity.this;
                Object[] objArr = new Object[MainActivity.REQUEST_CODE_SHARE_TO_MESSENGER];
                objArr[0] = id;
                showResult(title, mainActivity.getString(C0467R.string.successfully_posted_post, objArr));
            }
        }

        private void showResult(String title, String alertMessage) {
            new Builder(MainActivity.this).setTitle(title).setMessage(alertMessage).setPositiveButton(C0467R.string.ok, null).show();
        }
    }

    /* renamed from: com.provatsoft.apps.flag71.MainActivity.3 */
    class C07613 implements FacebookCallback<LoginResult> {
        C07613() {
        }

        public void onSuccess(LoginResult loginResult) {
            MainActivity.this.handlePendingAction();
            MainActivity.this.updateUI();
        }

        public void onCancel() {
            if (MainActivity.this.pendingAction != PendingAction.NONE) {
                showAlert();
                MainActivity.this.pendingAction = PendingAction.NONE;
            }
            MainActivity.this.updateUI();
        }

        public void onError(FacebookException exception) {
            if (MainActivity.this.pendingAction != PendingAction.NONE && (exception instanceof FacebookAuthorizationException)) {
                showAlert();
                MainActivity.this.pendingAction = PendingAction.NONE;
            }
            MainActivity.this.updateUI();
        }

        private void showAlert() {
            new Builder(MainActivity.this).setTitle(C0467R.string.cancelled).setMessage(C0467R.string.permission_not_granted).setPositiveButton(C0467R.string.ok, null).show();
        }
    }

    public MainActivity() {
        this.imageManager = new ImageManager();
        this.PENDING_ACTION_BUNDLE_KEY = "com.provatsoft.apps.picatorapp:PendingAction";
        this.pendingAction = PendingAction.NONE;
        this.clickListener = new C04641();
        this.shareCallback = new C07602();
    }

    public void didError(String message) {
        MessageManager.show(this.messageTextView, message);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initForFacebookPost(savedInstanceState);
        setContentView((int) C0467R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(C0467R.id.toolbar));
        this.photographer = new Photographer(this);
        this.canPresentShareDialogWithPhotos = ShareDialog.canShow(SharePhotoContent.class);
        this.messageTextView = (TextView) findViewById(C0467R.id.messageTextView);
        this.messageTextView.setVisibility(8);
        this.menuAddPhoto = (FloatingActionMenu) findViewById(C0467R.id.menu_get_photo);
        this.menuAddPhoto.setClosedOnTouchOutside(true);
        ((FloatingActionButton) findViewById(C0467R.id.facebook_propic)).setOnClickListener(this.clickListener);
        ((FloatingActionButton) findViewById(C0467R.id.photo_camera)).setOnClickListener(this.clickListener);
        ((FloatingActionButton) findViewById(C0467R.id.photo_gallery)).setOnClickListener(this.clickListener);
        ((FloatingActionButton) findViewById(C0467R.id.menu_fb_message)).setOnClickListener(this.clickListener);
        Intent intent = getIntent();
        if ("android.intent.action.PICK".equals(intent.getAction())) {
            this.mThreadParams = MessengerUtils.getMessengerThreadParamsForIntent(intent);
            this.mPicking = true;
        }
        this.menuSharePhoto = (FloatingActionMenu) findViewById(C0467R.id.menu_share_photo);
        this.menuSharePhoto.setClosedOnTouchOutside(true);
        ((FloatingActionButton) findViewById(C0467R.id.share_on_facebook)).setOnClickListener(this.clickListener);
        ((FloatingActionButton) findViewById(C0467R.id.save_photo)).setOnClickListener(this.clickListener);
        this.imageView = (ImageView) findViewById(C0467R.id.imageView);
        this.alphaSeekBar = (SeekBar) findViewById(C0467R.id.alphaSeekBar);
        Bitmap blankPropic = BitmapFactory.decodeResource(getResources(), C0467R.drawable.icon_user_default);
        this.background = blankPropic;
        mergeWithBdFlag(blankPropic);
        new AdManager(this).enableAd((AdView) findViewById(C0467R.id.adView));
    }

    private void loadFacebookProfilePic() {
        if (AccessToken.getCurrentAccessToken() == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), 20);
        } else {
            new ProfileManager(this).fetchProfilePicture(this, AccessToken.getCurrentAccessToken().getUserId());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0467R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0467R.id.flag /*2131493046*/:
                startActivityForResult(new Intent(this, FlagListActivity.class), 10);
                return true;
            case C0467R.id.settings /*2131493047*/:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case C0467R.id.rate_this /*2131493048*/:
                rate(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void rate(View view) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=com.provatsoft.apps.flag71"));
        startActivity(intent);
    }

    private void onMessengerButtonClicked() {
        ShareToMessengerParams shareToMessengerParams = ShareToMessengerParams.newBuilder(Uri.fromFile(saveToExternalStorage(((BitmapDrawable) this.imageView.getDrawable()).getBitmap())), "image/*").setMetaData("{ \"image\" : \"photo\" }").build();
        if (this.mPicking) {
            MessengerUtils.finishShareToMessenger(this, shareToMessengerParams);
        } else {
            MessengerUtils.shareToMessenger(this, REQUEST_CODE_SHARE_TO_MESSENGER, shareToMessengerParams);
        }
    }

    @NonNull
    private File saveToExternalStorage(Bitmap bm) {
        FileOutputStream fileOutputStream;
        FileNotFoundException e;
        IOException e2;
        File file = new File(Environment.getExternalStorageDirectory().toString(), "temp.png");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            try {
                bm.compress(CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
                fileOutputStream = outStream;
            } catch (FileNotFoundException e3) {
                e = e3;
                fileOutputStream = outStream;
                e.printStackTrace();
                return file;
            } catch (IOException e4) {
                e2 = e4;
                fileOutputStream = outStream;
                e2.printStackTrace();
                return file;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            e.printStackTrace();
            return file;
        } catch (IOException e6) {
            e2 = e6;
            e2.printStackTrace();
            return file;
        }
        return file;
    }

    public void didFetchPropic(Bitmap propic) {
        mergeWithBdFlag(propic);
        this.background = propic;
    }

    private void mergeWithBdFlag(Bitmap propic) {
        propic = this.imageManager.resize(propic, SIZE, SIZE);
        this.flagPainter = new BdFlagPainter(SIZE, SIZE);
        this.flagPainter.setAlphaInPc(70);
        this.flagPainter.setBackground(propic);
        this.flagPainter.listen(this.imageView, this.alphaSeekBar);
    }

    public void postPropic(View view) {
        ProfileManager propicManager = new ProfileManager(this);
        Bitmap bitmap = ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 2) {
            this.photographer.startCropImage();
        } else if (requestCode == REQUEST_CODE_SHARE_TO_MESSENGER) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                FileOutputStream fileOutputStream = new FileOutputStream(this.photographer.tempFile);
                copyStream(inputStream, fileOutputStream);
                fileOutputStream.close();
                inputStream.close();
                this.photographer.startCropImage();
            } catch (Exception e) {
                Log.e(TAG, "Error while creating temp file", e);
            }
        } else if (requestCode == 3) {
            if (data.getStringExtra(CropImage.IMAGE_PATH) != null) {
                this.background = BitmapFactory.decodeFile(this.photographer.tempFile.getPath());
                this.background = this.imageManager.resize(this.background, SIZE, SIZE);
                this.flagPainter = new BdFlagPainter(this.background.getWidth(), this.background.getHeight());
                this.flagPainter.setBackground(this.background);
                this.flagPainter.setAlphaInPc(70);
                this.flagPainter.listen(this.imageView, this.alphaSeekBar);
            }
        } else if (requestCode == 10) {
            int imgId = data.getIntExtra(AppConfigs.kDRAWABLE_IMAGE_ID, 0);
            if (imgId == C0467R.drawable.bdflag1_512_moveable_circle) {
                this.flagPainter = new BdFlagPainter(SIZE, SIZE);
                this.flagPainter.setAlphaInPc(80);
                this.flagPainter.setBackground(this.background);
                this.flagPainter.listen(this.imageView, this.alphaSeekBar);
                return;
            }
            Bitmap foreground = BitmapFactory.decodeResource(getResources(), imgId);
            this.flagPainter = new AnyFlagPainter(SIZE, SIZE);
            this.flagPainter.setAlphaInPc(80);
            this.flagPainter.setBackground(this.background);
            this.flagPainter.setForeground(this.imageManager.resize(foreground, SIZE, SIZE));
            this.flagPainter.listen(this.imageView, this.alphaSeekBar);
        } else if (requestCode == 20) {
            loadFacebookProfilePic();
        }
    }

    private void initForFacebookPost(Bundle savedInstanceState) {
        this.callbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new C07613());
        this.shareDialog = new ShareDialog((Activity) this);
        this.shareDialog.registerCallback(this.callbackManager, this.shareCallback);
        if (savedInstanceState != null) {
            this.pendingAction = PendingAction.valueOf(savedInstanceState.getString("com.provatsoft.apps.picatorapp:PendingAction"));
        }
    }

    private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO, this.canPresentShareDialogWithPhotos);
    }

    private void performPublish(PendingAction action, boolean allowNoToken) {
        if (AccessToken.getCurrentAccessToken() != null || allowNoToken) {
            this.pendingAction = action;
            handlePendingAction();
        }
    }

    private void handlePendingAction() {
        PendingAction previouslyPendingAction = this.pendingAction;
        this.pendingAction = PendingAction.NONE;
        switch (C04654.$SwitchMap$com$provatsoft$apps$flag71$MainActivity$PendingAction[previouslyPendingAction.ordinal()]) {
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED /*2*/:
                postPhoto();
            default:
        }
    }

    private void postPhoto() {
        SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(readImage()).build();
        ArrayList<SharePhoto> photos = new ArrayList();
        photos.add(sharePhoto);
        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder().setPhotos(photos).build();
        if (this.canPresentShareDialogWithPhotos) {
            this.shareDialog.show(sharePhotoContent);
        } else if (hasPublishPermission()) {
            ShareApi.share(sharePhotoContent, this.shareCallback);
        } else {
            this.pendingAction = PendingAction.POST_PHOTO;
            LoginManager instance = LoginManager.getInstance();
            String[] strArr = new String[REQUEST_CODE_SHARE_TO_MESSENGER];
            strArr[0] = PERMISSION;
            instance.logInWithPublishPermissions((Activity) this, Arrays.asList(strArr));
        }
    }

    private Bitmap readImage() {
        return ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
    }

    private boolean hasPublishPermission() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains(PERMISSION);
    }

    private void updateUI() {
        boolean enableButtons;
        boolean z = false;
        if (AccessToken.getCurrentAccessToken() != null) {
            enableButtons = true;
        } else {
            enableButtons = false;
        }
        Button button = this.postPhotoButton;
        if (enableButtons || this.canPresentShareDialogWithPhotos) {
            z = true;
        }
        button.setEnabled(z);
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int bytesRead = input.read(buffer);
            if (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
            } else {
                return;
            }
        }
    }
}
