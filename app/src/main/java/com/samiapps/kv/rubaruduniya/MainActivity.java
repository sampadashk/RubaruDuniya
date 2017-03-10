package com.samiapps.kv.rubaruduniya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private static String TAG = MainActivity.class.getName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static String mUsername;
    public static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 123;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsername = ANONYMOUS;
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
               // String s=firebaseAuth.getCurrentUser().getUid();
                if(user!=null)
                {
                    onSignedInInitialize(user.getDisplayName());
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new articlePage()).commit();

                }
                else
                {
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())).setTheme(R.style.FullscreenTheme)
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };


    }


    @Override
    public void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)

    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);



        return true;
    }
    private void onSignedInInitialize(String username)
    {
        mUsername=username;


    }
    private void onSignedOutCleanup()
    {
        mUsername=ANONYMOUS;


    }
    public void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode==RC_SIGN_IN)
        {
            if(resultcode==RESULT_OK)
            {
                Toast.makeText(this,"Signed in!",Toast.LENGTH_SHORT).show();

            }
            else if(resultcode==RESULT_CANCELED)
            {
                Toast.makeText(this,"Signed OUT!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.action_settings: {


            }
            case R.id.write_blog:
            {
                Intent intent=new Intent(this,writePage.class);
                intent.putExtra(writePage.TAG,MainActivity.mUsername);

                startActivity(intent);

            }



        }
        return super.onOptionsItemSelected(menuItem);


    }

}
