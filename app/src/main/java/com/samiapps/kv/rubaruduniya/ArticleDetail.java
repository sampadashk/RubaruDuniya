package com.samiapps.kv.rubaruduniya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KV on 16/3/17.
 */

public class ArticleDetail extends AppCompatActivity {
    int pos;
    rubaruduniya artsel;
    ImageView ivw;
    TextView tvtitle;
    TextView tvcontent;
   // ArrayList<rubaruduniya> rbd;

public void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detaillayout);
    Intent intent=getIntent();
    pos=intent.getIntExtra("position",-1);
    Log.d("checkpos",""+pos);
   artsel=(rubaruduniya)intent.getSerializableExtra(articlePage.TAG);
    ivw=(ImageView) findViewById(R.id.display_image);
    tvtitle=(TextView)findViewById(R.id.post_title);
    tvcontent=(TextView)findViewById(R.id.post_con);
    Glide.with(this).load(artsel.getPhotoUrl()).into(ivw);
    tvtitle.setText(artsel.getTitle());
    tvcontent.setText(artsel.getContent());


   // Log.d("chkobj",""+artsel);



}
}
