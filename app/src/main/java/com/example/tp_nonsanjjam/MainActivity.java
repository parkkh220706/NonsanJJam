package com.example.tp_nonsanjjam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;
    EditText etDate;
    String date = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDate = findViewById(R.id.et_date);


        recyclerView = findViewById(R.id.recycler); // 메인액티비티와 연결
        adapter = new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_search).setOnClickListener(v->{

            date = etDate.getText().toString();

            Thread thread = new Thread() {
                @Override
                public void run() {

                    String address = "https://openapi.mnd.go.kr/3139313636313232343532363332303636/xml/DS_TB_MNDT_DATEBYMLSVC_ATC/2500/2800";

                    try {
                        URL url = new URL(address);
                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is);

                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp = factory.newPullParser();

                        xpp.setInput(isr);

                        int eventType = xpp.getEventType();

                        Item item = null;

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            switch (eventType) {
                                case XmlPullParser.START_DOCUMENT:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "파싱시작입니다만", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;

                                case XmlPullParser.START_TAG:
                                    String tagName = xpp.getName();

                                    if(tagName.equals("row")) {
                                        item = new Item();
                                    }else if(tagName.equals("dates")){
                                        xpp.next();
                                        item.dates=xpp.getText();
                                    }else if(tagName.equals("brst")){
                                        xpp.next();
                                        item.brst=xpp.getText();
                                    }else if(tagName.equals("lunc")){
                                        xpp.next();
                                        item.lunc=xpp.getText();
                                    }else if(tagName.equals("dinr")){
                                        xpp.next();
                                        item.dinr=xpp.getText();
                                    }
                                    break;

                                case XmlPullParser.TEXT:
                                    break;

                                case XmlPullParser.END_TAG:
                                    if(xpp.getName().equals("row")){
                                        if(item.dates.equals(date)) {
                                            items.add(item);
                                        }
                                    }
                                    break;
                            }

                            eventType = xpp.next();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
            };
            thread.start();
        });
        }



}
