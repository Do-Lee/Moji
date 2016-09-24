package com.moji.lee.moji.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.GraphRequest;
import com.moji.lee.moji.CardView.MyData;
import com.moji.lee.moji.MainActivity;
import com.moji.lee.moji.R;

import java.util.ArrayList;

public class StuCouncilMenuList extends SherlockFragment {
	TextView textTitleBar;
	ListView listView;
	View v;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.stucouncillist, container, false);
		textTitleBar = (TextView) getSherlockActivity().findViewById(R.id.textTitle);
		textTitleBar.setText("학생회 공지");
		String[] stuData = {"총학생회", "총여학생회", "공과대학", "자연과학대학", "예술체육대학", "건축대학", "동아리연합회"};


		listView = (ListView) v.findViewById(R.id.stulist);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stuData);
		listView.setAdapter(adapter);


		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View vi, int pos, long id) {
				Fragment fragment = null;
				fragment = new StuCouncilMenuFragment();
				Bundle bundle = new Bundle();
				bundle.putString("title", adapter.getItem(pos));
				switch (pos) {
					case 0:
						bundle.putString("url", "/mju43/posts");
						break;
					case 1:
						bundle.putString("url", "/31thdawon/posts");
						break;
					case 2:
						bundle.putString("url", "/mjueng33/posts");
						break;
					case 3:
						bundle.putString("url", "/mjuJayeon32/posts");
						break;
					case 4:
						bundle.putString("url", "/명지대학교-제-13대-All-예술체육대학-1072840326080530/posts");
						break;
					case 5:
						bundle.putString("url","/13camu/posts");
						break;
					case 6:
						bundle.putString("url","/sum32/posts");
						break;

				}

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
				fragment.setArguments(bundle);

				if (fragment != null) {
					switchFragment(fragment);
				}
			}
		});
		return v;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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