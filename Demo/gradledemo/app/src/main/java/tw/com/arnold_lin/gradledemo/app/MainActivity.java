package tw.com.arnold_lin.gradledemo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tw.com.arnold_lin.dependenceproject.app.DependenceActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void goToDepend(View v){
        Intent intent = new Intent(this, DependenceActivity.class);
        startActivity(intent);
    }

    public  void goToQRcode(View v){
        Intent intent = new Intent(this, QRcodeShower.class);
        startActivity(intent);
    }

}
