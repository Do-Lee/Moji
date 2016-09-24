package com.moji.lee.moji.Fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.moji.lee.moji.MainActivity;
import com.moji.lee.moji.R;

public class MenuFragment extends SherlockFragment {

	ListView listView;
	ArrayList<String> alItems;

	public MenuFragment() {
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		String[] data = {"메인", "학생회 공지", "취업&경력개발", "교육개발센터", "커뮤니티", "음성인식"};

		View v = inflater.inflate(R.layout.menu_frame, container, false);


		// 기본 변수 선언
		listView = (ListView) v.findViewById(R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
		listView.setAdapter(adapter);


		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View vi, int pos, long id) {
				Fragment newContent = null;
				switch (pos) {
					case 0:
						newContent = new MainMenuFragment();
						break;
					case 1:
						newContent = new StuCouncilMenuList();
						break;
					case 2:
						newContent = new EmployMenuFragment();
						break;
					case 3:
						newContent = new EduMenuFragment();
						break;
					case 4:
						newContent = new CommunityMenuFragment();
						break;
					case 5:
						newContent = new VoiceCommandFragment();
						break;
				}
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, newContent).addToBackStack(null).commit();
				if (newContent != null) {
					switchFragment(newContent);
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