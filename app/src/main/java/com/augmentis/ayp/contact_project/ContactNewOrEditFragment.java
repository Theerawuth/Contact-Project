package com.augmentis.ayp.contact_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

/**
 * Created by Theerawuth on 8/9/2016.
 */
public class ContactNewOrEditFragment extends Fragment {

    private static final String TAG = "Contact";
    private static final String CONTACT_ID = "contactNewOrEditActivity.contactId";

    public static ContactNewOrEditFragment newInstance(UUID contactId) {
        
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_ID, contactId);
        ContactNewOrEditFragment contactNewOrEditFragment = new ContactNewOrEditFragment();
        contactNewOrEditFragment.setArguments(args);
        return contactNewOrEditFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Show Edit Contact Page");
        View v = inflater.inflate(R.layout.fragment_new_or_edit_contact, container, false);




        return v;
    }
}
