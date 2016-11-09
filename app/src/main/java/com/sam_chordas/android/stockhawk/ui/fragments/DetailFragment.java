package com.sam_chordas.android.stockhawk.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.sam_chordas.android.stockhawk.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private TextView name, lastprice, change, changepercent, high, low, open;
    private String stock;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle symbol = getArguments();
        stock = symbol.getString(getResources().getString(R.string.key_symbol));

        name = (TextView) rootView.findViewById(R.id.txtName);
        high = (TextView) rootView.findViewById(R.id.txtHigh);
        low = (TextView) rootView.findViewById(R.id.txtLow);
        open = (TextView) rootView.findViewById(R.id.txtOpen);
        lastprice = (TextView) rootView.findViewById(R.id.txtPrice);
        change = (TextView) rootView.findViewById(R.id.txtChangeValue);
        changepercent = (TextView) rootView.findViewById(R.id.txtChangePercent);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(Constants.BASE_URL)
                .appendPath(Constants.SERVICE_URL)
                .appendQueryParameter("quote", "yes")
                .appendQueryParameter(Constants.KEY_SYMBOL, stock);

        data(builder.build().toString());

        return rootView;
    }

    public void data(String url) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                name.setText(obj.getString("Name"));
                                lastprice.setText(obj.getString("LastPrice"));
                                change.setText(obj.getString("Change"));
                                String s = obj.getString("ChangePercent") + "%";
                                changepercent.setText(s);
                                high.setText(obj.getString("High"));
                                low.setText(obj.getString("Low"));
                                open.setText(obj.getString("Open"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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