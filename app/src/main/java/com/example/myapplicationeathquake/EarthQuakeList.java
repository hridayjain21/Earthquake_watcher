package com.example.myapplicationeathquake;


import android.graphics.Color;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationeathquake.Model.EarthQuake;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthQuakeList extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView listView;
    private RequestQueue requestQueue;
    private ArrayAdapter arrayAdapter;

    private List<EarthQuake> earthQuakeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_quake_list);

        earthQuakeList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_view);

        requestQueue = Volley.newRequestQueue(this);

        arrayList = new ArrayList<>();

        getAllEarthQuakes(Constants.BASE_URL);
    }

    public void getAllEarthQuakes(String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        EarthQuake earthQuake = new EarthQuake();

                        try {
                            JSONArray feature = response.getJSONArray("features");
                            for (int i = 0; i < feature.length(); i++) {

                                //Get the properties
                                JSONObject properties = feature.getJSONObject(i).getJSONObject("properties");

                                //Get the geometry
                                JSONObject geometry = feature.getJSONObject(i).getJSONObject("geometry");
                                //Get the coordinates
                                JSONArray coordinates = geometry.getJSONArray("coordinates");

                                //Get the longitute and latitude of the coordinates
                                double lon = coordinates.getDouble(0);
                                double lat = coordinates.getDouble(1);

                                earthQuake.setPlace(properties.getString("place"));
                                earthQuake.setType(properties.getString("type"));
                                earthQuake.setTime(properties.getLong("time"));
                                earthQuake.setLatitude(lat);
                                earthQuake.setLongitude(lon);

                                arrayList.add(earthQuake.getPlace());

                            }
                            //Setting up the Array Adapter
                            arrayAdapter = new ArrayAdapter(EarthQuakeList.this,android.R.layout.simple_list_item_1,
                                    android.R.id.text1,arrayList);
                            listView.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}

