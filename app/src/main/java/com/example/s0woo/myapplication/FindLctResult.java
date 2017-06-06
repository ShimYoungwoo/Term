package com.example.s0woo.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.String;

/**
 * Created by s0woo on 2017-06-06.
 */

public class FindLctResult {

    //자동차 최소비용
    static int carFCost;
    static int carFTaxi;
    static int carFTime;

    //자동차 최단시간
    static int carSCost;
    static int carSTaxi;
    static int carSTime;

    //대중교통 최단시간
    static int busSTime;

    static String resultPath = "";

    // 자동차 무료우선
    public static void findCarFree(final String startName, final int startLabel, final String endName, final int endLabel, final String urlString) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    //LogManager.printLog("무료우선 Car : " + urlString);
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

                    //LogManager.printLog("FREE result: " + builder.toString());
                    resultPath = builder.toString();

                    String stringTotalTime = "totalTime";
                    String stringTotalFare = "totalFare";
                    String stringTaxiFare = "taxiFare";
                    String stringIndex = "index";

                    String s1 = resultPath.substring((resultPath.indexOf(stringTotalTime)+stringTotalTime.length()+3),(resultPath.indexOf(stringTotalFare)-8));
                    carFTime = Integer.parseInt(s1);

                    String s2 = resultPath.substring((resultPath.indexOf(stringTotalFare)+stringTotalTime.length()+3),(resultPath.indexOf(stringTaxiFare)-8));
                    carFCost = Integer.parseInt(s2);

                    String s3 = resultPath.substring((resultPath.indexOf(stringTaxiFare)+stringTotalTime.length()+2),(resultPath.indexOf(stringIndex)-8));
                    carFTaxi = Integer.parseInt(s3);

                    LogManager.printLog("car 최소비용 " + startName + "-label : " + startLabel + "  " + endName + "-label : "
                            + endLabel + " time " + carFTime + " cost " + carFCost + " taxi " + carFTaxi);

                    /*

                    SPOT 만들기

                     */


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }



    //자동차 최단시간
    public static void findCarShort(final String startName, final int startLabel, final String endName, final int endLabel, final String urlString) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    //LogManager.printLog("최단시간 Car : " + urlString);
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

                   // LogManager.printLog("FREE result: " + builder.toString());
                    resultPath = builder.toString();

                    String stringTotalTime = "totalTime";
                    String stringTotalFare = "totalFare";
                    String stringTaxiFare = "taxiFare";
                    String stringIndex = "index";

                    String s1 = resultPath.substring((resultPath.indexOf(stringTotalTime)+stringTotalTime.length()+3),(resultPath.indexOf(stringTotalFare)-8));
                    carSTime = Integer.parseInt(s1);

                    String s2 = resultPath.substring((resultPath.indexOf(stringTotalFare)+stringTotalTime.length()+3),(resultPath.indexOf(stringTaxiFare)-8));
                    carSCost = Integer.parseInt(s2);

                    String s3 = resultPath.substring((resultPath.indexOf(stringTaxiFare)+stringTotalTime.length()+2),(resultPath.indexOf(stringIndex)-8));
                    carSTaxi = Integer.parseInt(s3);

                    LogManager.printLog("car최단시간 " + startName + "-label : " + startLabel + "  " + endName + "-label : "
                            + endLabel + " time " + carSTime + " cost " + carSCost + " taxi " + carSTaxi);

                    /*

                    SPOT 만들기

                     */



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }



    //대중교통 최단시간
    public static void findBusShort(final String startName, final int startLabel, final String endName, final int endLabel, final String urlString) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                   // LogManager.printLog("bus 최단시간" + urlString);
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

                    String s1 = resultPath .substring((resultPath .indexOf(stringDuration)+stringDuration.length()+3),(resultPath .indexOf(stringStatus)-33));

                    String s2 = s1.substring((s1.indexOf(stringValue)+stringValue.length()+4),s1.length());
                    busSTime = Integer.parseInt(s2);

                    //status OK = 정상
                    //String s3 = resultPath.substring((resultPath.indexOf(stringStatus)+stringStatus.length()+5),(resultPath.indexOf(stringQutation)));
                    //LogManager.printLog("status: " + s3);

                    LogManager.printLog("bus최단시간 " + startName + "-label : " + startLabel + "  " + endName + "-label : "
                            + endLabel + " time " + busSTime);

                    /*

                    SPOT 만들기

                     */

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
