package com.example.s0woo.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapPoint;

import java.lang.String;
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

    public EditText editStop2;
    public ImageButton btnStop2;

    public EditText editStop3;
    public ImageButton btnStop3;

    public Button btnCar;
    public Button btnBus;

    final static int ACT_EDIT = 0;

    //DBLctList locationList = new DBLctList(MainActivity.this, "SavedLocation.db", null, 1);
    //SQLiteDatabase dbLct;

    // 주소 목록
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

    final String Stop2Name[] = new String[7];
    final String Stop2Address[] = new String[7];
    final double Stop2Latitude[] = new double[7];
    final double Stop2Longitude[] = new double[7];

    final String Stop3Name[] = new String[7];
    final String Stop3Address[] = new String[7];
    final double Stop3Latitude[] = new double[7];
    final double Stop3Longitude[] = new double[7];

    final String Stop4Name[] = new String[7];
    final String Stop4Address[] = new String[7];
    final double Stop4Latitude[] = new double[7];
    final double Stop4Longitude[] = new double[7];

    //보기 목록에서 선택된 주소의 이름과 위도 경도
    final String decidedStartName[] = new String[1];
    final double decidedStartLatitude[] = new double[1];
    final double decidedStartLongitude[] = new double[1];

    final String decidedFinishName[] = new String[1];
    final double decidedFinishLatitude[] = new double[1];
    final double decidedFinishLongitude[] = new double[1];

    final String decidedStopName[] = new String[4];
    final double decidedStopLatitude[] = new double[4];
    final double decidedStopLongitude[] = new double[4];

    // 도착지 라벨 카운트
    int cntInput = 0;

    public String urlCarF = "";
    public String urlCarS = "";
    public String urlBusS = "";


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

        editStop2 = (EditText) findViewById(R.id.stop2Edit);
        btnStop2 = (ImageButton) findViewById(R.id.stop2Btn);

        editStop3 = (EditText) findViewById(R.id.stop3Edit);
        btnStop3 = (ImageButton) findViewById(R.id.stop3Btn);

        btnCar = (Button) findViewById(R.id.Car);
        btnBus = (Button) findViewById(R.id.Bus);

        mMapView = new TMapView(this);
        configureMapView();

        //출발지
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStart.getText().toString();

                final Intent intent = new Intent(MainActivity.this, SubActivity.class);

                //입력된 값이 없으면
                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "출발지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                            //item 수가 7개 보다 적다면 있는 만큼 가지고오기
                            if(poiItem.size() < 7) {
                                for (int i = 0; i < poiItem.size(); i++) {

                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog(" POI Name: " + item.getPOIName().toString() + ", " +
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
                            }
                            //item수가 7개보다 많다면 7개만 갖고오기
                            else {
                                for (int i = 0; i < 7; i++) {

                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog(" POI Name: " + item.getPOIName().toString() + ", " +
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
                            }

                            //findAllPOI를 이용해서 찾은 최대 7개의 값을 목록창으로 넘기기
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
                    cntInput++;
                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size() < 7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
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
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
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
                    cntInput++;

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop1Name[i] = item.getPOIName().toString();
                                    Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop1Latitude[i] = Latitude;
                                    Stop1Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop1Name[i] = item.getPOIName().toString();
                                    Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop1Latitude[i] = Latitude;
                                    Stop1Longitude[i] = Longitude;
                                }
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


        btnStop2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStop2.getText().toString();

                final Intent intent = new Intent(MainActivity.this, SubActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();
                    cntInput++;

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop2Name[i] = item.getPOIName().toString();
                                    Stop2Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop2Latitude[i] = Latitude;
                                    Stop2Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop2Name[i] = item.getPOIName().toString();
                                    Stop2Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop2Latitude[i] = Latitude;
                                    Stop2Longitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", Stop2Name[0]);
                            intent.putExtra("Name1", Stop2Name[1]);
                            intent.putExtra("Name2", Stop2Name[2]);
                            intent.putExtra("Name3", Stop2Name[3]);
                            intent.putExtra("Name4", Stop2Name[4]);
                            intent.putExtra("Name5", Stop2Name[5]);
                            intent.putExtra("Name6", Stop2Name[6]);

                            intent.putExtra("Address0", Stop2Address[0]);
                            intent.putExtra("Address1", Stop2Address[1]);
                            intent.putExtra("Address2", Stop2Address[2]);
                            intent.putExtra("Address3", Stop2Address[3]);
                            intent.putExtra("Address4", Stop2Address[4]);
                            intent.putExtra("Address5", Stop2Address[5]);
                            intent.putExtra("Address6", Stop2Address[6]);
                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });


        btnStop3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editStop3.getText().toString();

                final Intent intent = new Intent(MainActivity.this, SubActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();
                    cntInput++;

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop3Name[i] = item.getPOIName().toString();
                                    Stop3Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop3Latitude[i] = Latitude;
                                    Stop3Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop3Name[i] = item.getPOIName().toString();
                                    Stop3Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop3Latitude[i] = Latitude;
                                    Stop3Longitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", Stop3Name[0]);
                            intent.putExtra("Name1", Stop3Name[1]);
                            intent.putExtra("Name2", Stop3Name[2]);
                            intent.putExtra("Name3", Stop3Name[3]);
                            intent.putExtra("Name4", Stop3Name[4]);
                            intent.putExtra("Name5", Stop3Name[5]);
                            intent.putExtra("Name6", Stop3Name[6]);

                            intent.putExtra("Address0", Stop3Address[0]);
                            intent.putExtra("Address1", Stop3Address[1]);
                            intent.putExtra("Address2", Stop3Address[2]);
                            intent.putExtra("Address3", Stop3Address[3]);
                            intent.putExtra("Address4", Stop3Address[4]);
                            intent.putExtra("Address5", Stop3Address[5]);
                            intent.putExtra("Address6", Stop3Address[6]);
                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });


        //TMAP 차 경로
        btnCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TMapData tmapdata = new TMapData();

                //locationList = new LocationListDB(MainActivity.this, "LctList.db", null, 1);
                //dbLct = locationList.getWritableDatabase();
                //locationList.onCreate(db);

                //Cursor c = db.query("LctList", null, null, null, null, null, null);
                //Cursor c = dbLct.rawQuery("SELECT * FROM SavedLocation", null);
                //c.moveToFirst();

                /*
                    c.moveToNext();
                    Name[i] = c.getString(c.getColumnIndex("name"));
                    Index[i] = c.getInt(c.getColumnIndex("_index"));
                    Latitude[i] = c.getDouble(c.getColumnIndex("latitude"));
                    Longitude[i] = c.getDouble(c.getColumnIndex("longitude"));
                    c.moveToNext();
                 */

                //final String urlFree[] = new String[14];
                //final String urlShort[] = new String[14];


                //경유지 없는경우
                if(cntInput==1) {
                    LogManager.printLog("경유지X -> 결과 1개");
                    //X : longitude, Y:latitude
                    //무료우선
                    urlCarF = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                            + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                            + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                            + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                    //최소시간
                    urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                            + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                            + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                            + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                    FindLctResult.findCarFree(decidedStartName[0], 0, decidedFinishName[0], 1, urlCarF);
                    FindLctResult.findCarShort(decidedStartName[0], 0, decidedFinishName[0], 1, urlCarS);
                }

                //StartPoint to Stop
                for(int i=0; i<4; i++) {
                    if(decidedStopName[i]!=null) {
                        //LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ " " +decidedStartLongitude[0] +
                        //        " stop" +i + " " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]);

                        //X : longitude, Y:latitude
                        //무료우선
                        urlCarF = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                                + "&endX=" + decidedStopLongitude[i] + "&endY=" + decidedStopLatitude[i]
                                + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                        //최소시간
                        urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                                + "&endX=" + decidedStopLongitude[i] + "&endY=" + decidedStopLatitude[i]
                                + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                        //LogManager.printLog("Start Label 0 to Stop" + (i+1) +": " + urlCarF);
                        FindLctResult.findCarFree(decidedStartName[0], 0, decidedStopName[i], (i+1), urlCarF);
                        FindLctResult.findCarShort(decidedStartName[0], 0, decidedStopName[i], (i+1), urlCarS);
                    }
                    else {
                        break;
                    }
                }

                //Stop to Stop
                for(int i=0; i<3; i++) {
                    for(int j=i+1; j<4; j++) {
                        if(decidedStopName[i]!=null && decidedStopName[j]!=null) {
                           // LogManager.printLog("stop" + i + ": " + decidedStopName[i] + " " + decidedStopLatitude[i]+ " " +decidedStopLongitude[i]
                             //       + " stop" + j + ": " + decidedStopName[j] + decidedStopLatitude[j]+ " " +decidedStopLongitude[j]);

                            //X : longitude, Y:latitude
                            //무료우선
                            urlCarF= "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                    + "&startX=" + decidedStopLongitude[i]+ "&startY=" + decidedStopLatitude[i]
                                    + "&endX=" + decidedStopLongitude[j] + "&endY=" + decidedStopLatitude[j]
                                    + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                            //최소시간
                            urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                    + "&startX=" + decidedStopLongitude[i]+ "&startY=" + decidedStopLatitude[i]
                                    + "&endX=" + decidedStopLongitude[j] + "&endY=" + decidedStopLatitude[j]
                                    + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                            //LogManager.printLog("StopLabel" + i+1 + " to Stop" + j+1 +": " + urlCarF);
                            FindLctResult.findCarFree(decidedStopName[i], (i+1) , decidedStopName[j], (j+1), urlCarF);
                            FindLctResult.findCarShort(decidedStopName[i], (i+1), decidedStopName[j], (j+1), urlCarS);
                        }
                        else {
                            break;
                        }
                    }
                }

                //Stop to Finish
                for(int i=0; i<4; i++) {
                    if(decidedStopName[i]!=null) {
                        //LogManager.printLog("stop " + i + " : " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]
                        //        + " finish: " + decidedFinishName[0] + decidedFinishLatitude[0] + " " + decidedFinishLongitude[0]);
                        //X : longitude, Y:latitude
                        //무료우선
                        urlCarF = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                + "&startX=" + decidedStopLongitude[i]+ "&startY=" + decidedStopLatitude[i]
                                + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                                + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                        //최소시간
                        urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                + "&startX=" + decidedStopLongitude[i]+ "&startY=" + decidedStopLatitude[i]
                                + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                                + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                        //LogManager.printLog("StopLabel" + (i+1) + " to finish" +": " + urlCarF);
                        FindLctResult.findCarFree(decidedStopName[i], i+1 , decidedFinishName[0], cntInput, urlCarF);
                        FindLctResult.findCarShort(decidedStopName[i], i+1, decidedFinishName[0], cntInput, urlCarS);
                    }
                    else {
                        break;
                    }
                }

                //무료우선
                /*
                final String urlString = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                       + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                        + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                       + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8";
                */

             }
        });


        //google api 대중교통 길찾기
        btnBus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ decidedStartLongitude[0] + "  " + decidedFinishName[0] + decidedFinishLatitude[0]+ decidedFinishLongitude[0]);

                /*
                final String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                + "&origins=" + decidedStartLatitude[0] + "," + decidedStartLongitude[0]
                + "&destinations=" + decidedFinishLatitude[0] + "," + decidedFinishLongitude[0]
                + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";
                */

                //경유지 없는경우
                if(cntInput==1) {
                    //LogManager.printLog("경유지X -> 결과 1개");

                    urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                            + "&origins=" + decidedStartLatitude[0] + "," + decidedStartLongitude[0]
                            + "&destinations=" + decidedFinishLatitude[0] + "," + decidedFinishLongitude[0]
                            + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                    FindLctResult.findBusShort(decidedStartName[0], 0, decidedFinishName[0], 1, urlBusS);
                }

                //StartPoint to Stop
                for(int i=0; i<4; i++) {
                    if(decidedStopName[i]!=null) {
                        //LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ " " +decidedStartLongitude[0] +
                        //        " stop" +i + " " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]);

                        urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                                + "&origins=" + decidedStartLatitude[0] + "," + decidedStartLongitude[0]
                                + "&destinations=" + decidedStopLatitude[i] + "," + decidedStopLongitude[i]
                                + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                        FindLctResult.findBusShort(decidedStartName[0], 0, decidedStopName[i], (i+1), urlBusS);
                    }
                    else {
                        break;
                    }
                }

                //Stop to Stop
                for(int i=0; i<3; i++) {
                    for(int j=i+1; j<4; j++) {
                        if(decidedStopName[i]!=null && decidedStopName[j]!=null) {
                            // LogManager.printLog("stop" + i + ": " + decidedStopName[i] + " " + decidedStopLatitude[i]+ " " +decidedStopLongitude[i]
                            //       + " stop" + j + ": " + decidedStopName[j] + decidedStopLatitude[j]+ " " +decidedStopLongitude[j]);

                            urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                                    + "&origins=" + decidedStopLatitude[i] + "," + decidedStopLongitude[i]
                                    + "&destinations=" + decidedStopLatitude[j] + "," + decidedStopLongitude[j]
                                    + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                            FindLctResult.findBusShort(decidedStopName[i], (i+1), decidedStopName[j], (j+1), urlBusS);
                        }
                        else {
                            break;
                        }
                    }
                }

                //Stop to Finish
                for(int i=0; i<4; i++) {
                    if(decidedStopName[i]!=null) {
                        //LogManager.printLog("stop " + i + " : " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]
                        //        + " finish: " + decidedFinishName[0] + decidedFinishLatitude[0] + " " + decidedFinishLongitude[0]);

                        urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                                + "&origins=" + decidedStopLatitude[i] + "," + decidedStopLongitude[i]
                                + "&destinations=" + decidedFinishLatitude[0] + "," + decidedFinishLongitude[0]
                                + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                        FindLctResult.findBusShort(decidedStopName[i], (i+1), decidedFinishName[0], cntInput, urlBusS);
                    }
                    else {
                        break;
                    }
                }

            }
        });
    }


    // SubAcitivy 값 받아오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //dbLct = locationList.getWritableDatabase();
        //locationList.onCreate(dbLct);
        //ContentValues values = new ContentValues();

        if (requestCode == ACT_EDIT && resultCode == RESULT_OK) {
            //목록 화면에서 선택된 값값
            LogManager.printLog("넘어온 값 : " + data.getStringExtra("OutName"));
            String outputName = data.getStringExtra("OutName");

            //start
            for (int i = 0; i < 7; i++) {
                if (outputName.equals(StartName[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    decidedStartName[0] = StartName[i];
                    decidedStartLatitude[0] = StartLatitude[i];
                    decidedStartLongitude[0] = StartLongitude[i];

                    /*
                    values.put("_index", 0);
                    values.put("name", StartName[i]);
                    values.put("latitude", StartLatitude[i]);
                    values.put("longitude", StartLongitude[i]);

                    dbLct.insert("SavedLocation", null, values);
                    */

                    LogManager.printLog("start 입력된 name : " + StartName[i] + " : " + decidedStartName[0]
                            + "\nlatitude: " + StartLatitude[i] + " : " + decidedStartLatitude[0]
                            + "\nlongitude : " + StartLongitude[i] + " : " + decidedStartLongitude[0]);
                }
            }

            //finish
            for (int i = 0; i < 7; i++) {
                if (outputName.equals(FinishName[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));

                    decidedFinishName[0] = FinishName[i];
                    decidedFinishLatitude[0] = FinishLatitude[i];
                    decidedFinishLongitude[0] = FinishLongitude[i];

                    LogManager.printLog("finish 입력된 name : " + FinishName[i] + ":"  + decidedFinishName[0]
                            + " latitude: " + FinishLatitude[i] + ":" + decidedFinishLatitude[0]
                            + " longitude : " + FinishLongitude[i] + ":" + decidedFinishLongitude[0]);
                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop1Name[i])) {
                    LogManager.printLog("stop1 : " + data.getStringExtra("OutName"));
                    decidedStopName[0] = Stop1Name[i];
                    decidedStopLatitude[0] = Stop1Latitude[i];
                    decidedStopLongitude[0] = Stop1Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop1Name[i] + " latitude: " + Stop1Latitude[i]
                            + " longitude : " + Stop1Longitude[i]);
                }
            }


            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop2Name[i])) {
                    LogManager.printLog("stop2: " + data.getStringExtra("OutName"));
                    decidedStopName[1] = Stop2Name[i];
                    decidedStopLatitude[1] = Stop2Latitude[i];
                    decidedStopLongitude[1] = Stop2Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop2Name[i] + " latitude: " + Stop2Latitude[i]
                            + " longitude : " + Stop2Longitude[i]);
                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop3Name[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    decidedStopName[2] = Stop3Name[i];
                    decidedStopLatitude[2] = Stop3Latitude[i];
                    decidedStopLongitude[2] = Stop3Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop3Name[i] + " latitude: " + Stop3Latitude[i]
                            + " longitude : " + Stop3Longitude[i]);
                }
            }

            /*
            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop4Name[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    decidedStopName[3] = Stop4Name[i];
                    decidedStopLatitude[3] = Stop4Latitude[i];
                    decidedStopLongitude[3] = Stop4Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop4Name[i] + " latitude: " + Stop4Latitude[i]
                            + " longitude : " + Stop4Longitude[i]);
                }
            }
            */


        }
    }
}
