package com.augmentis.ayp.contact_project;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ContactListFragment extends Fragment {

    private static final String TAG = "Contact";
    private RecyclerView contactRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        contactRecycleView = (RecyclerView) v.findViewById(R.id.recycle_view_contact_list);
        contactRecycleView.setLayoutManager(new LinearLayoutManager((getActivity())));

        Log.d(TAG, "Show ContactList");
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
}
