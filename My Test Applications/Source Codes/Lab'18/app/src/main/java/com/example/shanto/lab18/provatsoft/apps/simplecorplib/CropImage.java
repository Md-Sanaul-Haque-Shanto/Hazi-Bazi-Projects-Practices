package com.example.shanto.lab18.provatsoft.apps.simplecorplib;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.provatsoft.apps.simplecorplib.BitmapManager.ThreadSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class CropImage extends MonitoredActivity {
    public static final String ACTION_INLINE_DATA = "inline-data";
    public static final String ASPECT_X = "aspectX";
    public static final String ASPECT_Y = "aspectY";
    public static final int CANNOT_STAT_ERROR = -2;
    public static final String CIRCLE_CROP = "circleCrop";
    public static final String IMAGE_PATH = "image-path";
    public static final int NO_STORAGE_ERROR = -1;
    public static final String ORIENTATION_IN_DEGREES = "orientation_in_degrees";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    public static final String RETURN_DATA = "return-data";
    public static final String RETURN_DATA_AS_BITMAP = "data";
    public static final String SCALE = "scale";
    public static final String SCALE_UP_IF_NEEDED = "scaleUpIfNeeded";
    private static final String TAG = "CropImage";
    final int IMAGE_MAX_SIZE;
    private int mAspectX;
    private int mAspectY;
    private Bitmap mBitmap;
    private boolean mCircleCrop;
    private ContentResolver mContentResolver;
    HighlightView mCrop;
    private final ThreadSet mDecodingThreads;
    private boolean mDoFaceDetection;
    private final Handler mHandler;
    private String mImagePath;
    private CropImageView mImageView;
    private CompressFormat mOutputFormat;
    private int mOutputX;
    private int mOutputY;
    Runnable mRunFaceDetection;
    private Uri mSaveUri;
    boolean mSaving;
    private boolean mScale;
    private boolean mScaleUp;
    boolean mWaitingToPick;

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.1 */
    class C04711 implements OnClickListener {
        C04711() {
        }

        public void onClick(View v) {
            CropImage.this.setResult(0);
            CropImage.this.finish();
        }
    }

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.2 */
    class C04722 implements OnClickListener {
        C04722() {
        }

        public void onClick(View v) {
            try {
                CropImage.this.onSaveClicked();
            } catch (Exception e) {
                CropImage.this.finish();
            }
        }
    }

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.3 */
    class C04733 implements OnClickListener {
        C04733() {
        }

        public void onClick(View v) {
            CropImage.this.mBitmap = Util.rotateImage(CropImage.this.mBitmap, -90.0f);
            CropImage.this.mImageView.setImageRotateBitmapResetBase(new RotateBitmap(CropImage.this.mBitmap), true);
            CropImage.this.mRunFaceDetection.run();
        }
    }

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.4 */
    class C04744 implements OnClickListener {
        C04744() {
        }

        public void onClick(View v) {
            CropImage.this.mBitmap = Util.rotateImage(CropImage.this.mBitmap, 90.0f);
            CropImage.this.mImageView.setImageRotateBitmapResetBase(new RotateBitmap(CropImage.this.mBitmap), true);
            CropImage.this.mRunFaceDetection.run();
        }
    }

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.5 */
    class C04765 implements Runnable {

        /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.5.1 */
        class C04751 implements Runnable {
            final /* synthetic */ Bitmap val$b;
            final /* synthetic */ CountDownLatch val$latch;

            C04751(Bitmap bitmap, CountDownLatch countDownLatch) {
                this.val$b = bitmap;
                this.val$latch = countDownLatch;
            }

            public void run() {
                if (!(this.val$b == CropImage.this.mBitmap || this.val$b == null)) {
                    CropImage.this.mImageView.setImageBitmapResetBase(this.val$b, true);
                    CropImage.this.mBitmap.recycle();
                    CropImage.this.mBitmap = this.val$b;
                }
                if (CropImage.this.mImageView.getScale() == 1.0f) {
                    CropImage.this.mImageView.center(true, true);
                }
                this.val$latch.countDown();
            }
        }

        C04765() {
        }

        public void run() {
            CountDownLatch latch = new CountDownLatch(1);
            CropImage.this.mHandler.post(new C04751(CropImage.this.mBitmap, latch));
            try {
                latch.await();
                CropImage.this.mRunFaceDetection.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.6 */
    class C04776 implements Runnable {
        final /* synthetic */ Bitmap val$b;

        C04776(Bitmap bitmap) {
            this.val$b = bitmap;
        }

        public void run() {
            CropImage.this.saveOutput(this.val$b);
        }
    }

    /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.7 */
    class C04797 implements Runnable {
        Face[] mFaces;
        Matrix mImageMatrix;
        int mNumFaces;
        float mScale;

        /* renamed from: com.provatsoft.apps.simplecorplib.CropImage.7.1 */
        class C04781 implements Runnable {
            C04781() {
            }

            public void run() {
                boolean z;
                CropImage cropImage = CropImage.this;
                if (C04797.this.mNumFaces > 1) {
                    z = true;
                } else {
                    z = false;
                }
                cropImage.mWaitingToPick = z;
                if (C04797.this.mNumFaces > 0) {
                    for (int i = 0; i < C04797.this.mNumFaces; i++) {
                        C04797.this.handleFace(C04797.this.mFaces[i]);
                    }
                } else {
                    C04797.this.makeDefault();
                }
                CropImage.this.mImageView.invalidate();
                if (CropImage.this.mImageView.mHighlightViews.size() == 1) {
                    CropImage.this.mCrop = (HighlightView) CropImage.this.mImageView.mHighlightViews.get(0);
                    CropImage.this.mCrop.setFocus(true);
                }
                if (C04797.this.mNumFaces > 1) {
                    Toast.makeText(CropImage.this, "Multi face crop help", 0).show();
                }
            }
        }

        C04797() {
            this.mScale = 1.0f;
            this.mFaces = new Face[3];
        }

        private void handleFace(Face f) {
            PointF midPoint = new PointF();
            int r = ((int) (f.eyesDistance() * this.mScale)) * 2;
            f.getMidPoint(midPoint);
            midPoint.x *= this.mScale;
            midPoint.y *= this.mScale;
            int midX = (int) midPoint.x;
            int midY = (int) midPoint.y;
            HighlightView hv = new HighlightView(CropImage.this.mImageView);
            Rect imageRect = new Rect(0, 0, CropImage.this.mBitmap.getWidth(), CropImage.this.mBitmap.getHeight());
            RectF faceRect = new RectF((float) midX, (float) midY, (float) midX, (float) midY);
            faceRect.inset((float) (-r), (float) (-r));
            if (faceRect.left < 0.0f) {
                faceRect.inset(-faceRect.left, -faceRect.left);
            }
            if (faceRect.top < 0.0f) {
                faceRect.inset(-faceRect.top, -faceRect.top);
            }
            if (faceRect.right > ((float) imageRect.right)) {
                faceRect.inset(faceRect.right - ((float) imageRect.right), faceRect.right - ((float) imageRect.right));
            }
            if (faceRect.bottom > ((float) imageRect.bottom)) {
                faceRect.inset(faceRect.bottom - ((float) imageRect.bottom), faceRect.bottom - ((float) imageRect.bottom));
            }
            Matrix matrix = this.mImageMatrix;
            boolean access$500 = CropImage.this.mCircleCrop;
            boolean z = (CropImage.this.mAspectX == 0 || CropImage.this.mAspectY == 0) ? false : true;
            hv.setup(matrix, imageRect, faceRect, access$500, z);
            CropImage.this.mImageView.add(hv);
        }

        private void makeDefault() {
            boolean z = false;
            HighlightView hv = new HighlightView(CropImage.this.mImageView);
            int width = CropImage.this.mBitmap.getWidth();
            int height = CropImage.this.mBitmap.getHeight();
            Rect imageRect = new Rect(0, 0, width, height);
            int cropWidth = (Math.min(width, height) * 4) / 5;
            int cropHeight = cropWidth;
            if (!(CropImage.this.mAspectX == 0 || CropImage.this.mAspectY == 0)) {
                if (CropImage.this.mAspectX > CropImage.this.mAspectY) {
                    cropHeight = (CropImage.this.mAspectY * cropWidth) / CropImage.this.mAspectX;
                } else {
                    cropWidth = (CropImage.this.mAspectX * cropHeight) / CropImage.this.mAspectY;
                }
            }
            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;
            RectF cropRect = new RectF((float) x, (float) y, (float) (x + cropWidth), (float) (y + cropHeight));
            Matrix matrix = this.mImageMatrix;
            boolean access$500 = CropImage.this.mCircleCrop;
            if (!(CropImage.this.mAspectX == 0 || CropImage.this.mAspectY == 0)) {
                z = true;
            }
            hv.setup(matrix, imageRect, cropRect, access$500, z);
            CropImage.this.mImageView.mHighlightViews.clear();
            CropImage.this.mImageView.add(hv);
        }

        private Bitmap prepareBitmap() {
            if (CropImage.this.mBitmap == null) {
                return null;
            }
            if (CropImage.this.mBitmap.getWidth() > AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY) {
                this.mScale = 256.0f / ((float) CropImage.this.mBitmap.getWidth());
            }
            Matrix matrix = new Matrix();
            matrix.setScale(this.mScale, this.mScale);
            return Bitmap.createBitmap(CropImage.this.mBitmap, 0, 0, CropImage.this.mBitmap.getWidth(), CropImage.this.mBitmap.getHeight(), matrix, true);
        }

        public void run() {
            this.mImageMatrix = CropImage.this.mImageView.getImageMatrix();
            Bitmap faceBitmap = prepareBitmap();
            this.mScale = 1.0f / this.mScale;
            if (faceBitmap != null && CropImage.this.mDoFaceDetection) {
                this.mNumFaces = new FaceDetector(faceBitmap.getWidth(), faceBitmap.getHeight(), this.mFaces.length).findFaces(faceBitmap, this.mFaces);
            }
            if (!(faceBitmap == null || faceBitmap == CropImage.this.mBitmap)) {
                faceBitmap.recycle();
            }
            CropImage.this.mHandler.post(new C04781());
        }
    }

    public CropImage() {
        this.IMAGE_MAX_SIZE = AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT;
        this.mOutputFormat = CompressFormat.JPEG;
        this.mSaveUri = null;
        this.mDoFaceDetection = true;
        this.mCircleCrop = false;
        this.mHandler = new Handler();
        this.mScaleUp = true;
        this.mDecodingThreads = new ThreadSet();
        this.mRunFaceDetection = new C04797();
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mContentResolver = getContentResolver();
        requestWindowFeature(1);
        setContentView(C0482R.layout.cropimage);
        this.mImageView = (CropImageView) findViewById(C0482R.id.image);
        showStorageToast(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString(CIRCLE_CROP) != null) {
                if (VERSION.SDK_INT > 11) {
                    this.mImageView.setLayerType(1, null);
                }
                this.mCircleCrop = true;
                this.mAspectX = 1;
                this.mAspectY = 1;
            }
            this.mImagePath = extras.getString(IMAGE_PATH);
            this.mSaveUri = getImageUri(this.mImagePath);
            this.mBitmap = getBitmap(this.mImagePath);
            if (extras.containsKey(ASPECT_X) && (extras.get(ASPECT_X) instanceof Integer)) {
                this.mAspectX = extras.getInt(ASPECT_X);
                if (extras.containsKey(ASPECT_Y) && (extras.get(ASPECT_Y) instanceof Integer)) {
                    this.mAspectY = extras.getInt(ASPECT_Y);
                    this.mOutputX = extras.getInt(OUTPUT_X);
                    this.mOutputY = extras.getInt(OUTPUT_Y);
                    this.mScale = extras.getBoolean(SCALE, true);
                    this.mScaleUp = extras.getBoolean(SCALE_UP_IF_NEEDED, true);
                } else {
                    throw new IllegalArgumentException("aspect_y must be integer");
                }
            }
            throw new IllegalArgumentException("aspect_x must be integer");
        }
        if (this.mBitmap == null) {
            Log.d(TAG, "finish!!!");
            finish();
            return;
        }
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        findViewById(C0482R.id.discard).setOnClickListener(new C04711());
        findViewById(C0482R.id.save).setOnClickListener(new C04722());
        findViewById(C0482R.id.rotateLeft).setOnClickListener(new C04733());
        findViewById(C0482R.id.rotateRight).setOnClickListener(new C04744());
        startFaceDetection();
    }

    private Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private Bitmap getBitmap(String path) {
        Uri uri = getImageUri(path);
        try {
            InputStream in = this.mContentResolver.openInputStream(uri);
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;
            if (o.outHeight > AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT || o.outWidth > AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT) {
                scale = (int) Math.pow(2.0d, (double) ((int) Math.round(Math.log(1024.0d / ((double) Math.max(o.outHeight, o.outWidth))) / Math.log(0.5d))));
            }
            Options o2 = new Options();
            o2.inSampleSize = scale;
            in = this.mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();
            return b;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file " + path + " not found");
            return null;
        } catch (IOException e2) {
            Log.e(TAG, "file " + path + " not found");
            return null;
        }
    }

    private void startFaceDetection() {
        if (!isFinishing()) {
            this.mImageView.setImageBitmapResetBase(this.mBitmap, true);
            Util.startBackgroundJob(this, null, "Please wait\u2026", new C04765(), this.mHandler);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onSaveClicked() throws Exception {
        /*
        r25 = this;
        r0 = r25;
        r0 = r0.mSaving;
        r21 = r0;
        if (r21 == 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r25;
        r0 = r0.mCrop;
        r21 = r0;
        if (r21 == 0) goto L_0x0008;
    L_0x0011:
        r21 = 1;
        r0 = r21;
        r1 = r25;
        r1.mSaving = r0;
        r0 = r25;
        r0 = r0.mCrop;
        r21 = r0;
        r18 = r21.getCropRect();
        r20 = r18.width();
        r14 = r18.height();
        r0 = r25;
        r0 = r0.mCircleCrop;	 Catch:{ Exception -> 0x0145 }
        r21 = r0;
        if (r21 == 0) goto L_0x0141;
    L_0x0033:
        r21 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Exception -> 0x0145 }
    L_0x0035:
        r0 = r20;
        r1 = r21;
        r8 = android.graphics.Bitmap.createBitmap(r0, r14, r1);	 Catch:{ Exception -> 0x0145 }
        if (r8 == 0) goto L_0x0008;
    L_0x003f:
        r7 = new android.graphics.Canvas;
        r7.<init>(r8);
        r9 = new android.graphics.Rect;
        r21 = 0;
        r22 = 0;
        r0 = r21;
        r1 = r22;
        r2 = r20;
        r9.<init>(r0, r1, r2, r14);
        r0 = r25;
        r0 = r0.mBitmap;
        r21 = r0;
        r22 = 0;
        r0 = r21;
        r1 = r18;
        r2 = r22;
        r7.drawBitmap(r0, r1, r9, r2);
        r0 = r25;
        r0 = r0.mCircleCrop;
        r21 = r0;
        if (r21 == 0) goto L_0x00b2;
    L_0x006c:
        r6 = new android.graphics.Canvas;
        r6.<init>(r8);
        r17 = new android.graphics.Path;
        r17.<init>();
        r0 = r20;
        r0 = (float) r0;
        r21 = r0;
        r22 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r21 = r21 / r22;
        r0 = (float) r14;
        r22 = r0;
        r23 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r22 = r22 / r23;
        r0 = r20;
        r0 = (float) r0;
        r23 = r0;
        r24 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r23 = r23 / r24;
        r24 = android.graphics.Path.Direction.CW;
        r0 = r17;
        r1 = r21;
        r2 = r22;
        r3 = r23;
        r4 = r24;
        r0.addCircle(r1, r2, r3, r4);
        r21 = android.graphics.Region.Op.DIFFERENCE;
        r0 = r17;
        r1 = r21;
        r6.clipPath(r0, r1);
        r21 = 0;
        r22 = android.graphics.PorterDuff.Mode.CLEAR;
        r0 = r21;
        r1 = r22;
        r6.drawColor(r0, r1);
    L_0x00b2:
        r0 = r25;
        r0 = r0.mOutputX;
        r21 = r0;
        if (r21 == 0) goto L_0x00f6;
    L_0x00ba:
        r0 = r25;
        r0 = r0.mOutputY;
        r21 = r0;
        if (r21 == 0) goto L_0x00f6;
    L_0x00c2:
        r0 = r25;
        r0 = r0.mScale;
        r21 = r0;
        if (r21 == 0) goto L_0x0147;
    L_0x00ca:
        r16 = r8;
        r21 = new android.graphics.Matrix;
        r21.<init>();
        r0 = r25;
        r0 = r0.mOutputX;
        r22 = r0;
        r0 = r25;
        r0 = r0.mOutputY;
        r23 = r0;
        r0 = r25;
        r0 = r0.mScaleUp;
        r24 = r0;
        r0 = r21;
        r1 = r22;
        r2 = r23;
        r3 = r24;
        r8 = com.provatsoft.apps.simplecorplib.Util.transform(r0, r8, r1, r2, r3);
        r0 = r16;
        if (r0 == r8) goto L_0x00f6;
    L_0x00f3:
        r16.recycle();
    L_0x00f6:
        r21 = r25.getIntent();
        r15 = r21.getExtras();
        if (r15 == 0) goto L_0x01e6;
    L_0x0100:
        r21 = "data";
        r0 = r21;
        r21 = r15.getParcelable(r0);
        if (r21 != 0) goto L_0x0114;
    L_0x010a:
        r21 = "return-data";
        r0 = r21;
        r21 = r15.getBoolean(r0);
        if (r21 == 0) goto L_0x01e6;
    L_0x0114:
        r13 = new android.os.Bundle;
        r13.<init>();
        r21 = "data";
        r0 = r21;
        r13.putParcelable(r0, r8);
        r21 = -1;
        r22 = new android.content.Intent;
        r22.<init>();
        r23 = "inline-data";
        r22 = r22.setAction(r23);
        r0 = r22;
        r22 = r0.putExtras(r13);
        r0 = r25;
        r1 = r21;
        r2 = r22;
        r0.setResult(r1, r2);
        r25.finish();
        goto L_0x0008;
    L_0x0141:
        r21 = android.graphics.Bitmap.Config.RGB_565;	 Catch:{ Exception -> 0x0145 }
        goto L_0x0035;
    L_0x0145:
        r12 = move-exception;
        throw r12;
    L_0x0147:
        r0 = r25;
        r0 = r0.mOutputX;
        r21 = r0;
        r0 = r25;
        r0 = r0.mOutputY;
        r22 = r0;
        r23 = android.graphics.Bitmap.Config.RGB_565;
        r5 = android.graphics.Bitmap.createBitmap(r21, r22, r23);
        r7 = new android.graphics.Canvas;
        r7.<init>(r5);
        r0 = r25;
        r0 = r0.mCrop;
        r21 = r0;
        r19 = r21.getCropRect();
        r9 = new android.graphics.Rect;
        r21 = 0;
        r22 = 0;
        r0 = r25;
        r0 = r0.mOutputX;
        r23 = r0;
        r0 = r25;
        r0 = r0.mOutputY;
        r24 = r0;
        r0 = r21;
        r1 = r22;
        r2 = r23;
        r3 = r24;
        r9.<init>(r0, r1, r2, r3);
        r21 = r19.width();
        r22 = r9.width();
        r21 = r21 - r22;
        r10 = r21 / 2;
        r21 = r19.height();
        r22 = r9.height();
        r21 = r21 - r22;
        r11 = r21 / 2;
        r21 = 0;
        r0 = r21;
        r21 = java.lang.Math.max(r0, r10);
        r22 = 0;
        r0 = r22;
        r22 = java.lang.Math.max(r0, r11);
        r0 = r19;
        r1 = r21;
        r2 = r22;
        r0.inset(r1, r2);
        r21 = 0;
        r0 = -r10;
        r22 = r0;
        r21 = java.lang.Math.max(r21, r22);
        r22 = 0;
        r0 = -r11;
        r23 = r0;
        r22 = java.lang.Math.max(r22, r23);
        r0 = r21;
        r1 = r22;
        r9.inset(r0, r1);
        r0 = r25;
        r0 = r0.mBitmap;
        r21 = r0;
        r22 = 0;
        r0 = r21;
        r1 = r19;
        r2 = r22;
        r7.drawBitmap(r0, r1, r9, r2);
        r8.recycle();
        r8 = r5;
        goto L_0x00f6;
    L_0x01e6:
        r5 = r8;
        r21 = 0;
        r22 = com.provatsoft.apps.simplecorplib.C0482R.string.saving_image;
        r0 = r25;
        r1 = r22;
        r22 = r0.getString(r1);
        r23 = new com.provatsoft.apps.simplecorplib.CropImage$6;
        r0 = r23;
        r1 = r25;
        r0.<init>(r5);
        r0 = r25;
        r0 = r0.mHandler;
        r24 = r0;
        r0 = r25;
        r1 = r21;
        r2 = r22;
        r3 = r23;
        r4 = r24;
        com.provatsoft.apps.simplecorplib.Util.startBackgroundJob(r0, r1, r2, r3, r4);
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.provatsoft.apps.simplecorplib.CropImage.onSaveClicked():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveOutput(Bitmap r8) {
        /*
        r7 = this;
        r4 = r7.mSaveUri;
        if (r4 == 0) goto L_0x0072;
    L_0x0004:
        r3 = 0;
        r4 = r7.mContentResolver;	 Catch:{ IOException -> 0x0047 }
        r5 = r7.mSaveUri;	 Catch:{ IOException -> 0x0047 }
        r3 = r4.openOutputStream(r5);	 Catch:{ IOException -> 0x0047 }
        if (r3 == 0) goto L_0x0016;
    L_0x000f:
        r4 = r7.mOutputFormat;	 Catch:{ IOException -> 0x0047 }
        r5 = 90;
        r8.compress(r4, r5, r3);	 Catch:{ IOException -> 0x0047 }
    L_0x0016:
        com.provatsoft.apps.simplecorplib.Util.closeSilently(r3);
        r1 = new android.os.Bundle;
        r1.<init>();
        r2 = new android.content.Intent;
        r4 = r7.mSaveUri;
        r4 = r4.toString();
        r2.<init>(r4);
        r2.putExtras(r1);
        r4 = "image-path";
        r5 = r7.mImagePath;
        r2.putExtra(r4, r5);
        r4 = "orientation_in_degrees";
        r5 = com.provatsoft.apps.simplecorplib.Util.getOrientationInDegree(r7);
        r2.putExtra(r4, r5);
        r4 = -1;
        r7.setResult(r4, r2);
    L_0x0040:
        r8.recycle();
        r7.finish();
    L_0x0046:
        return;
    L_0x0047:
        r0 = move-exception;
        r4 = "CropImage";
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006d }
        r5.<init>();	 Catch:{ all -> 0x006d }
        r6 = "Cannot open file: ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x006d }
        r6 = r7.mSaveUri;	 Catch:{ all -> 0x006d }
        r5 = r5.append(r6);	 Catch:{ all -> 0x006d }
        r5 = r5.toString();	 Catch:{ all -> 0x006d }
        android.util.Log.e(r4, r5, r0);	 Catch:{ all -> 0x006d }
        r4 = 0;
        r7.setResult(r4);	 Catch:{ all -> 0x006d }
        r7.finish();	 Catch:{ all -> 0x006d }
        com.provatsoft.apps.simplecorplib.Util.closeSilently(r3);
        goto L_0x0046;
    L_0x006d:
        r4 = move-exception;
        com.provatsoft.apps.simplecorplib.Util.closeSilently(r3);
        throw r4;
    L_0x0072:
        r4 = "CropImage";
        r5 = "not defined image url";
        android.util.Log.e(r4, r5);
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.provatsoft.apps.simplecorplib.CropImage.saveOutput(android.graphics.Bitmap):void");
    }

    protected void onPause() {
        super.onPause();
        BitmapManager.instance().cancelThreadDecoding(this.mDecodingThreads);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mBitmap != null) {
            this.mBitmap.recycle();
        }
    }

    public static void showStorageToast(Activity activity) {
        showStorageToast(activity, calculatePicturesRemaining(activity));
    }

    public static void showStorageToast(Activity activity, int remaining) {
        String noStorageText = null;
        if (remaining == NO_STORAGE_ERROR) {
            if (Environment.getExternalStorageState().equals("checking")) {
                noStorageText = activity.getString(C0482R.string.preparing_card);
            } else {
                noStorageText = activity.getString(C0482R.string.no_storage_card);
            }
        } else if (remaining < 1) {
            noStorageText = activity.getString(C0482R.string.not_enough_space);
        }
        if (noStorageText != null) {
            Toast.makeText(activity, noStorageText, 5000).show();
        }
    }

    public static int calculatePicturesRemaining(Activity activity) {
        try {
            String storageDirectory = BuildConfig.FLAVOR;
            if ("mounted".equals(Environment.getExternalStorageState())) {
                storageDirectory = Environment.getExternalStorageDirectory().toString();
            } else {
                storageDirectory = activity.getFilesDir().toString();
            }
            StatFs stat = new StatFs(storageDirectory);
            return (int) ((((float) stat.getAvailableBlocks()) * ((float) stat.getBlockSize())) / 400000.0f);
        } catch (Exception e) {
            return CANNOT_STAT_ERROR;
        }
    }
}
