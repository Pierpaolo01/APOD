package com.pierpaolopascarella.apod;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StartFragment.StartFragmentListener, EndFragment.EndFragmentListener{

    private static String urlData;
    private static String urlDataWeek;

    private String formattedDateEnd;
    private String formattedDate;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    String startDate;
    String endDate;


    private Context context;
    private List<ListItem> listItemList;

    private ImageView mainIMG;
    private TextView mainTitle;
    private TextView mainText;
    private TextView mainDate;
    public static String media_type;
    private android.support.v7.widget.Toolbar toolbar;


 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mainIMG = findViewById(R.id.imgAPOD);
        mainTitle = findViewById(R.id.titleAPOD);
        mainText = findViewById(R.id.mainText);
        mainDate = findViewById(R.id.dateAPOD);


            ImageView startDateBTN = findViewById(R.id.ListCalendar);
        startDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "BottomSheetDialog");
            }
        });


         Calendar calendar = Calendar.getInstance();
         Date date = calendar.getTime();

         SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
         long milidate = calendar.getTimeInMillis() - 604800000; // 604800000 = 7 * 24 * 60 * 60 * 1000
         formattedDateEnd = dateFormat.format(milidate);
         formattedDate = dateFormat.format(date);

         Log.i("date", this.startDate + endDate + formattedDate + formattedDateEnd);


        urlData = "https://api.nasa.gov/planetary/apod?api_key=HFJGnBVswqPN8Dod5ITHPPgdXw10votfAsEoKKTG&date=" + formattedDate;

         urlDataWeek = "https://api.nasa.gov/planetary/apod?api_key=HFJGnBVswqPN8Dod5ITHPPgdXw10votfAsEoKKTG&start_date="
                 + formattedDateEnd + "&end_date=" + formattedDate;




        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-LightItalic.ttf");
        mainTitle.setTypeface(typeface);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.recyclerViewRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItemList = new ArrayList<>();

        homeScreenData();
        RecyclerViewData();
    }

    private void homeScreenData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading info...");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String date = jsonObject.getString("date");
                            String text = jsonObject.getString("explanation");
                            String title = jsonObject.getString("title");
                            String picture = jsonObject.getString("hdurl");

                            pd.cancel();
                            mainTitle.setText(title);
                            mainText.setText(text);
                            mainDate.setText(date);
                            Picasso.get()
                                    .load(picture)
                                    .into(mainIMG);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            pd.cancel();
                            Toast.makeText(MainActivity.this, "there was an error in HomeScreen", Toast.LENGTH_SHORT).show();
                            Log.i("error", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }



    private void RecyclerViewData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading info...");
        pd.show();

        if (endDate != null & startDate !=null ) {
            urlDataWeek = "https://api.nasa.gov/planetary/apod?api_key=HFJGnBVswqPN8Dod5ITHPPgdXw10votfAsEoKKTG&start_date="
                    + endDate + "&end_date=" + startDate;
        }else{
            urlDataWeek = "https://api.nasa.gov/planetary/apod?api_key=HFJGnBVswqPN8Dod5ITHPPgdXw10votfAsEoKKTG&start_date="
                    + formattedDateEnd + "&end_date=" + formattedDate;
        }

        Log.i("date", urlDataWeek);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlDataWeek,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONArray jArray = new JSONArray(response);

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject o = jArray.getJSONObject(i);

                                media_type = o.getString("media_type");

                                if(media_type.equals("image")) {
                                    Log.i("info", "picture,");

                                    String title = o.getString("title");
                                    String text = o.getString("explanation");
                                    String date = o.getString("date");
                                    String url = o.getString("url");


                                    ListItem item = new ListItem(title, text, date, url);
                                    listItemList.add(item);

                                    Log.i("info",item.getTitle() +" / "+item.getText() +" / "+ item.getDate() +" / "+item.getPictureURL());

                                }
                                else if(media_type.equals("video")){
                                    Log.i("info", "video,");

                                    ListItem item = new ListItem(
                                            o.getString("title"),
                                            o.getString("explanation"),
                                            o.getString("date"),
                                            o.getString("url")
                                    );

                                    Log.i("info",item.getTitle() +" / "+item.getText() +" / "+ item.getDate() +" / "+item.getPictureURL());
                                    listItemList.add(item);

                                }

                            }

                            adapter = new AdapterClass(listItemList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pd.cancel();
                            Toast.makeText(MainActivity.this, "there was an error in recyclerView catch", Toast.LENGTH_LONG).show();
                            Log.i("info", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Toast.makeText(MainActivity.this, "Error in RecyclerView onErrorResponse", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

    }



    //the start and end date listeners
    @Override
    public void onEndFragmentListener(CharSequence input) {
        Toast.makeText(this, "End date: " + String.valueOf(input), Toast.LENGTH_SHORT).show();
        endDate = String.valueOf(input);

        if(input != null){
            RecyclerViewData();
        }
        Log.i("date", urlDataWeek);

    }

    @Override
    public void onStartFragmentListener(CharSequence date1) {
        Toast.makeText(this, "End date: " + String.valueOf(date1), Toast.LENGTH_SHORT).show();
        startDate = String.valueOf(date1);
    }

}



//todo sort out different media types - add youtube player item to recView
//todo make imgView fullsize and zoomable
//todo make img downloadable or set as background
//todo make a share feature for the whole app but also for apods
//todo make the first date picked indicater on the second date calendar fragment


//left off: todo update the api link and make the recycleview load when dates are picked



