package com.example.s0woo.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
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

    public Button btnCar;

    final static int ACT_EDIT = 0;

    DBLocationList locationList;
    SQLiteDatabase db;

    final String StartName[] = new String[7];
    final String StartAddress[] = new String[7];
    final double StartLatitude[] = new double[7];
    final double StartLongitude[] = new double[7];

    final String FinishName[] = new String[7];
    final String FinishAddress[] = new String[7];
    final double FinishLatitude[] = new double[7];
    final double FinishLongitude[] = new double[7];

    final String Stop1Name[] = new String[7];
    final String Stop1Address[] = new String[7];
    final double Stop1Latitude[] = new double[7];
    final double Stop1Longitude[] = new double[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editStart = (EditText) findViewById(R.id.startEdit);
        btnStart = (ImageButton) findViewById(R.id.startBtn);

        editFinish = (EditText) findViewById(R.id.finishEdit);
        btnFinish = (ImageButton) findViewById(R.id.finishBtn);

        editStop1 = (EditText) findViewById(R.id.stop1Edit);
        btnStop1 = (ImageButton) findViewById(R.id.stop1Btn);

        btnCar = (Button) findViewById(R.id.Car);


        mMapView = new TMapView(this);
        configureMapView();


        //출발지
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStart.getText().toString();

                final Intent intent = new Intent(MainActivity.this, SubActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "출발지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            for (int i = 0; i < 7; i++) {
                                TMapPOIItem item = poiItem.get(i);

                                LogManager.printLog("POI Name: " + item.getPOIName().toString() + ", " +
                                        "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                        "Point: " + item.getPOIPoint().toString());

                                TMapPoint point = item.getPOIPoint();
                                double Latitude = point.getLatitude();
                                double Longitude = point.getLongitude();

                                StartName[i] = item.getPOIName().toString();
                                StartAddress[i] = item.getPOIAddress().replace("null", "");
                                StartLatitude[i] = Latitude;
                                StartLongitude[i] = Longitude;
                            }

                            intent.putExtra("Name0", StartName[0]);
                            intent.putExtra("Name1", StartName[1]);
                            intent.putExtra("Name2", StartName[2]);
                            intent.putExtra("Name3", StartName[3]);
                            intent.putExtra("Name4", StartName[4]);
                            intent.putExtra("Name5", StartName[5]);
                            intent.putExtra("Name6", StartName[6]);

                            intent.putExtra("Address0", StartAddress[0]);
                            intent.putExtra("Address1", StartAddress[1]);
                            intent.putExtra("Address2", StartAddress[2]);
                            intent.putExtra("Address3", StartAddress[3]);
                            intent.putExtra("Address4", StartAddress[4]);
                            intent.putExtra("Address5", StartAddress[5]);
                            intent.putExtra("Address6", StartAddress[6]);
                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editFinish.getText().toString();

                final Intent intent = new Intent(MainActivity.this, SubActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "도착지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            for (int i = 0; i < 7; i++) {
                                TMapPOIItem item = poiItem.get(i);

                                LogManager.printLog("POI Name: " + item.getPOIName().toString() + ", " +
                                        "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                        "Point: " + item.getPOIPoint().toString());

                                TMapPoint point = item.getPOIPoint();
                                double Latitude = point.getLatitude();
                                double Longitude = point.getLongitude();

                                FinishName[i] = item.getPOIName().toString();
                                FinishAddress[i] = item.getPOIAddress().replace("null", "");
                                FinishLatitude[i] = Latitude;
                                FinishLongitude[i] = Longitude;
                            }

                            intent.putExtra("Name0", FinishName[0]);
                            intent.putExtra("Name1", FinishName[1]);
                            intent.putExtra("Name2", FinishName[2]);
                            intent.putExtra("Name3", FinishName[3]);
                            intent.putExtra("Name4", FinishName[4]);
                            intent.putExtra("Name5", FinishName[5]);
                            intent.putExtra("Name6", FinishName[6]);

                            intent.putExtra("Address0", FinishAddress[0]);
                            intent.putExtra("Address1", FinishAddress[1]);
                            intent.putExtra("Address2", FinishAddress[2]);
                            intent.putExtra("Address3", FinishAddress[3]);
                            intent.putExtra("Address4", FinishAddress[4]);
                            intent.putExtra("Address5", FinishAddress[5]);
                            intent.putExtra("Address6", FinishAddress[6]);
                            startActivityForResult(intent, ACT_EDIT);

                        }
                    });
                }
            }
        });


        btnStop1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStop1.getText().toString();

                final Intent intent = new Intent(MainActivity.this, SubActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            for (int i = 0; i < 7; i++) {
                                TMapPOIItem item = poiItem.get(i);

                                LogManager.printLog("POI Name: " + item.getPOIName().toString() + ", " +
                                        "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                        "Point: " + item.getPOIPoint().toString());

                                TMapPoint point = item.getPOIPoint();
                                double Latituge = point.getLatitude();
                                double Longitude = point.getLongitude();

                                Stop1Name[i] = item.getPOIName().toString();
                                Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                Stop1Latitude[i] = Latituge;
                                Stop1Longitude[i] = Longitude;
                            }

                            intent.putExtra("Name0", Stop1Name[0]);
                            intent.putExtra("Name1", Stop1Name[1]);
                            intent.putExtra("Name2", Stop1Name[2]);
                            intent.putExtra("Name3", Stop1Name[3]);
                            intent.putExtra("Name4", Stop1Name[4]);
                            intent.putExtra("Name5", Stop1Name[5]);
                            intent.putExtra("Name6", Stop1Name[6]);

                            intent.putExtra("Address0", Stop1Address[0]);
                            intent.putExtra("Address1", Stop1Address[1]);
                            intent.putExtra("Address2", Stop1Address[2]);
                            intent.putExtra("Address3", Stop1Address[3]);
                            intent.putExtra("Address4", Stop1Address[4]);
                            intent.putExtra("Address5", Stop1Address[5]);
                            intent.putExtra("Address6", Stop1Address[6]);
                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });




        btnCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TMapData tmapdata = new TMapData();

                String Name[] = new String[7];
                int Index[] = new int[7];
                double Latitude[] = new double[7];
                double Longitude[] = new double[7];

                //locationList = new DBLocationList(MainActivity.this, "LocationList.db", null, 1);
                //db = locationList.getReadableDatabase();

                Cursor c = db.query("LocationList", null, null, null, null, null, null, null);

                while(c.moveToNext()) {
                    for(int i=0; i<7; i++) {
                        Name[i] = c.getString(c.getColumnIndex("name"));
                        Index[i] = c.getInt(c.getColumnIndex("idNumber"));
                        Latitude[i] = c.getDouble(c.getColumnIndex("latitude"));
                        Longitude[i] = c.getDouble(c.getColumnIndex("longitude"));
                    }
                }

                TMapPoint point0 = new TMapPoint(Latitude[0], Longitude[0]);
                TMapPoint point1 = new TMapPoint(Latitude[1], Longitude[1]);

                tmapdata.findPathData(point0, point1, new FindPathDataListenerCallback() {
                   @Override
                    public void onFindPathData(TMapPolyLine polyLine) {
                       mMapView.addTMapPath(polyLine);
                       LogManager.printLog(polyLine.toString());
                    }
                });

            }
        });


    }



    //값 받아오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        locationList = new DBLocationList(MainActivity.this, "LocationList.db", null, 1);
        db = locationList.getWritableDatabase();
        locationList.onCreate(db);
        ContentValues values = new ContentValues();

        if (requestCode == ACT_EDIT && resultCode == RESULT_OK) {
            LogManager.printLog("standard : " + data.getStringExtra("OutName"));
            String outputName = data.getStringExtra("OutName");

            for(int i=0; i<7;i++) {
                LogManager.printLog(i + "번째 이름 : " + StartName[i] );

                if(outputName.equals(StartName[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    String tempLatitude = "" + StartLatitude[i];
                    String tempLongitude = "" + StartLongitude[i];
                    values.put("idNumber", 0);
                    values.put("name", StartName[i]);
                    //values.put("latitude", tempLatitude);
                   // values.put("longitude", tempLongitude);
                    //db.insert(0, StartName[i], StartLatitude[i], StartLongitude[i]);
                    db.insert("LocationList", null, values);
                    LogManager.printLog("name : " + StartName[i] + " latitude: " + tempLatitude
                    + " longitude : " + tempLongitude);
                }
            }

            for(int i=0; i<7;i++) {
                if(data.getStringExtra("OutName") == FinishName[i]) {
                    LogManager.printLog("finish : " + data.getStringExtra("OutName"));
                    values.put("idNumber", 2);
                    values.put("name", FinishName[i]);
                   // values.put("latitude", FinishLatitude[i]);
                    //values.put("longitude", FinishLongitude[i]);
                    db.insert("LocationList",null,values);
                    LogManager.printLog("name : " + FinishName[i] + " latitude: " + FinishLatitude[i]
                            + " longitude : " + FinishLongitude[i]);
                }
            }

            for(int i=0; i<7;i++) {
                if(data.getStringExtra("OutName") == Stop1Name[i]) {
                    LogManager.printLog("stop1 : " + data.getStringExtra("OutName"));
                    values.put("idNumber", 1);
                    values.put("name", Stop1Name[i]);
                   // values.put("latitude", Stop1Latitude[i]);
                   // values.put("longitude", Stop1Longitude[i]);
                    db.insert("LocationList",null,values);
                    LogManager.printLog("name : " + Stop1Name[i] + " latitude: " + Stop1Latitude[i]
                            + " longitude : " + Stop1Longitude[i]);
                }
            }

        }
    }
}
