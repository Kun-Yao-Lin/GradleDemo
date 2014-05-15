package tw.com.arnold_lin.gradledemo.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.zxing.EncodeHintType;

import java.util.HashMap;


/**
 * Created by arnold_lin on 2014/4/27.
 */
public class QRcodeShower extends Activity {

    public static final String TAG = "QRcodeShower";
    private DisplayMetrics metrics;
    private FrameLayout QR_code_layout;
    private ImageView QR_code;
    private ProgressBar progressBar;
    private Handler handler;


    private void setupView() {


        metrics = this.getResources().getDisplayMetrics();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                    }
//                });
                return true;
            }
        });


        progressBar = (ProgressBar) this.findViewById(R.id.progress);

        QR_code_layout = (FrameLayout) this.findViewById(R.id.qr_code_layout);
        QR_code_layout.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels, metrics.widthPixels));

        QR_code = (ImageView) this.findViewById(R.id.qr_code);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createQRcode("https://www.google.com.tw");
                } catch (Exception e) {
                    Log.e(TAG, "deviceId EmptyResponseException", e);
                }
            }
        }).start();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qrcode_layout);
        setupView();

    }



    private void createQRcode(final String sceret) throws Exception {
        Log.d(TAG, sceret);
        String content = sceret;
        Log.d(TAG, content);

        try {
            final Bitmap bitmap = encodeAsBitmap(content, com.google.zxing.BarcodeFormat.QR_CODE, metrics.widthPixels, metrics.widthPixels);
            if (null != bitmap) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        QR_code.setImageBitmap(bitmap);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        } catch (Exception e) {
            throw e;
        } finally {

        }


        //

    }

    private Bitmap encodeAsBitmap(String contents, com.google.zxing.BarcodeFormat format,
                                  int desiredWidth, int desiredHeight) throws com.google.zxing.WriterException {
//        final int WHITE = 0xFFFFFFFF; //可以指定其他颜色，让二维码变成彩色效果
        final int WHITE = this.getResources().getColor(android.R.color.transparent);
        final int BLACK = 0xFF000000;

        HashMap<EncodeHintType, String> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new HashMap<com.google.zxing.EncodeHintType, String>(2);
            hints.put(com.google.zxing.EncodeHintType.CHARACTER_SET, encoding);
        }
        com.google.zxing.MultiFormatWriter writer = new com.google.zxing.MultiFormatWriter();
        com.google.zxing.common.BitMatrix result = writer.encode(contents, format, desiredWidth,
                desiredHeight, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}
