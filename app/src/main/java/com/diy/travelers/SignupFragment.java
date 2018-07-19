package com.diy.travelers;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class SignupFragment extends Fragment implements View.OnClickListener{

    public SignupFragment() {

    }

    public Context context;
    public View rootView;
    public FloatingActionButton fbBtn;
    private ImageView profileImage;
    private String selectedImagePath;
    private static final int SELECT_PICTURE = 1;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button submitDetails;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setTitle("Signup details");
        int black= Color.BLACK;
        toolbar.setTitleTextColor(black);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        fbBtn = (FloatingActionButton)rootView.findViewById(R.id.selectImageBtn);
        profileImage= (ImageView)rootView.findViewById(R.id.profileImage);
        collapsingToolbarLayout = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsingToolbar);
        submitDetails=(Button)rootView.findViewById(R.id.submitDetails);
        collapsingToolbarLayout.setTitle("Registration");
        fbBtn.setOnClickListener(this);
        submitDetails.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        if(id==R.id.selectImageBtn){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }

        if(id==R.id.submitDetails){
            SQLiteHelper sqLiteHelper= new SQLiteHelper(context);
            sqLiteHelper.createTable("eoRegistration");
            sqLiteHelper.insertData("Abhinav","123", "abhinav@gmail.com", "shipra", "Ghaziabad");
            //sqLiteHelper.getData();
            //Toast.makeText(context, sqLiteHelper.getData()., Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                if (EasyPermissions.hasPermissions(context, galleryPermissions)) {
                    profileImage.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                } else {
                    EasyPermissions.requestPermissions(this, "Access for storage",
                            101, galleryPermissions);
                }

            }
        }

    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            Log.v("path-->", path);

            return path;
        }else {
            // this is our fallback here
            Log.v("uri Path-->", uri.getPath());

            return uri.getPath();
        }
    }


}
