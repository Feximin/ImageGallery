package com.feximin.imagegallery;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Neo on 16/2/1.
 */
public class ActivityMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but).setOnClickListener(v -> {
            ArrayList<String> data = new ArrayList<>();
            data.add("http://p4.so.qhimg.com/t01472db906ce126ebe.jpg");
            data.add("http://p2.so.qhimg.com/t015c249f917fd0395c.jpg");
            data.add("http://p4.so.qhimg.com/t01c48c2b155f23f1de.jpg");
            data.add("http://p2.so.qhimg.com/t01967e6292f2689c37.jpg");
            data.add("http://p2.so.qhimg.com/t018e7e64110afdd0ec.jpg");
            data.add("http://p3.so.qhimg.com/t0176c15c7fd7cac5c8.jpg");
            data.add("http://p2.so.qhimg.com/t017ee16d1d499b6022.jpg");
            data.add("http://p0.so.qhimg.com/t016f304e739ee2cc15.jpg");
            data.add("http://p4.so.qhimg.com/t01b087f06be900ac79.jpg");
            ActivityImageShower.startActivity(this, data, 2);
        });
    }
}
