package com.augmentis.ayp.contact_project;

import android.support.v4.app.Fragment;

/**
 * Created by Theerawuth on 8/9/2016.
 */
public class ContactListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return new ContactListFragment();
    }
}
