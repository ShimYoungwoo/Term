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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.String;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;


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
    public Button btnBus;

    final static int ACT_EDIT = 0;

    //DBLctList locationList = new DBLctList(MainActivity.this, "SavedLocation.db", null, 1);
    //SQLiteDatabase dbLct;

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

    final String decidedStartName[] = new String[1];
    final double decidedStartLatitude[] = new double[1];
    final double decidedStartLongitude[] = new double[1];

    final String decidedFinishName[] = new String[1];
    final double decidedFinishLatitude[] = new double[1];
    final double decidedFinishLongitude[] = new double[1];

    final String decidedStopName[] = new String[4];
    final double decidedStopLatitude[] = new double[4];
    final double decidedStopLongitude[] = new double[4];

    public String resultPath="";

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
        btnBus = (Button) findViewById(R.id.Bus);

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

                LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ decidedStartLongitude[0] + "  " + decidedFinishName[0] + decidedFinishLatitude[0]+ decidedFinishLongitude[0]);

                //X : longitude, Y:latitude
                final String urlString = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                       + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                        + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                       + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8";

                LogManager.printLog(urlString);

                //String pathResult;

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            LogManager.printLog(urlString);
                            URL url = new URL(urlString);

                            StringBuffer sb = new StringBuffer();

                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");
                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String strResult;

                            while ((strResult = reader.readLine()) != null) {
                                builder.append(strResult);
                            }

                            LogManager.printLog("result: " + builder.toString());
                            resultPath = builder.toString();

                            String stringTotalTime = "totalTime";
                            String stringTotalFare = "totalFare";
                            String stringTaxiFare = "taxiFare";
                            String stringIndex = "index";

                            String s1 = resultPath.substring((resultPath.indexOf(stringTotalTime)+stringTotalTime.length()+3),(resultPath.indexOf(stringTotalFare)-8));
                            LogManager.printLog("totaltime : " + s1);

                            String s2 = resultPath.substring((resultPath.indexOf(stringTotalFare)+stringTotalTime.length()+3),(resultPath.indexOf(stringTaxiFare)-8));
                            LogManager.printLog("fare : " + s2);

                            String s3 = resultPath.substring((resultPath.indexOf(stringTaxiFare)+stringTotalTime.length()+3),(resultPath.indexOf(stringIndex)-8));
                            LogManager.printLog("taxi: " + s3);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                };
                thread.start();
             }
        });


        //google api 대중교통 길찾기
        btnBus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ decidedStartLongitude[0] + "  " + decidedFinishName[0] + decidedFinishLatitude[0]+ decidedFinishLongitude[0]);

                //X : longitude 경도, Y:latitude 위도
                /*
                final String urlString = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                        + "&startX=" + decidedStartLongitude[0]+ "&startY=" + decidedStartLatitude[0]
                        + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                        + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8";*/

                final String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                + "&origins=" + decidedStartLatitude[0] + "," + decidedStartLongitude[0]
                + "&destinations=" + decidedFinishLatitude[0] + "," + decidedFinishLongitude[0]
                + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                //LogManager.printLog(urlString);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            LogManager.printLog("thread 실행 " + urlString);
                            URL url = new URL(urlString);

                            StringBuffer sb = new StringBuffer();

                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");
                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String strResult;

                            while ((strResult = reader.readLine()) != null) {
                                builder.append(strResult);
                            }

                            LogManager.printLog("result: " + builder.toString());
                            resultPath = builder.toString();

                            String stringDuration = "duration";
                            String stringValue = "value";
                            String stringStatus = "status";
                            //String stringQutation = "\"";

                            String s1 = resultPath.substring((resultPath.indexOf(stringDuration)+stringDuration.length()+3),(resultPath.indexOf(stringStatus)-33));
                            LogManager.printLog("s1 : " + s1);

                            String s2 = s1.substring((s1.indexOf(stringValue)+stringValue.length()+4),s1.length());
                            LogManager.printLog("time : " + s2);

                            //status OK = 정상
                            //String s3 = resultPath.substring((resultPath.indexOf(stringStatus)+stringStatus.length()+5),(resultPath.indexOf(stringQutation)));
                            //LogManager.printLog("status: " + s3);


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
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

                    /*
                    values.put("_index", 2);
                    values.put("name", FinishName[i]);
                    values.put("latitude", FinishLatitude[i]);
                    values.put("longitude", FinishLongitude[i]);

                    dbLct.insert("SavedLocation", null, values);
                    */

                    LogManager.printLog("finish 입력된 name : " + FinishName[i] + ":"  + decidedFinishName[0]
                            + " latitude: " + FinishLatitude[i] + ":" + decidedFinishLatitude[0]
                            + " longitude : " + FinishLongitude[i] + ":" + decidedFinishLongitude[0]);
                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop1Name[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    decidedStopName[0] = Stop1Name[i];
                    decidedStopLatitude[0] = Stop1Latitude[i];
                    decidedStopLongitude[0] = Stop1Longitude[i];
                    /*
                    values.put("_index", 1);
                    values.put("name", Stop1Name[i]);
                    values.put("latitude", Stop1Latitude[i]);
                    values.put("longitude", Stop1Longitude[i]);

                    dbLct.insert("SavedLocation", null, values);
                    */

                    LogManager.printLog("입력된 name : " + Stop1Name[i] + " latitude: " + Stop1Latitude[i]
                            + " longitude : " + Stop1Longitude[i]);
                }
            }
        }
    }
}
