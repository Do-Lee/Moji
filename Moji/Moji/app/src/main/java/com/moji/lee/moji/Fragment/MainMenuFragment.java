package com.moji.lee.moji.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.moji.lee.moji.MainActivity;
import com.moji.lee.moji.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.os.AsyncTask;

public class MainMenuFragment extends SherlockFragment {

	TextView textTitleBar;
	TextView yonginWeather;
	TextView seoulWeather;
	ImageView seoulImage;
	ImageView yonginImage;
	View v;
	GetXMLTask ytask, stask;
	Document doc = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.main, container, false);

		textTitleBar = (TextView) getSherlockActivity().findViewById(R.id.textTitle);
		textTitleBar.setText("모두의 명지");

		seoulWeather = (TextView) v.findViewById(R.id.seoulWeather);
		yonginWeather = (TextView) v.findViewById(R.id.yonginWeather);
		seoulImage = (ImageView) v.findViewById(R.id.seoulimageView);
		yonginImage = (ImageView) v.findViewById(R.id.yonginimageView);
		this.setSeoulWeather();
		this.setYonginWeather();

		v.findViewById(R.id.stuBtn).setOnClickListener(mClickListener);
		v.findViewById(R.id.employBtn).setOnClickListener(mClickListener);
		v.findViewById(R.id.voiceBtn).setOnClickListener(mClickListener);
		v.findViewById(R.id.eduBtn).setOnClickListener(mClickListener);

		return v;
	}
	public void setSeoulWeather() {
		stask = new GetXMLTask(this.seoulWeather, this.seoulImage);
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			stask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1141069000");
		} else {
			stask.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1141069000");
		}

	}

	public void setYonginWeather() {
		ytask = new GetXMLTask(this.yonginWeather, this.yonginImage);
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			ytask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4146152000");
		} else {
			ytask.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4146152000");
		}

	}
	private class GetXMLTask extends AsyncTask<String, Void, Document>{
		private TextView textView;
		private ImageView imageView;
		GetXMLTask(TextView textView, ImageView imageView) {
			this.textView = textView;
			this.imageView = imageView;
		}
		@Override
		protected Document doInBackground(String... urls) {
			URL url;
			try {
				url = new URL(urls[0]);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
				doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
				doc.getDocumentElement().normalize();

			} catch (Exception e) {

			}
			if(this.isCancelled()) {
				this.cancel(true);
			}
			return doc;
		}

		@Override
		protected void onPostExecute(Document doc) {

			String s = "";
			String w = "";
			//data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
			NodeList nodeList = doc.getElementsByTagName("data");
			//data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

			for(int i = 0; i < 1; i++){

				//날씨 데이터를 추출
				Node node = nodeList.item(i); //data엘리먼트 노드
				Element fstElmnt = (Element) node;

				NodeList nameList = fstElmnt.getElementsByTagName("temp");
				Element nameElement = (Element) nameList.item(0);
				nameList = nameElement.getChildNodes();
				s += "온도 : "+ ((Node) nameList.item(0)).getNodeValue() +"˚C\n";


				NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
				//<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
				w += websiteList.item(0).getChildNodes().item(0).getNodeValue();


			}
			textView.setText(s);

			if(w.equals("맑음")) {
				imageView.setImageResource(R.drawable.sunny);
			} else if(w.equals("구름 조금") || w.equals("구름 많음")) {
				imageView.setImageResource(R.drawable.partlycloudy);
			} else if(w.equals("흐림")) {
				imageView.setImageResource(R.drawable.cloudy);
			} else if(w.equals("비")) {
				imageView.setImageResource(R.drawable.rainy);
			} else if(w.equals("눈/비")) {
				imageView.setImageResource(R.drawable.rainysnowy);
			} else if(w.equals("눈")) {
				imageView.setImageResource(R.drawable.snowy);
			}
			super.onPostExecute(doc);
			if(this.getStatus() != AsyncTask.Status.FINISHED ) {
				this.cancel(true);
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			Log.i("", "close");
		}
	}
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			Fragment newContent = null;
			switch (v.getId()) {
				case R.id.stuBtn:
					newContent = new StuCouncilMenuList();
					break;
				case R.id.voiceBtn:
					newContent = new VoiceCommandFragment();
					break;
				case R.id.employBtn:
					newContent = new EmployMenuFragment();
					break;
				case R.id.eduBtn:
					newContent = new EduMenuFragment();
					break;
			}


			if(ytask.getStatus() != AsyncTask.Status.FINISHED ) {
				ytask.cancel(true);
			}
			if(stask.getStatus() != AsyncTask.Status.FINISHED ) {
				stask.cancel(true);
			}
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, newContent).addToBackStack(null).commit();
			if (newContent != null) {
				switchFragment(newContent);

			}
		}
	};
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
	}
}