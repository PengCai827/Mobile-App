package com.example.hw9;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
			
	TextView output;
	List<MyTask> tasks;
	Button button;
	JSONObject reader = null;
	public static TextView textview;;
	public String addressGet = null;
	public String cityGet = null;
	public String stateGet = null;
	String content = "";
	String useCode = "";
	String yearBuilt = "";
	Activity thisActivity = this;
	private static final String TAG = "MainActivity";
	ArrayList<String> sendPool = new ArrayList<String>();

	public static Intent intent = new Intent();
	ImageButton imageButton;
	public int checkCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// output = (TextView) findViewById(R.id.textView);// Output JSON
		//
		// output.setMovementMethod(new ScrollingMovementMethod());
		//
		tasks = new ArrayList<>();

		intent = new Intent(this, MainActivity2.class);
		TextView empty1 = (TextView) findViewById(R.id.empty1);
		empty1.setVisibility(View.GONE);

		TextView empty2 = (TextView) findViewById(R.id.empty2);
		empty2.setVisibility(View.GONE);

		TextView empty3 = (TextView) findViewById(R.id.empty3);
		empty3.setVisibility(View.GONE);

		TextView warning = (TextView) findViewById(R.id.warning);
		warning.setVisibility(View.GONE);

		addListenerOnButton();

	}

	public void addListenerOnButton() {

		Button button = (Button) findViewById(R.id.btnPost);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView empty1 = (TextView) findViewById(R.id.empty1);
				TextView empty2 = (TextView) findViewById(R.id.empty2);
				TextView empty3 = (TextView) findViewById(R.id.empty3);
				boolean flag=true;
				EditText address = (EditText) findViewById(R.id.address);
				addressGet = address.getText().toString().trim();
				if (addressGet.matches("")) {
					empty1.setVisibility(View.VISIBLE);
					flag=false;
				}
				EditText city = (EditText) findViewById(R.id.city);
				cityGet = city.getText().toString().trim();
				if (cityGet.matches("")) {
					empty2.setVisibility(View.VISIBLE);
					flag=false;

				}
				Spinner state = (Spinner) findViewById(R.id.state);
				stateGet = state.getSelectedItem().toString();
				if (stateGet.matches("Choose State")) {
					empty3.setVisibility(View.VISIBLE);
					flag=false;

				}
				if(flag){
					requestData("http://default-environment-jnwuiqppmd.elasticbeanstalk.com/");

				}

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_refresh) {
			if (isOnline()) {
				Intent intent = getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				startActivity(intent);

				// requestData("http://default-environment-jnwuiqppmd.elasticbeanstalk.com/");
			} else {
				Toast.makeText(this, "Network isn't available",
						Toast.LENGTH_LONG).show();
			}
		}
		return false;
	}

	private void requestData(String uri) {
		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);
		 p.setParam("address", addressGet);
		 p.setParam("city", cityGet);
		 p.setParam("state",stateGet);
		// Log.v("EditText..", addressGet);
		// Log.v("EditText..", cityGet);
		// Log.v("EditText..", stateGet);
//		p.setParam("address", "2636 Menlo Ave");
//		p.setParam("city", "Los Angeles");
//		p.setParam("state", "CA");

		MyTask task = new MyTask();
		task.execute(p);
	}

	protected void updateDisplay(String result) {
		// output.append(result + "\n");
	}

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	private class MyTask extends AsyncTask<RequestPackage, String, String> {

		@Override
		protected void onPreExecute() {
			if (tasks.size() == 0) {
			}
			tasks.add(this);
		}

		@Override
		protected String doInBackground(RequestPackage... params) {
			content = HttpManager.getData(params[0]);

			// try parse the string to a JSON object
			try {
				reader = new JSONObject(content);

				TextView warning = (TextView) findViewById(R.id.warning);
				// warning.setVisibility(View.VISIBLE);

				String check = reader.getJSONObject("message")
						.getString("code");
				int checkInt = Integer.parseInt(check);
				checkCode = checkInt;
				if (checkInt != 0) {

					return null;
				} else {
					String[] arrMonths = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec" };
					useCode = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("useCode");
					yearBuilt = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("yearBuilt");

					String lotSizeRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("lotSizeSqFt");
					double lotSizeDouble = Double.parseDouble(lotSizeRaw);
					String lotSize = String.format("%,.2f", lotSizeDouble)
							+ " sq.ft";

					String finishedAreaRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("finishedSqFt");
					double finishedAreaDouble = Double
							.parseDouble(finishedAreaRaw);
					String finishedArea = String.format("%,.2f",
							finishedAreaDouble) + " sq.ft";

					String bathroomsRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("bathrooms");
					double bathroomsDouble = Double.parseDouble(bathroomsRaw);
					String bathrooms = String.format("%,.2f", bathroomsDouble);

					String bedroomsRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("bedrooms");
					double bedroomsDouble = Double.parseDouble(bedroomsRaw);
					String bedrooms = String.format("%,.2f", bedroomsDouble);

					String taxAssessmentYear = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("taxAssessmentYear");

					String taxAssessmentRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("taxAssessment");
					double taxAssessmentDouble = Double
							.parseDouble(taxAssessmentRaw);
					String taxAssessment = "$"
							+ String.format("%,.2f", taxAssessmentDouble);

					String lastSoldPriceRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("lastSoldPrice");
					double lastSoldPriceDouble = Double
							.parseDouble(lastSoldPriceRaw);
					String lastSoldPrice = "$"
							+ String.format("%,.2f", lastSoldPriceDouble);

					String lastSoldDateRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("lastSoldDate");

					String[] soldTokens = null;
					for (int i = 0; i < 3; i++) {
						soldTokens = lastSoldDateRaw.split("/");
					}
					int soldMonth = Integer.parseInt(soldTokens[0]);
					int soldDay = Integer.parseInt(soldTokens[1]);
					int soldYear = Integer.parseInt(soldTokens[2]);
					String soldMonthName = arrMonths[soldMonth - 1];
					String soldTimeName = soldDay + " - " + soldMonthName + " - " + soldYear;
					String lastSoldDate=soldTimeName;
					
					
					String ZestmateProperyEstimateRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result").getJSONObject("zestimate")
							.getString("amount");
					double ZestmateProperyEstimateDouble = Double
							.parseDouble(ZestmateProperyEstimateRaw);
					String ZestmateProperyEstimate = "$"
							+ String.format("%,.2f",
									ZestmateProperyEstimateDouble);

					/**
					 * ZestmateProEstimateTime
					 */

					String ZestmateProperyEstimateTimeRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result").getJSONObject("zestimate")
							.getString("last-updated");
					String[] tokens = null;

					for (int i = 0; i < 3; i++) {
						tokens = ZestmateProperyEstimateTimeRaw.split("/");
					}
					int month = Integer.parseInt(tokens[0]);
					int day = Integer.parseInt(tokens[1]);
					int year = Integer.parseInt(tokens[2]);
					String monthName = arrMonths[month - 1];
					String timeName = day + " - " + monthName + " - " + year;
					String cellTitle = "Zestimate\u00AE Property Estimate as of\n "
							+ timeName;

					String daysOverallChangeRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result").getJSONObject("zestimate")
							.getString("valueChange");
					double daysOverallChangeDouble = Double
							.parseDouble(daysOverallChangeRaw);
					String daysOverallChange = "$"
							+ String.format("%,.2f", daysOverallChangeDouble);

					String allTimePropertyRangeLowRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result").getJSONObject("zestimate")
							.getJSONObject("valuationRange").getString("low");
					double allTimePropertyRangeLowDouble = Double
							.parseDouble(allTimePropertyRangeLowRaw);
					String allTimePropertyRangeLow = "$"
							+ String.format("%,.2f",
									allTimePropertyRangeLowDouble);

					String allTimePropertyRangeHighRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result").getJSONObject("zestimate")
							.getJSONObject("valuationRange").getString("high");
					double allTimePropertyRangeHighDouble = Double
							.parseDouble(allTimePropertyRangeHighRaw);
					String allTimePropertyRangeHigh = "$"
							+ String.format("%,.2f",
									allTimePropertyRangeHighDouble);
					String allTimePropertyRange = allTimePropertyRangeLow
							+ " - " + allTimePropertyRangeHigh;

					String rentZestimateValuationRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result")
							.getJSONObject("rentzestimate").getString("amount");
					double rentZestimateValuationDouble = Double
							.parseDouble(rentZestimateValuationRaw);
					String rentZestimateValuation = "$"
							+ String.format("%,.2f",
									rentZestimateValuationDouble);

					String daysRentChangeRaw = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("rentzestimate")
							.getString("valueChange");
					double daysRentChangeDouble = Double
							.parseDouble(daysRentChangeRaw);
					String daysRentChange = "$"
							+ String.format("%,.2f", daysRentChangeDouble);

					String allTimeRentRangeLowRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result")
							.getJSONObject("rentzestimate")
							.getJSONObject("valuationRange").getString("low");
					double allTimeRentRangeLowDouble = Double
							.parseDouble(allTimeRentRangeLowRaw);
					String allTimeRentRangeLow = "$"
							+ String.format("%,.2f", allTimeRentRangeLowDouble);

					String allTimeRentRangeHighRaw = reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result")
							.getJSONObject("rentzestimate")
							.getJSONObject("valuationRange").getString("high");
					double allTimeRentRangeHighDouble = Double
							.parseDouble(allTimeRentRangeHighRaw);
					String allTimeRentRangeHigh = "$"
							+ String.format("%,.2f", allTimeRentRangeHighDouble);
					String allTimeRentRange = allTimeRentRangeLow + " - "
							+ allTimeRentRangeHigh;

					String logo = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("GraphT");

					String url1year = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("Graph_1");
					String url3year = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("Graph_2");
					String url5year = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getString("Graph_3");

					String street = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("address").getString("street");
					String city = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("address").getString("city");
					String state = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("address").getString("state");
					String zip = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("address").getString("zipcode");
					String hyperlink = street + "," + city + "," + state + "-"
							+ zip;
					String link = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("links").getString("homedetails");
					String rentZestimateValuationTime= reader
							.getJSONObject("response").getJSONObject("results")
							.getJSONObject("result")
							.getJSONObject("rentzestimate").getString("last-updated");
					
					String[] Renttokens = null;

					for (int i = 0; i < 3; i++) {
						Renttokens = ZestmateProperyEstimateTimeRaw.split("/");
					}
					int rentmonth = Integer.parseInt(Renttokens[0]);
					int rentday = Integer.parseInt(Renttokens[1]);
					int rentyear = Integer.parseInt(Renttokens[2]);
					String rentmonthName = arrMonths[rentmonth - 1];
					String renttimeName = rentday + " - " + rentmonthName + " - " + rentyear;
					String rentcellTitle = "Rent Zestimate\u00AE Valuation as of\n"
							+ renttimeName;

					
//					
//					String low = reader.getJSONObject("response")
//							.getJSONObject("results").getJSONObject("result")
//							.getJSONObject("zestimate")
//							.getJSONObject("valuationRange").getString("low");

					String valueChange = reader.getJSONObject("response")
							.getJSONObject("results").getJSONObject("result")
							.getJSONObject("rentzestimate")
							.getString("valueChange");
					int valueChangeParse = Integer.parseInt(valueChange);

					intent = new Intent(thisActivity, MainActivity2.class);
					sendPool.add(useCode);
					sendPool.add(yearBuilt);
					sendPool.add(lotSize);
					sendPool.add(finishedArea);
					sendPool.add(bathrooms);
					sendPool.add(bedrooms);
					sendPool.add(taxAssessmentYear);
					sendPool.add(taxAssessment);
					sendPool.add(lastSoldPrice);
					sendPool.add(lastSoldDate);
					sendPool.add(ZestmateProperyEstimate);
					sendPool.add(daysOverallChange);
					sendPool.add(allTimePropertyRange);
					sendPool.add(rentZestimateValuation);
					sendPool.add(daysRentChange);
					sendPool.add(allTimeRentRange);
					sendPool.add(logo);
					sendPool.add(url1year);//17
					sendPool.add(url3year);
					sendPool.add(url5year);
					sendPool.add(hyperlink);
					sendPool.add(link);
					sendPool.add(cellTitle);// 22
					sendPool.add(rentcellTitle);//23
					sendPool.add(daysOverallChangeRaw);//24,daysOverallChangeRaw
					sendPool.add(daysRentChangeRaw);//25,daysRentChangeRaw
					intent.putStringArrayListExtra("Value1", sendPool);
					startActivity(intent);
					return useCode;
					// return useCode;
				}
			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
			return useCode;

		}

		@Override
		protected void onPostExecute(String result) {
			if (checkCode != 0) {
				TextView warning = (TextView) findViewById(R.id.warning);

				warning.setVisibility(View.VISIBLE);
			}
			tasks.remove(this);
			if (tasks.size() == 0) {
			}

			updateDisplay(result);

		}

	}
}