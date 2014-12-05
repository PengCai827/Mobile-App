package com.example.hw9;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class SwipeTabFragment extends Fragment {
	private String tab;
	private int color;
	private static final String TAG = "MainFragment";
	private ImageButton shareButton;
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = true;
	private UiLifecycleHelper uiHelper;
	private String info;
	private String link;
	private String lastSoldPrice;
	private String daysOverallChange;
	private String image1year;
	// private final String htmlText =
	// "<img src='http://www-scf.usc.edu/~csci571/2014Spring/hw6/up_g.gif'>";
	private final String htmlText = "<img src=\"up_g.gif\">";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentActivity activity = getActivity();
		uiHelper = new UiLifecycleHelper(activity, null);
		uiHelper.onCreate(savedInstanceState);

	}

	TextView view2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity2 activity2 = (MainActivity2) getActivity();
		ArrayList<String> myDataFromActivity = activity2.getMyData();
		View view = inflater.inflate(R.layout.fragment_basic, container, false);

		TextView textView = (TextView) view
				.findViewById(R.id.TextViewBottomTitle);
		textView.setText(Html
				.fromHtml("<span>\u00A9 Zillow, Inc., 2006-2014 </span><br>Use is subject to"
						+ "<a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a><br/> "
						+ "<a href=\"http://www.zillow.com/zestimate\">What&apos;s a Zestmate</a><br/> "));
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		View viewFragment = inflater.inflate(R.layout.fragment_historical,
				container, false);
		TextView textView3 = (TextView) viewFragment
				.findViewById(R.id.TextViewBottomTitle2);

		textView3
				.setText(Html
						.fromHtml("<span>\u00A9 Zillow, Inc., 2006-2014 </span><br>Use is subject to"
								+ "<a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a><br/> "
								+ "<a href=\"http://www.zillow.com/zestimate\">What&apos;s a Zestmate</a><br/> "));

		textView3.setMovementMethod(LinkMovementMethod.getInstance());

		TextView hyperlink = (TextView) view.findViewById(R.id.tbl_txt3);
		info = myDataFromActivity.get(20);
		link = myDataFromActivity.get(21);
		hyperlink.setText(Html.fromHtml("<a href=\'" + link + "'>" + info
				+ "</a><br/> "));
		hyperlink.setMovementMethod(LinkMovementMethod.getInstance());

		String useCode = myDataFromActivity.get(0);
		view2 = (TextView) view.findViewById(R.id.tbl_txt6);
		view2.setText(useCode);

		String yearBuilt = myDataFromActivity.get(1);
		view2 = (TextView) view.findViewById(R.id.tbl_txt8);
		view2.setText(yearBuilt);

		String lotSize = myDataFromActivity.get(2);
		view2 = (TextView) view.findViewById(R.id.tbl_txt10);
		view2.setText(lotSize);

		String finishedArea = myDataFromActivity.get(3);
		view2 = (TextView) view.findViewById(R.id.tbl_txt12);
		view2.setText(finishedArea);

		String bathrooms = myDataFromActivity.get(4);
		view2 = (TextView) view.findViewById(R.id.tbl_txt14);
		view2.setText(bathrooms);

		String bedrooms = myDataFromActivity.get(5);
		view2 = (TextView) view.findViewById(R.id.tbl_txt16);
		view2.setText(bedrooms);

		String taxAssessmentYear = myDataFromActivity.get(6);
		view2 = (TextView) view.findViewById(R.id.tbl_txt18);
		view2.setText(taxAssessmentYear);

		String taxAssessment = myDataFromActivity.get(7);
		view2 = (TextView) view.findViewById(R.id.tbl_txt20);
		view2.setText(taxAssessment);

		lastSoldPrice = myDataFromActivity.get(8);
		view2 = (TextView) view.findViewById(R.id.tbl_txt22);
		view2.setText(lastSoldPrice);

		String lastSoldDate = myDataFromActivity.get(9);
		view2 = (TextView) view.findViewById(R.id.tbl_txt24);
		view2.setText(lastSoldDate);

		String ZestmateProperyEstimate = myDataFromActivity.get(10);
		view2 = (TextView) view.findViewById(R.id.tbl_txt26);
		view2.setText(ZestmateProperyEstimate);

		String ZestmateProperyEstimateTime = myDataFromActivity.get(22);
		// zestmateProEstimate inlcuding time
		Log.v("ZestimateProperty", ZestmateProperyEstimateTime);
		view2 = (TextView) view.findViewById(R.id.tbl_txt25);
		view2.setText(ZestmateProperyEstimateTime);

		 daysOverallChange = myDataFromActivity.get(11);// 30 days overall
		 view2 = (TextView) view.findViewById(R.id.tbl_txt28);
		// view2.setText(daysOverallChange);

		String allTimePropertyRange = myDataFromActivity.get(12);
		view2 = (TextView) view.findViewById(R.id.tbl_txt30);
		view2.setText(allTimePropertyRange);

		String allTimePropertyRangeTime = myDataFromActivity.get(23);
		view2 = (TextView) view.findViewById(R.id.tbl_txt31);
		view2.setText(allTimePropertyRangeTime);

		String rentZestimateValuation = myDataFromActivity.get(13);
		view2 = (TextView) view.findViewById(R.id.tbl_txt32);
		view2.setText(rentZestimateValuation);

		String daysRentChange = myDataFromActivity.get(14);// 30 days rent
		view2 = (TextView) view.findViewById(R.id.tbl_txt34);
		view2.setText(daysRentChange);

		String allTimeRentRange = myDataFromActivity.get(15);
		view2 = (TextView) view.findViewById(R.id.tbl_txt36);
		view2.setText(allTimeRentRange);

		image1year = myDataFromActivity.get(17);

		shareButton = (ImageButton) view.findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setMessage("Post to Facebook")
						.setPositiveButton("Post Property Details",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int item) {
										logInSession();
										Session session = Session
												.getActiveSession();
										if (session.isOpened()) {

											publishFeedDialog();
										} else {

										}
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(getActivity(),
												"Post Cancelled",
												Toast.LENGTH_SHORT).show();
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();

			}
		});

		if (savedInstanceState != null) {
			pendingPublishReauthorization = savedInstanceState.getBoolean(
					PENDING_PUBLISH_KEY, false);
		}
		String daysOverallChangeRaw = myDataFromActivity.get(24);
		int daysOverallChangeParse = Integer.parseInt(daysOverallChangeRaw);
		String htmlTextUp;
		String htmlTextDn;
		TextView htmlTextView = (TextView) view.findViewById(R.id.tbl_txt28);
		if(daysOverallChangeParse>0){
		 htmlTextUp=" <img src='up_g.png'>"+daysOverallChange;
		 
		 htmlTextView.setText(Html.fromHtml(htmlTextUp,
					imgGetter, null));
		}else{
			 htmlTextDn=" <img src='down_r.png'>"+daysOverallChange;
			 htmlTextView.setText(Html.fromHtml(htmlTextDn,
					 imgGetter2, null));
		}
		
	
		String daysRentChangeRaw = myDataFromActivity.get(25);
		int daysRentChangeRawParse = Integer.parseInt(daysRentChangeRaw);
		String htmlTextUp2;
		String htmlTextDn2;
		TextView htmlTextView2= (TextView) view.findViewById(R.id.tbl_txt34);
		if(daysRentChangeRawParse>0){
		 htmlTextUp2=" <img src='up_g.png'>"+daysRentChange;
		 htmlTextView2.setText(Html.fromHtml(htmlTextUp2,
					imgGetter, null));
		}else{
			 htmlTextDn2=" <img src='down_r.png'>"+daysRentChange;
			 htmlTextView2.setText(Html.fromHtml(htmlTextDn2,
					 imgGetter2, null));
		}
		return view;
	}

	private ImageGetter imgGetter = new ImageGetter() {

		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			try {

				drawable = getResources().getDrawable(R.drawable.new_up);
				// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				// drawable.getIntrinsicHeight());
				drawable.setAlpha(200);
				drawable.setBounds(0, 0, 40, 40);
				int iconColor = android.graphics.Color.WHITE;
				//drawable.setColorFilter( iconColor, Mode.LIGHTEN );
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("Exception thrown", e.getMessage());
			}
			return drawable;
		}
	};
	private ImageGetter imgGetter2 = new ImageGetter() {

		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			try {

				drawable = getResources().getDrawable(R.drawable.new_down);
				// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				// drawable.getIntrinsicHeight());
				drawable.setBounds(0, 0, 40, 40);
				int iconColor = android.graphics.Color.WHITE;
				//drawable.setColorFilter( iconColor, Mode.LIGHTEN );
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("Exception thrown", e.getMessage());
			}
			return drawable;
		}
	};


	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (pendingPublishReauthorization
				&& state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
			pendingPublishReauthorization = false;
			publishFeedDialog();
		} else {

		}
	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", info);
		params.putString("caption", "Property Information from Zillow.com");
		params.putString("description", "Last Sold Price: " + lastSoldPrice
				+ ", 30 Days Overall Change:" + daysOverallChange);
		params.putString("link", link);
		params.putString("picture", image1year);

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(getActivity(),
				Session.getActiveSession(), params)).setOnCompleteListener(
				new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(getActivity(),
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(
										getActivity().getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}

				}).build();
		feedDialog.show();
	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}

	public void logInSession() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
					.setCallback(callback));

		} else {
			Session.openActiveSession(getActivity(), this, true, callback);
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);

		}
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Session.getActiveSession().onActivityResult(getActivity(),
		// requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data);

	}

}
