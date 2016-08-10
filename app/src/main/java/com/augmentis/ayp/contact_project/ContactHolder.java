package com.augmentis.ayp.contact_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.contact_project.Fragment.ContactNewOrEditFragment;
import com.augmentis.ayp.contact_project.Model.Contact;
import com.augmentis.ayp.contact_project.Model.ContactLab;

import java.io.File;
import java.util.UUID;

/**
 * Created by Theerawuth on 8/10/2016.
 */
public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "Contact";
    private static final int REQUEST_CODE = 12345;

    private FragmentActivity _fragmentActivity;
    private TextView nameContact;
    private ImageView photoContact;
    Contact _contact;
    UUID _contactId;
    int _position;
    File filePhoto;
    


    public ContactHolder(View itemView, FragmentActivity fragmentActivity) {
        super(itemView);
        Log.d(TAG, "ContactHolder: Show Name and Photo ");
        _fragmentActivity = fragmentActivity;
        nameContact = (TextView) itemView.findViewById(R.id.list_item_contact_name);
        photoContact = (ImageView) itemView.findViewById(R.id.list_item_contact_photo);

        itemView.setOnClickListener(this);
    }

    public void bind(Contact contact, int position){
        Log.d(TAG, "ContactHolder: Bind Name and Photo ");
        _contactId = contact.getId();
        _contact = contact;
        _position = position;

        nameContact.setText(contact.getName());

        ContactLab contactLab = ContactLab.getInstance(_fragmentActivity);
        filePhoto = contactLab.getPhotoFile(_contact);

        Bitmap bitmap = PictureUtils.getScaledBitmap(filePhoto.getPath(), _fragmentActivity);
        photoContact.setImageBitmap(bitmap);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Click UUID = " + _contactId);
        Intent intent = ContactNewOrEditActivity.newIntent(_fragmentActivity, _contactId);
        _fragmentActivity.startActivityForResult(intent, REQUEST_CODE);


    }
}
