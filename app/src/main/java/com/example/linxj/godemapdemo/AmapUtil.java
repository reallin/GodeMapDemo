package com.example.linxj.godemapdemo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * AmapUtil <br/>
 * Created by xiaqiulei on 2016-04-11.
 */
public class AmapUtil {

    // 定位时间间隔
    public static final int LOCATION_INTERVAL = 5 * 60 * 1000;

    public interface LocationCallback {

        void onSuccess(double latitude, double longtitude, String city, String address);

        void onFailed(int errorCode, String message);
    }

    private static Context context;
    private static AMapLocationClient locationClient = null;
    private static LocationCallback locationCallback;

    public static void init(Context context) {
        AmapUtil.context = context;
    }

    public static void start(LocationCallback callback) {
        stop();

        locationCallback = callback;
        AMapLocationClientOption mLocationOption = newAMapLocationClientOption();
        //初始化定位
        locationClient = new AMapLocationClient(context);
        //设置定位回调监听
        locationClient.setLocationListener(newAMapLocationListener());
        // 给定位客户端对象设置定位参数
        locationClient.setLocationOption(mLocationOption);
        // 启动定位
        locationClient.startLocation();
    }

    // 初始化定位参数
    @NonNull
    static AMapLocationClientOption newAMapLocationClientOption() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 设置是否返回地址信息（默认返回地址信息）
        option.setNeedAddress(true);
        // 设置是否只定位一次,默认为false
        option.setOnceLocation(false);
        // 设置是否强制刷新WIFI，默认为强制刷新
        option.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        option.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为2000ms
        option.setInterval(LOCATION_INTERVAL);

        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        return option;
    }

    @NonNull
    static AMapLocationListener newAMapLocationListener() {
        return new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {

                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    if (locationCallback != null) {
                        double latitude = amapLocation.getLatitude();
                        double longitude = amapLocation.getLongitude();
                        String address = amapLocation.getAddress();
                        locationCallback.onSuccess(latitude, longitude, amapLocation.getCity(), address);
                    }
                } else {
                    if (locationCallback != null) {
                        String message = amapLocation != null ? amapLocation.getErrorInfo() : null;
                        int code = amapLocation != null ? amapLocation.getErrorCode() : 0;
                        locationCallback.onFailed(code, message);
                    }
                }

                // stop();
            }
        };
    }

    public static void stop() {
        if (locationClient != null) {
            locationClient.stopLocation(); // 停止定位
            locationClient.onDestroy();
            locationClient = null;
            locationCallback = null;
        }
    }
}