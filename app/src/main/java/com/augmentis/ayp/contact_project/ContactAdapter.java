package com.augmentis.ayp.contact_project;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.contact_project.Fragment.ContactListFragment;
import com.augmentis.ayp.contact_project.Model.Contact;

import java.util.List;

/**
 * Created by Theerawuth on 8/10/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {

    private static final String TAG = "Contact";
    private List<Contact> _contactList;
    private FragmentActivity _fragmentActivity;
    private ContactListFragment.Callbacks _callbacks;


    public ContactAdapter(List<Contact> contactList, FragmentActivity fragmentActivity, ContactListFragment.Callbacks callbacks) {
        Log.d(TAG, "ContactAdapter");
        _contactList = contactList;
        _fragmentActivity = fragmentActivity;
        _callbacks = callbacks;
    }

    public void setContact(List<Contact> contactList){
        _contactList = contactList;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "ContactAdapter: View Holder");

        LayoutInflater layoutInflater = LayoutInflater.from(_fragmentActivity);
        View v = layoutInflater.inflate(R.layout.list_item_contact, parent, false);

        return new ContactHolder(v, _fragmentActivity, _callbacks);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Log.d(TAG, "ContactAdapter: onBindViewHolder");

        Contact contact = _contactList.get(position);
        holder.bind(contact, position);

    }

    @Override
    public int getItemCount() {

        return _contactList.size();
    }
}
