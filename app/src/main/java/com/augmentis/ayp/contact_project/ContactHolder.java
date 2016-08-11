package com.augmentis.ayp.contact_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.contact_project.Fragment.ContactListFragment;
import com.augmentis.ayp.contact_project.Fragment.ContactNewOrEditFragment;
import com.augmentis.ayp.contact_project.Model.Contact;
import com.augmentis.ayp.contact_project.Model.ContactLab;

import java.io.File;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by Theerawuth on 8/10/2016.
 */
public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "Contact";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private FragmentActivity _fragmentActivity;
    private TextView nameContact;
    private ImageView photoContact;
    private ContactListFragment.Callbacks _callbacks;
    Contact _contact;
    UUID _contactId;
    int _position;
    File filePhoto;


    public ContactHolder(View itemView, FragmentActivity fragmentActivity, final ContactListFragment.Callbacks callbacks) {
        super(itemView);
        Log.d(TAG, "ContactHolder: Show Name and Photo ");
        _fragmentActivity = fragmentActivity;
        _callbacks = callbacks;
        nameContact = (TextView) itemView.findViewById(R.id.list_item_contact_name);
        photoContact = (ImageView) itemView.findViewById(R.id.list_item_contact_photo);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bind(Contact contact, int position) {
        Log.d(TAG, "ContactHolder: Bind Name and Photo ");
        _contactId = contact.getId();
        _contact = contact;
        _position = position;
        nameContact.setText(contact.getName());

        ContactLab contactLab = ContactLab.getInstance(_fragmentActivity);
        filePhoto = contactLab.getPhotoFile(_contact);

        if(filePhoto == null || !filePhoto.exists()){
            photoContact.setImageDrawable(null);
        }
        else
        {
            Bitmap bitmap = PictureUtils.getScaledBitmap(filePhoto.getPath(), _fragmentActivity);
            photoContact.setImageBitmap(getRoundedShape(bitmap));
        }
    }

    @Override
    public void onClick(View v) {

        if (hasCallPermission()) {
            callSuspect();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        _callbacks.onContactSelected(_contact);
        return true;
    }

    private void callSuspect() {
        Intent i = new Intent(Intent.ACTION_CALL);
        StringTokenizer tokenizer = new StringTokenizer(_contact.getPhoneNumber(), ":");
        String phone = tokenizer.nextToken();
        Log.d(TAG, "calling " + phone);
        i.setData(Uri.parse("tel: " + phone));
        if (hasCallPermission())
            _fragmentActivity.startActivity(i);
    }

    private boolean hasCallPermission() {

        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(_fragmentActivity,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _fragmentActivity.requestPermissions(
                        new String[]{
                                Manifest.permission.CALL_PHONE
                        },
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }

            return false; //checking -- wait for dialog

        }

        return true; // already
    }

    //////////////////////////////////////set picture Round  ///////////////////////////////////////
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 500;
        int targetHeight = 500;

        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;

    }
//////////////////////////////////////////////////////////////////////////////////////////////////
}
