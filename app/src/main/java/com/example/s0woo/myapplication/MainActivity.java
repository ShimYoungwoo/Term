package com.example.s0woo.myapplication;

import android.renderscript.ScriptGroup;
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
import com.example.s0woo.myapplication.LogManager;

import android.view.inputmethod.InputMethodManager;




import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.skp.Tmap.BizCategory;
import com.skp.Tmap.TMapCircle;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.BizCategoryListenerCallback;
import com.skp.Tmap.TMapData.ConvertGPSToAddressListenerCallback;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapData.FindAroundNamePOIListenerCallback;
import com.skp.Tmap.TMapData.FindPathDataAllListenerCallback;
import com.skp.Tmap.TMapData.FindPathDataListenerCallback;
import com.skp.Tmap.TMapData.TMapPathType;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapGpsManager.onLocationChangedCallback;
import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapLabelInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapMarkerItem2;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapPolygon;
import com.skp.Tmap.TMapTapi;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.MapCaptureImageListenerCallback;
import com.skp.Tmap.TMapView.TMapLogoPositon;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TMap api 실행을 위한 api key
    private TMapView mMapView = null;
    public static String mApiKey = "73a7a315-a395-350e-9bff-14b10cd0f738";

    private void configureMapView() {
        mMapView.setSKPMapApiKey(mApiKey);
        //mMapView.setSKPMapBizappId(mBizAppID);
    }

    public EditText editStart;
    public ImageButton btnStart;

    public EditText editFinish;
    public ImageButton btnFinish;

    public EditText editStop1;
    public ImageButton btnStop1;

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


        mMapView = new TMapView(this);
        configureMapView();

        //출발지
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStart.getText().toString();

                final String name[] = new String[7];
                final String address[] = new String[7];
                final String point[] = new String[7];

                final String StartName;
                final String StartAddress;
                final String StartPoint;

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "출발지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            for (int i = 0; i < 7; i++) {
                                TMapPOIItem item = poiItem.get(i);

                                LogManager.printLog("POI Name: " + item.getPOIName().toString() + ", " +
                                        "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                                        "Point: " + item.getPOIPoint().toString());

                                name[i] = item.getPOIName().toString();
                                address[i] = item.getPOIAddress().replace("null", "");
                                point[i] = item.getPOIPoint().toString();

                                System.out.println(name[i] + " " +address[i] + " " + point[i]);
                            }
                        }
                    });
                }
            }
        });



        //도착지
        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editFinish.getText().toString();
                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "도착지를 입력해주세요.", Toast.LENGTH_LONG).show();
                }

            }
        });


        //경유지1
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
