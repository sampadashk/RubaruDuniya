package com.samiapps.kv.rubaruduniya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KV on 6/3/17.
 */

public class articlePage extends Fragment {
    private FirebaseDatabase firebaseDtabase;
    private DatabaseReference dbaseReference;
    private imgAdapter imageAdapter;
    ArrayList<rubaruduniya> rubaru=new ArrayList<rubaruduniya>();
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    String uid;
   public static final String TAG=articlePage.class.getName();



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDtabase = FirebaseDatabase.getInstance();
        dbaseReference = firebaseDtabase.getReference().child("messages").child(uid);
        Log.d("checkt",dbaseReference.toString());




        //setHasOptionsMenu(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.headlinelayout, container, false);
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.editor_recycleview);
        //mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
       // mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());


        return rootView;
    }
    public articlePage() {

    }
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<rubaruduniya,BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<rubaruduniya,BlogViewHolder>(rubaruduniya.class,
                R.layout.editorcard,BlogViewHolder.class,dbaseReference)
        {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, rubaruduniya model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(getContext(),model.getPhotoUrl());

            }
        };
        mRecycleView.setAdapter(firebaseRecyclerAdapter);

    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        View mView;
        Context mContext;



        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mContext=itemView.getContext();
            itemView.setOnClickListener(this);
        }
        public void setTitle(String title)
        {
            Log.d("tag","I am here");
            TextView post_title= (TextView) mView.findViewById(R.id.editortxt);
            Log.d("chktitle",title);
            post_title.setText(title);
        }
        public void setImage(Context ct, String image)
        {
            ImageView imageView= (ImageView) mView.findViewById(R.id.imgEditor);
          //  Log.d("imgtag",image);
            Glide.with(ct)
                    .load(image).into(imageView);




        }

        @Override
        public void onClick(View v) {
            final ArrayList<rubaruduniya> rd = new ArrayList<>();
            String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("messages").child(userid);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        rd.add(snapshot.getValue(rubaruduniya.class));
                    }


                    int itemPosition = getLayoutPosition();
                    Log.d("postag",""+itemPosition);
                   rubaruduniya obj= rd.get(itemPosition);
                    Log.d("chkclk",obj.getTitle());

                    Intent intent = new Intent(mContext, ArticleDetail.class);
                    intent.putExtra("position", itemPosition);
                    Bundle b=new Bundle();
                    b.putSerializable(articlePage.TAG,obj);
                    intent.putExtras(b);


                    //intent.putExtra("article",rd);

                    mContext.startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        }

  /*  @Override
    public void onResume() {
        super.onResume();

        dbaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("chkcount", String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    rubaruduniya rbd = postSnapshot.getValue(rubaruduniya.class);
                    Log.d("checktitle",rbd.getTitle());
                    rubaru.add(rbd);




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
       // imageAdapter=new imgAdapter(rubaru,getContext());
       // mRecycleView.setAdapter(imageAdapter);


    }
    */









}
