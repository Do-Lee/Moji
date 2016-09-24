package com.moji.lee.moji.CardView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moji.lee.moji.R;

import java.util.ArrayList;

/**
 * Created by Lee on 16. 5. 26..
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleView;
        public TextView mContentView;
        public LinearLayout lnrLayout;
        public ViewHolder(View view) {
            super(view);
            mTitleView = (TextView)view.findViewById(R.id.title);
            mContentView = (TextView)view.findViewById(R.id.content);
            lnrLayout = (LinearLayout) view.findViewById(R.id.lnrLayout);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element\
        holder.mTitleView.setText(mDataset.get(position).title);
        holder.mContentView.setText(mDataset.get(position).content);
        holder.lnrLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
               
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

