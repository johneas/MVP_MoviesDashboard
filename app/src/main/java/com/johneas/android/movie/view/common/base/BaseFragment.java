package com.johneas.android.movie.view.common.base;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.johneas.android.movie.view.main.MainActivity;


public class BaseFragment extends Fragment {

    public void displayMessage(String message) {
        getActivity().onBackPressed();
        ((MainActivity) getActivity()).displayMessage(message);
    }

    public void setTitle(@StringRes int title){
        getActivity().setTitle(title);
    }

    public void setTitle(String title){
        getActivity().setTitle(title);
    }

    public void printLog(String TAG, String message) {
        Log.e(TAG, message);
    }
}
