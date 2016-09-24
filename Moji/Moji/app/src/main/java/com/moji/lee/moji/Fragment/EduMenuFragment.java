package com.moji.lee.moji.Fragment;


import android.os.AsyncTask;
import android.os.Build;
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
import com.moji.lee.moji.CardView.MyAdapter;
import com.moji.lee.moji.CardView.MyData;
import com.moji.lee.moji.CardView.RecyclerItemClickListener;
import com.moji.lee.moji.MainActivity;
import com.moji.lee.moji.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EduMenuFragment extends SherlockFragment {

	private TextView textTitleBar;
	private View v;
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private ArrayList<MyData> myDataset;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.edu, container, false);

		textTitleBar = (TextView) getSherlockActivity().findViewById(R.id.textTitle);
		textTitleBar.setText("교육개발센터");
		mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(getSherlockActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		// specify an adapter (see also next example)
		myDataset = new ArrayList<>();
		mAdapter = new MyAdapter(myDataset);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getSherlockActivity(), new OnItemClickListener()));
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		StringBuilder jsonHtml = new StringBuilder();
		try {
			// 연결 url 설정
			URL url = new URL("http://52.78.21.188/edu.php");
			// 커넥션 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 연결되었으면.
			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				// 연결되었음 코드가 리턴되면.
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					for (; ; ) {
						// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
						String line = br.readLine();
						if (line == null) break;
						// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
						jsonHtml.append(line + "\n");
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String title, content, image;
		try {
			JSONObject root = new JSONObject(jsonHtml.toString());
			JSONArray ja = root.getJSONArray("result"); //get the JSONArray which I made in the php file. the name of JSONArray is "results"

			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				title = jo.getString("title");
				content = jo.getString("m_text");
				image = jo.getString("image");
				myDataset.add(new MyData(title.replace("제목 : ", ""), content, image));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
			bundle.putString("image", myDataset.get(position).image);
			itemFragment.setArguments(bundle);

			FragmentManager fragmentManager = getFragmentManager();

			fragmentManager.beginTransaction().replace(R.id.content_frame, itemFragment).addToBackStack(null).commit();
			if (itemFragment != null) {
				switchFragment(itemFragment);

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




}