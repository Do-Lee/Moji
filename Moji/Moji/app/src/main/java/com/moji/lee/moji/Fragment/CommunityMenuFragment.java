package com.moji.lee.moji.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.moji.lee.moji.CardView.MyAdapter;
import com.moji.lee.moji.CardView.MyData;
import com.moji.lee.moji.CardView.RecyclerItemClickListener;
import com.moji.lee.moji.MainActivity;
import com.moji.lee.moji.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityMenuFragment extends SherlockFragment {

    TextView textTitleBar;
    View v;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    private GraphRequest graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.stucouncil, container, false);
        textTitleBar = (TextView) getSherlockActivity().findViewById(R.id.textTitle);
        textTitleBar.setText("명지대학교 대나무 숲");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getSherlockActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getSherlockActivity(), new OnItemClickListener()));

        FacebookSdk.sdkInitialize(getSherlockActivity().getApplicationContext());
        // "https://graph.facebook.com/mju43/posts?access_token=999899993410862|w_SSgEq8_xzllhJNBtn8axMsCeY"
        //FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);

        Bundle paramPage = new Bundle();
        paramPage.putString("access_token", "999899993410862|w_SSgEq8_xzllhJNBtn8axMsCeY");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        graph = new GraphRequest(
                null, "/mjubamboo/posts", paramPage, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        String id = null;
                        FacebookRequestError error = response.getError();
                        String strTime = null;
                        String strMsg = null;
                        String title = null;

                        if (error != null) {
                            Log.e("Error", error.getErrorMessage());
                        } else {

                            JSONObject objData = response.getJSONObject();
                            JSONArray dataArray = null;

                            try {
                                dataArray = objData.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject data = dataArray.getJSONObject(i);
                                        strTime = data.optString("created_time").toString();
                                        title = strTime.substring(0,10) + " " + strTime.substring(11,16);
                                        strMsg = data.optString("message").toString();
                                        myDataset.add(new MyData(title, strMsg));
                                    }

                                } else {
                                    System.out.println("dataArray is null");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        graph.executeAndWait();
        return v;
    }
    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(View childView, int position) {
            Fragment itemFragment = null;
            itemFragment = new ItemFragment();

            Bundle bundle = new Bundle();
            bundle.putString("title", myDataset.get(position).title);
            bundle.putString("content", myDataset.get(position).content);
            itemFragment.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.content_frame, itemFragment).addToBackStack(null).commit();
            if (itemFragment != null) {
                switchFragment(itemFragment);
            }
        }

    }
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;
        if (getActivity() instanceof MainActivity) {
            MainActivity fca = (MainActivity) getActivity();
            fca.switchContent(fragment);
        }
    }

}