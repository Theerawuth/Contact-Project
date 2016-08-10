package com.augmentis.ayp.contact_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.contact_project.Fragment.ContactListFragment;
import com.augmentis.ayp.contact_project.Model.Contact;


/**
 * Created by Theerawuth on 8/10/2016.
 */
public class ContactListActivity extends SingleFragmentActivity implements ContactListFragment.Callbacks {

    @Override
    protected Fragment onCreateFragment() {
        return new ContactListFragment();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_masterdetail;

    }

    @Override
    public void onContactSelected(Contact contact) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ContactNewOrEditActivity.newIntent(this, contact.getId());
            startActivity(intent);
        }
    }






}
