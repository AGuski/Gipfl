package com.gipflstuermer.gipfl.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gipflstuermer.gipfl.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alex on 07.02.16.
 */
public class AddPoiDialogFragment extends DialogFragment {

    private Uri picUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AppCompatDialog poiDialog = new AppCompatDialog(getContext(), android.R.style.Theme_Material_Dialog);
        poiDialog.setContentView(R.layout.add_poi_dialog);

        poiDialog.getSupportActionBar();

        poiDialog.setTitle("Add New Point of Interest");
        poiDialog.setCancelable(true);


        final Button createButton = (Button) poiDialog.findViewById(R.id.create_poi_button);
        final Button cancelButton = (Button) poiDialog.findViewById(R.id.cancel_button);
        final Button takePicBtn = (Button) poiDialog.findViewById(R.id.add_photo_button);
        final EditText poiName = (EditText) poiDialog.findViewById(R.id.poi_name_editText);
        final EditText poiDesc = (EditText) poiDialog.findViewById(R.id.poi_description_editText);
        final ImageView addPoiImg = (ImageView) poiDialog.findViewById(R.id.add_poi_imageView);



        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Camera Take Picture Intent
                Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                picUri = getOutputMediaFileUri(); // create a file to save the image
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file name
                // start the image capture Intent
                startActivityForResult(takePicIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                // Anstelle von OnActivityResult
                //addPoiImg.setImageURI(picUri);


            }

        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poiName.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(R.string.add_poi_no_name_message);
                    AlertDialog noNameAlert = builder.create();
                    noNameAlert.show();
                }else {

                    poiDialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poiDialog.cancel();
            }
        });

        return poiDialog;

    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");


        return mediaFile;
    }
}
