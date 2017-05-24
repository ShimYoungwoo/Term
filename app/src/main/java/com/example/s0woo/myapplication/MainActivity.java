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
import android.content.Intent;
import android.widget.Button;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapPoint;

import org.json.JSONArray;

import java.lang.String;
import java.net.URI;
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

    DBLctList locationList = new DBLctList(MainActivity.this, "SavedLocation.db", null, 1);
    SQLiteDatabase dbLct;

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

    int count = 0;

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
                    count++;
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
                    count++;
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
                    count++;
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

                                Stop1Name[i] = item.getPOIName().toString();
                                Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                Stop1Latitude[i] = Latitude;
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

                String Name[] = new String[6];
                int Index[] = new int[6];
                double Latitude[] = new double[6];
                double Longitude[] = new double[6];
                int i=0;

                //locationList = new LocationListDB(MainActivity.this, "LctList.db", null, 1);
                dbLct = locationList.getWritableDatabase();
                //locationList.onCreate(db);

                //Cursor c = db.query("LctList", null, null, null, null, null, null);
                Cursor c = dbLct.rawQuery("SELECT * FROM SavedLocation", null);
                //c.moveToFirst();

                while(c.moveToNext()!=false) {
                    LogManager.printLog("for " + i);
                    Name[i] = c.getString(c.getColumnIndex("name"));
                    Index[i] = c.getInt(c.getColumnIndex("_index"));
                    Latitude[i] = c.getDouble(c.getColumnIndex("latitude"));
                    Longitude[i] = c.getDouble(c.getColumnIndex("longitude"));
                    LogManager.printLog(i + "번째 : " + Name[i] + " " + Index[i] + " " + Latitude[i] + " " + Longitude[i]);
                    i++;
                }

                //길찾기 _ 최소비용

               String url = " https://apis.skplanetx.com/tmap/routes?version=1&appKey=" + mApiKey
                        + "&startX=14368651.605895586&startY=4188210.3283031476&endX=14135591.321771959&endY=4518111.822510956"
                        + "&resCoordType=EPSG3857&tollgateFareOption=8";

                //JSONArray jarray = new JSONArray(url);

            }
        });

    }



    //값 받아오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dbLct = locationList.getWritableDatabase();
        locationList.onCreate(dbLct);
        ContentValues values = new ContentValues();

        if (requestCode == ACT_EDIT && resultCode == RESULT_OK) {
            LogManager.printLog("넘어온 값 : " + data.getStringExtra("OutName"));
            String outputName = data.getStringExtra("OutName");

            for(int i=0; i<7;i++) {
                if(outputName.equals(StartName[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    values.put("_index", 0);
                    values.put("name", StartName[i]);
                    values.put("latitude", StartLatitude[i]);
                    values.put("longitude", StartLongitude[i]);

                    dbLct.insert("SavedLocation", null, values);

                    LogManager.printLog("입력된 name : " + StartName[i] + " latitude: " + StartLatitude[i]
                    + " longitude : " + StartLongitude[i]);
                }
            }

            for(int i=0; i<7;i++) {
                if(outputName.equals(FinishName[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    values.put("_index", 2);
                    values.put("name", FinishName[i]);
                    values.put("latitude", FinishLatitude[i]);
                    values.put("longitude", FinishLongitude[i]);

                    dbLct.insert("SavedLocation", null, values);

                    LogManager.printLog("입력된 name : " + FinishName[i] + " latitude: " + FinishLatitude[i]
                            + " longitude : " + FinishLongitude[i]);
                }
            }

            for(int i=0; i<7;i++) {
                if(outputName.equals(Stop1Name[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    values.put("_index", 1);
                    values.put("name", Stop1Name[i]);
                    values.put("latitude", Stop1Latitude[i]);
                    values.put("longitude", Stop1Longitude[i]);
                    dbLct.insert("SavedLocation", null, values);

                    LogManager.printLog("입력된 name : " + Stop1Name[i] + " latitude: " + Stop1Latitude[i]
                            + " longitude : " + Stop1Longitude[i]);
                }
            }

        }
    }
}
