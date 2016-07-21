package com.example.linxj.godemapdemo;

/**
 * Created by linxj on 16/7/20.
 */

public class AppLocation {


        public double latitude;


        public double longitude;


        public String city;


        public String address;

        @Override
        public String toString() {
            return "AppLocation{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", city='" + city + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }

}
