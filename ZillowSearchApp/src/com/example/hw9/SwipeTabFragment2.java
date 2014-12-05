package com.example.hw9;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class SwipeTabFragment2 extends Fragment {
	private ArrayList<String> urlList = new ArrayList<String>();
	private ArrayList<String> yearList = new ArrayList<String>();

	private TextSwitcher mTextSwitcher;
	private ImageSwitcher mImageSwitcher;
	private int pageIndex = 0;
	private String address = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	TextView view2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View viewFragment = inflater.inflate(
				R.layout.fragment_historical, container, false);
		TextView textView3 = (TextView) viewFragment
				.findViewById(R.id.TextViewBottomTitle2);

		textView3
				.setText(Html
						.fromHtml("<span>\u00A9 Zillow, Inc., 2006-2014 </span><br>Use is subject to"
								+ "<a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a><br/> "
								+ "<a href=\"http://www.zillow.com/zestimate\">What&apos;s a Zestmate</a><br/> "));

		textView3.setMovementMethod(LinkMovementMethod.getInstance());
		MainActivity2 activity2 = (MainActivity2) getActivity();
		ArrayList<String> myDataFromActivity = activity2.getMyData();
		address=myDataFromActivity.get(20);
		String url1year = myDataFromActivity.get(17);
		String url5year = myDataFromActivity.get(18);
		String url10year = myDataFromActivity.get(19);
		urlList.add(url1year);
		urlList.add(url5year);
		urlList.add(url10year);

		String firstyear = "Historical Zestimate for the past 1 year";
		String fiveyear = "Historical Zestimate for the past 5 year";
		String tenyear = "Historical Zestimate for the past 10 year";
		yearList.add(firstyear);
		yearList.add(fiveyear);
		yearList.add(tenyear);

		mImageSwitcher = (ImageSwitcher) viewFragment
				.findViewById(R.id.imageSwitcher);

		// Create an object for subclass of AsyncTask
		// Execute the task
		mImageSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				ImageView myView = new ImageView(getActivity());
				myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				myView.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				return myView;
			}

		});
		mTextSwitcher = (TextSwitcher) viewFragment
				.findViewById(R.id.textSwitcher);
		mTextSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView textView = new TextView(getActivity());
				textView.setTextSize(17);
				textView.setTypeface(null, Typeface.BOLD);

				textView.setGravity(Gravity.CENTER);
				return textView;
			}
		});

		mTextSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in);
		mTextSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out);
		mImageSwitcher.setInAnimation(getActivity(),
				android.R.anim.slide_in_left);
		mImageSwitcher.setOutAnimation(getActivity(),
				android.R.anim.slide_out_right);
		GetXMLTask task = new GetXMLTask();
		task.execute(urlList.get(0));

		mTextSwitcher.setText(yearList.get(0));

		Button mButton = (Button) viewFragment.findViewById(R.id.buttonSwitch);
		mButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pageIndex--;
				if (pageIndex == -1) {
					pageIndex = 2;
				}
				if (pageIndex == 3) {
					pageIndex = 1;
				}
				GetXMLTask task = new GetXMLTask();
				task.execute(urlList.get(pageIndex));
				mTextSwitcher.setText(yearList.get(pageIndex));
				// TextView boldTitle = (TextView) mTextSwitcher.getChildAt(0);
				// boldTitle.setTypeface(null, Typeface.BOLD);
			}
		});
		Button mButton2 = (Button) viewFragment
				.findViewById(R.id.buttonSwitch2);
		mButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pageIndex++;
				if (pageIndex == 3) {
					pageIndex = 0;
				}
				if (pageIndex == -1) {
					pageIndex = 1;
				}
				GetXMLTask task = new GetXMLTask();
				task.execute(urlList.get(pageIndex));
				mTextSwitcher.setText(yearList.get(pageIndex));
				// TextView boldTitle = (TextView) mTextSwitcher.getChildAt(0);
				// boldTitle.setTypeface(null, Typeface.BOLD);

			}
		});
		TextView addressView = (TextView) viewFragment.findViewById(R.id.address);
		addressView.setText(address);
		
		return viewFragment;
	}

	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap map = null;
			for (String url : urls) {
				map = downloadImage(url);
			}
			return map;
		}

		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			Drawable drawImge = new BitmapDrawable(getResources(), result);
			mImageSwitcher.setImageDrawable(drawImge);

		}

		// Creates Bitmap from InputStream and returns it
		private Bitmap downloadImage(String url) {
			Bitmap bitmap = null;
			InputStream stream = null;
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;

			try {
				stream = getHttpConnection(url);
				bitmap = BitmapFactory.decodeStream(stream, null, null);
				stream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			return bitmap;
		}

		// Makes HttpURLConnection and returns InputStream
		private InputStream getHttpConnection(String urlString)
				throws IOException {
			InputStream stream = null;
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			try {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return stream;
		}

	}

}
