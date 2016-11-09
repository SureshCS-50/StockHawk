package com.sam_chordas.android.stockhawk.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sam_chordas.android.stockhawk.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {
    WebView webView;
    String stock;


    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        Bundle symbol = getArguments();
        stock = symbol.getString(getResources().getString(R.string.key_symbol));
        webView = (WebView) rootView.findViewById(R.id.web_chart);
        webView.getSettings().setJavaScriptEnabled(true);
        String s = "http://empyrean-aurora-455.appspot.com/charts.php?symbol=" + stock;
        webView.loadUrl(s);
        return rootView;
    }
}