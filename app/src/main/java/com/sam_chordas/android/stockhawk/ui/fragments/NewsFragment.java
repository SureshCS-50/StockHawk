package com.sam_chordas.android.stockhawk.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.adapters.NewsAdapter;
import com.sam_chordas.android.stockhawk.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> source_url = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private String stock;
    private NewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        Bundle symbol = getArguments();
        stock = symbol.getString(getResources().getString(R.string.key_symbol));

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recyclerViewNews);
        rv.setHasFixedSize(true);
        adapter = new NewsAdapter(getContext(), title, description, source_url);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(Constants.BASE_URL)
                .appendPath(Constants.SERVICE_URL)
                .appendQueryParameter(Constants.KEY_SEARCH_TEXT, stock)
                .appendQueryParameter(Constants.KEY_NEWS, "yes");

        data(builder.build().toString());

        return rootView;
    }

    private void data(String url) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String d = obj.getString("d");
                                JSONObject obj2 = new JSONObject(d);
                                String results = obj2.getString("results");
                                JSONArray array = new JSONArray(results);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    title.add(object.getString("Title"));
                                    source_url.add(object.getString("Url"));
                                    description.add(object.getString("Description"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), getResources().getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}