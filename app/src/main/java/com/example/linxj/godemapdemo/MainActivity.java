package com.example.linxj.godemapdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);//自定义的code
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        AmapUtil.init(this);
        AmapUtil.start(new AmapUtil.LocationCallback() {
            @Override
            public void onSuccess(double latitude, double longtitude, String city, String address) {
                AppLocation location = new AppLocation();
                location.latitude = latitude;
                location.longitude = longtitude;
                location.city = city;
                location.address = address;
                String locationStr = latitude +" "+ longtitude+" "+city+" ";
                //Toast.makeText(MainActivity.this,locationStr,Toast.LENGTH_SHORT).show();
                Log.e("locationInfo",locationStr);
            }

            @Override
            public void onFailed(int errorCode, String message) {
                //AppLog.i("AppLocationManager.onFailed");
                String error = "Get location fail, msg = " + message + ", code = " + errorCode;
                //subscriber.onError(new AppException(new RuntimeException(error)));
                //Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
                Log.e("locationInfo",error);
            }
        });
    }
}
