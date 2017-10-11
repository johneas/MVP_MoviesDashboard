package com.johneas.android.movie.view.common.base;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.johneas.android.movie.R;

import butterknife.BindView;


public class BaseActivity extends AppCompatActivity {

    private Snackbar snackbar;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void displayMessage(String message){

        snackbar = Snackbar.make(coordinator_layout,
                message, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}
