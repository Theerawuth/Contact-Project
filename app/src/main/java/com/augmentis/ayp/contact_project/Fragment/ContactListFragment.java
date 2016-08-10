package com.augmentis.ayp.contact_project.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.contact_project.ContactAdapter;
import com.augmentis.ayp.contact_project.Model.Contact;
import com.augmentis.ayp.contact_project.Model.ContactLab;
import com.augmentis.ayp.contact_project.ContactNewOrEditActivity;
import com.augmentis.ayp.contact_project.R;

import java.util.List;

public class ContactListFragment extends Fragment {

    private static final String TAG = "Contact";
    private RecyclerView contactRecycleView;
    private ContactAdapter contactAdapter;
    private Callbacks callbacks;

    public interface  Callbacks {
        void onContactSelected(Contact contact);
        void onOpenSelectFirst();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context; // activity
        callbacks.onOpenSelectFirst();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        contactRecycleView = (RecyclerView) v.findViewById(R.id.recycle_view_contact_list);
        contactRecycleView.setLayoutManager(new GridLayoutManager((getActivity()), 3));

        Log.d(TAG, "Show ContactList");
        updateUI();
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call toolbar menu
        setHasOptionsMenu(true);
    }


    /////////////////////////////////// Menu Toolbars //////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_list, menu);
        Log.d(TAG, "Show Add Icon");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_contact:
                Log.d(TAG, "ADD Contact:");
                Contact contact = new Contact();
                ContactLab.getInstance(getActivity()).addContact(contact);
                Intent intent = ContactNewOrEditActivity.newIntent(getActivity(), contact.getId());
                startActivity(intent);

                //default case
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Update UI
     */
    public void updateUI(){
        ContactLab contactLab = ContactLab.getInstance(getActivity());
        List<Contact> contactList = contactLab.getContact();
        Log.d(TAG, "updateUI");

        if(!contactList.isEmpty()){
            Log.d(TAG, "Have list in database");
        }

        if(contactAdapter == null){
            contactAdapter = new ContactAdapter(contactList, getActivity());
            contactRecycleView.setAdapter(contactAdapter);
        }
        else
        {
            contactAdapter.setContact(contactLab.getContact());
            contactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}



