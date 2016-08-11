package com.augmentis.ayp.contact_project.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.augmentis.ayp.contact_project.Model.Contact;
import com.augmentis.ayp.contact_project.Model.ContactLab;
import com.augmentis.ayp.contact_project.PictureUtils;
import com.augmentis.ayp.contact_project.R;

import java.io.File;
import java.util.UUID;

/**
 * Created by Theerawuth on 8/9/2016.
 */
public class ContactNewOrEditFragment extends Fragment {

    private static final String TAG = "Contact";
    private static final String CONTACT_ID = "contactNewOrEditActivity.contactId";
    private static final int REQUEST_CAPTURE_PHOTO = 12;

    private EditText nameEditText;
    private EditText telEdiText;
    private EditText emailEditText;
    private Button deleteButton;
    private ImageButton photoButton;
    private ImageView photoView;
    private Contact contact;
    private File mPhotoFile;
    private Callbacks callbacks;

    public static ContactNewOrEditFragment newInstance(UUID contactId) {
        
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_ID, contactId);
        ContactNewOrEditFragment contactNewOrEditFragment = new ContactNewOrEditFragment();
        contactNewOrEditFragment.setArguments(args);
        return contactNewOrEditFragment;
    }

    public interface Callbacks {
        void onContactUpdate(Contact contact);
        void onContactDelete(Contact contact);
    }

    private void deleteContact(){
        ContactLab.getInstance(getActivity()).deleteContact(contact.getId());
        callbacks.onContactDelete(contact);
    }

    private void updateContact() {
        ContactLab.getInstance(getActivity()).updateContact(contact);
        callbacks.onContactUpdate(contact);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactLab contactLab = ContactLab.getInstance(getActivity());

        UUID contactId = (UUID) getArguments().getSerializable(CONTACT_ID);
        contact = contactLab.getContactById(contactId);

        mPhotoFile = ContactLab.getInstance(getActivity()).getPhotoFile(contact);

        Log.d(TAG, "crime.getId()=" + contact.getId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Show Edit Contact Page");
        View v = inflater.inflate(R.layout.fragment_new_or_edit_contact, container, false);

        nameEditText = (EditText) v.findViewById(R.id.contact_name);
        nameEditText.setText(contact.getName());
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contact.setName(s.toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        telEdiText = (EditText) v.findViewById(R.id.contact_tel);
        telEdiText.setText(contact.getPhoneNumber());
        telEdiText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contact.setPhoneNumber(s.toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailEditText = (EditText) v.findViewById(R.id.contact_email);
        emailEditText.setText(contact.getEmail());
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contact.setEmail(s.toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        deleteButton = (Button) v.findViewById(R.id.contact_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "AlertDeleteContact");
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContactLab.getInstance(getActivity()).deleteContact(contact.getId());
                                callbacks.onContactDelete(contact);
                                callbacks.onContactUpdate(contact);
                                updateContact();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        photoButton = (ImageButton) v.findViewById(R.id.contact_camera_button);
        photoView = (ImageView) v.findViewById(R.id.contact_photo_image_view);


        // Call camera intent
        final Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Check if we can take photo ?
        boolean canTakePhoto = mPhotoFile != null
                && captureImageIntent.resolveActivity(packageManager) != null;

        if (canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            Log.d(TAG, "File output at " + mPhotoFile.getAbsolutePath());
            captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        // on click --> start activity for camera
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImageIntent, REQUEST_CAPTURE_PHOTO);
            }
        });

        updatePhotoView();

        return v;
    }

    private void updatePhotoView() {
        if(mPhotoFile == null || !mPhotoFile.exists()) {
            photoView.setImageDrawable(null);
        }
        else
        {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            photoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //update photo
        if(requestCode == REQUEST_CAPTURE_PHOTO) {
            updatePhotoView();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        ContactLab.getInstance(getActivity()).updateContact(contact); // update contact in db

    }
}
