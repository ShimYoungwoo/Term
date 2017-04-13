package com.example.s0woo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapTapi;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapView;


import java.lang.String;
import android.util.Log;
import java.util.logging.*;

import java.util.ArrayList;


//import com.skplanetx.tmapopenmapapi.LogManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    public EditText editStart;
    public ImageButton btnStart;

    public EditText editFinish;
    public ImageButton btnFinish;

    public EditText editStop1;
    public ImageButton btnStop1;

    public static String mApiKey = "";
    private TMapView mMapView = null;

    private void configureMapView() {
        mMapView.setSKPMapApiKey(mApiKey);
        //song
//		mMapView.setSKPMapBizappId(mBizAppID);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editStart = (EditText)findViewById(R.id.startEdit);
        btnStart = (ImageButton)findViewById(R.id.startBtn);

        editFinish = (EditText)findViewById(R.id.finishEdit);
        btnFinish = (ImageButton)findViewById(R.id.finishBtn);

        editStop1 = (EditText)findViewById(R.id.stop1Edit);
        btnStop1 = (ImageButton)findViewById(R.id.stop1Btn);


        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStart.getText().toString();
                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "입력값이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //System.out.println("입력성공");
                    Toast.makeText(getApplicationContext(), "입력성공." + str, Toast.LENGTH_SHORT).show();

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                            for (int i = 0; i < poiItem.size(); i++) {
                                TMapPOIItem  item = poiItem.get(i);

                                //Log.d(TAG, "POI Name: " + item.getPOIName().toString() + ", " +
                                //        "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                                //        "Point: " + item.getPOIPoint().toString());
                            }
                        }
                    });


                }
            }
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editFinish.getText().toString();
                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "입력값이 필요합니다.", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnStop1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStop1.getText().toString();
                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "입력값이 필요합니다.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
