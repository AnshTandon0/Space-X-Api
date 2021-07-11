package com.example.spacexapi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spacexapi.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CrewRepository crewRepository;
    List<CrewModal> crewModals = new ArrayList<>();
    private CrewRecyclerAdapter crewRecyclerAdapter;
    private ActivityMainBinding activityMainBinding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.delete.setOnClickListener(this::onClick);
        activityMainBinding.refresh.setOnClickListener(this::onClick);

        crewRepository = new CrewRepository(this);


        try {
            if (crewRepository.SelectAll().size() > 0) {
                displayData();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.refresh:

                ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                boolean connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();

                if ( connected )
                {
                    api_call();
                }
                else
                {
                    Toast.makeText(this, "Please Connect to internet", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.delete:

                AlertDialog.Builder alert = new AlertDialog.Builder(this)
                        .setMessage("Are you sure . All the data will be deleted ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                crewRepository.DeleteAll();
                                displayData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                alert.show();

                break;
        }

    }

    public void api_call() {

        // only 1 api call so using volley instead of retrofit


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, "https://api.spacexdata.com/v4/crew", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    CrewModal crewModal = new CrewModal();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        crewModal.setName(jsonArray.getJSONObject(i).getString("name"));
                        crewModal.setAgency(jsonArray.getJSONObject(i).getString("agency"));
                        crewModal.setImageUrl(jsonArray.getJSONObject(i).getString("image"));
                        crewModal.setWikipedia(jsonArray.getJSONObject(i).getString("wikipedia"));
                        crewModal.setStatus(jsonArray.getJSONObject(i).getString("status"));
                        writeToRoom(crewModal);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    private void writeToRoom(CrewModal crewModal) {
        crewRepository.Insert(crewModal);
        displayData();
    }

    private void displayData() {
        try {
            crewModals = crewRepository.SelectAll();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        crewRecyclerAdapter = new CrewRecyclerAdapter(this, crewModals);
        activityMainBinding.recyclerView.setAdapter(crewRecyclerAdapter);
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

}