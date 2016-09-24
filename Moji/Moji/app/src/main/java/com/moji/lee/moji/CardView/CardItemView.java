package com.moji.lee.moji.CardView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.moji.lee.moji.R;

public class CardItemView extends SherlockFragment {

    TextView mTitleView, mContentView;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.card_item, container, false);


        return v;
    }

}
