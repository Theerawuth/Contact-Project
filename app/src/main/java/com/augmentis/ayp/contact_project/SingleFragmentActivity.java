package com.augmentis.ayp.contact_project;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.augmentis.ayp.contact_project.Fragment.ContactListFragment;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private static final String TAG = "Contact";

    @LayoutRes
    protected int getLayoutResID(){
        return
                R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        Log.d(TAG, "On Create Activity");

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        if(f == null)
        {
            f = onCreateFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit();
            Log.d(TAG, "Fragment is create");
        }
        else
        {
            Log.d(TAG, "Fragment have already been created");
        }

    }

    protected abstract Fragment onCreateFragment();


}
