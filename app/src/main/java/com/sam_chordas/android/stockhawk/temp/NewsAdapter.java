package com.sam_chordas.android.stockhawk.temp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

import java.util.ArrayList;

/**
 * Created by Sureshkumar on 08/11/16.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<String> mTitles;
    private ArrayList<String> mSourceUrls;
    private ArrayList<String> mDescriptions;
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<String> title, ArrayList<String> description,
                       ArrayList<String> sourceUrls) {
        this.mTitles = title;
        this.mSourceUrls = sourceUrls;
        this.mDescriptions = description;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder holder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_news_item, parent, false);
        holder = new ViewHolder(view);
        holder.cardView.setTag(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.txtTitle.setText(mTitles.get(position));
            holder.txtDes.setText(mDescriptions.get(position));
            final String url = mSourceUrls.get(position);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    mContext.startActivity(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtDes;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cardView);
            txtTitle = (TextView) v.findViewById(R.id.txtHeadline);
            txtDes = (TextView) v.findViewById(R.id.txtDescription);
        }
    }

}
