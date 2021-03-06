package com.augmentis.ayp.contact_project;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.contact_project.Fragment.ContactListFragment;
import com.augmentis.ayp.contact_project.Fragment.ContactNewOrEditFragment;
import com.augmentis.ayp.contact_project.Model.Contact;

import java.util.UUID;

public class ContactNewOrEditActivity extends SingleFragmentActivity implements ContactNewOrEditFragment.Callbacks {

    private static final String CONTACT_ID = "contactNewOrEditActivity.contactId" ;

    @Override
    protected Fragment onCreateFragment() {
        UUID contactId = (UUID) getIntent().getSerializableExtra(CONTACT_ID);
        Fragment fragment = ContactNewOrEditFragment.newInstance(contactId);
        return fragment;
    }

    public static Intent newIntent(Context activity, UUID id) {
        Intent intent = new Intent(activity, ContactNewOrEditActivity.class);
        intent.putExtra(CONTACT_ID, id);
        return intent;
    }

    @Override
    public void onContactUpdate(Contact contact) {

    }

    @Override
    public void onContactDelete(Contact contact) {
        finish();
    }
}
