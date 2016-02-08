package com.gipflstuermer.gipfl.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gipflstuermer.gipfl.R;

/**
 * Created by alex on 07.02.16.
 */
public class AddPoiDialogFragment extends DialogFragment {

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
        final EditText poiName = (EditText) poiDialog.findViewById(R.id.poi_name_editText);
        final EditText poiDesc = (EditText) poiDialog.findViewById(R.id.poi_description_editText);


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
}
