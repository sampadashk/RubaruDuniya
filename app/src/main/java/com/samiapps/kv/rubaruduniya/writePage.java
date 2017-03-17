package com.samiapps.kv.rubaruduniya;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by KV on 6/3/17.
 */

public class writePage extends AppCompatActivity {
    public static final String TAG = writePage.class.getName();
    private FirebaseDatabase mFirebaseDtabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    EditText editTitle;
    EditText editContent;
    MenuItem draftButton;
    MenuItem saveButton;
    String userName;
    int i=0;
    String uid;
    String utitle;
    String refuid;
    int draftPressed=0;
    private String keyn;
    Boolean saved=false;
    ImageButton photopick;
    private static final int RC_PHOTO_PICKER =  2;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    rubaruduniya rbd;



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_article);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("checkuid",uid);

        editTitle = (EditText) findViewById(R.id.post_title_edit);
        editContent = (EditText) findViewById(R.id.post_content);
        photopick=(ImageButton) findViewById(R.id.photoPickerButton);
       // editContent.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        Intent it = getIntent();
        userName = it.getStringExtra(TAG);
        mFirebaseDtabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDtabase.getReference().child("messages").child(uid);
        firebaseStorage= FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("article_photos");
        photopick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });







    }
    public void onActivityResult(int requestcode,int resultcode,Intent data) {

        if (requestcode == RC_PHOTO_PICKER && resultcode == RESULT_OK) {
            final Uri SelectedImageUri = data.getData();
            if (editTitle.getText().toString() != null) {


                StorageReference photoref = storageReference.child(SelectedImageUri.getLastPathSegment());
                photoref.putFile(SelectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d("uricheck",downloadUrl.toString());

                        rbd = new rubaruduniya(saved, editTitle.getText().toString(), editContent.getText().toString(), downloadUrl.toString());
                        if (keyn != null) {
                            Log.d("checkkey", " " + keyn);

                            databaseReference.child(keyn).setValue(rbd);
                        } else {
                            keyn = databaseReference.push().getKey();
                            databaseReference.child(keyn).setValue(rbd);

                        }
                        draftButton.setEnabled(true);


                    }
                });
            }
        }
    }



    public void onStart() {
        super.onStart();


        editContent.addTextChangedListener(new TextWatcher() {



            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((s.toString().trim().length()) > 0)

                {
                    draftButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        try
        {
            attachDatabaseReadListener();
        }
        catch(NullPointerException ne)
        {

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)

    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_write, menu);
        draftButton = (MenuItem) menu.findItem(R.id.draft);
        saveButton=(MenuItem)menu.findItem(R.id.savecl);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        //String keyn;

        switch (id) {
            case R.id.draft: {
                if (draftButton.isEnabled()) {
                    saveButton.setEnabled(true);

                    if(draftPressed==0)
                    {
                    try {
                        if(rbd==null) {

                            rbd = new rubaruduniya(saved, editTitle.getText().toString(), editContent.getText().toString(), null);
                        }
                       if(keyn!=null)
                       {
                           databaseReference.child(keyn).setValue(rbd);
                       }
                        else {

                           keyn = databaseReference.push().getKey();
                           databaseReference.child(keyn).setValue(rbd);
                       }

                        draftPressed+=1;
                        Log.d("checkuser",userName);
                        Toast.makeText(this,"draft saved",Toast.LENGTH_SHORT).show();

                        draftButton.setEnabled(false);


                    } catch (NullPointerException ne) {

                    }
                } else if (draftPressed>0) {
                        rubaruduniya rd = new rubaruduniya(saved,editTitle.getText().toString(), editContent.getText().toString(), rbd.getPhotoUrl());
                        databaseReference.child(keyn).setValue(rd);
                    }

                }
                break;


            }
            case R.id.savecl:
            {
                draftPressed=0;
                keyn=null;
                Intent inti=new Intent(this,MainActivity.class);
                startActivity(inti);
                break;


            }

        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void attachDatabaseReadListener()
    {
        if(childEventListener==null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                  //  saveButton.setEnabled(true);
//                    rubaruduniya rbd = dataSnapshot.getValue(rubaruduniya.class);
                  //  refuid=dataSnapshot.getKey();
                   // utitle=rbd.getTitle();
                   // refuid=rbd.getUid();
                 //   Log.d("ctitl",utitle);
                  //  Log.d("ruid",refuid);



                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference.addChildEventListener(childEventListener);
        }
    }
}
