package tw.com.arnold_lin.gradledemo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //qweojqsfhliwue
    }

    public  void goToDepend(View v){
//        Intent intent = new Intent(this, DependenceActivity.class);
//        startActivity(intent);
    }

    public  void goToQRcode(View v){
        Intent intent = new Intent(this, QRcodeShower.class);
        startActivity(intent);
    }

}
