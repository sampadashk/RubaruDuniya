package com.samiapps.kv.rubaruduniya;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KV on 13/3/17.
 */

public class imgAdapter extends RecyclerView.Adapter<imgAdapter.ViewHolder> {
    private static ClickListener clickListener;
    Context context;
    private ArrayList<rubaruduniya> articles;

    public imgAdapter(ArrayList<rubaruduniya> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.editorcard,parent,false);
        ViewHolder myviewholder=new ViewHolder(view) ;
        return myviewholder;


    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int listPosition) {


        rubaruduniya item = articles.get(listPosition);
        Context context = holder.imageViewIcon.getContext();
        String text=item.getTitle();
        String image=item.getPhotoUrl();


       holder.textView.setText(text);

        Glide.with(context)
                .load(image)
                .into(holder.imageViewIcon);


    }
    @Override
    public int getItemCount() {
        return articles.size();
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        imgAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageViewIcon;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageViewIcon = (ImageView) view.findViewById(R.id.imgEditor);
            this.textView = (TextView) view.findViewById(R.id.editortxt);
        }
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(getAdapterPosition(), v);
            }
       }










    }

