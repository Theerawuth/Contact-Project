package com.augmentis.ayp.contact_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.contact_project.Fragment.ContactListFragment;
import com.augmentis.ayp.contact_project.Fragment.ContactNewOrEditFragment;
import com.augmentis.ayp.contact_project.Model.Contact;


/**
 * Created by Theerawuth on 8/10/2016.
 */
public class ContactListActivity extends SingleFragmentActivity implements ContactListFragment.Callbacks, ContactNewOrEditFragment.Callbacks {

    @Override
    protected Fragment onCreateFragment() {
        return new ContactListFragment();
    }


    @Override
    public void onOpenSelectFirst() {

    }

    @Override
    public void onContactSelected(Contact contact) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ContactNewOrEditActivity.newIntent(this, contact.getId());
            startActivity(intent);
        }
        else
        {
            Fragment newDetail = ContactNewOrEditFragment.newInstance(contact.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }
    }

    @Override
    public void onContactUpdate(Contact contact) {
        ContactListFragment listFragment = (ContactListFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    public void onContactDelete(Contact contact) {
        ContactListFragment listFragment = (ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        ContactNewOrEditFragment detailFragment = (ContactNewOrEditFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        listFragment.updateUI();
        getSupportFragmentManager().beginTransaction().detach(detailFragment).commit();

    }

}
