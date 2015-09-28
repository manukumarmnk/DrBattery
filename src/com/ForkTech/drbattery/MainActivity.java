package com.ForkTech.drbattery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IntentFilter mIntentFilter;

	private TextView mStatus, mLevel, mScale;
	private TextView mHealth;
	private TextView mVoltage;
	private TextView mTemperature;
	private TextView mTechnology;
	private TextView mDockStatus;
	private TextView mDockLevel;
	private TextView mDockLastConnected;
	private TextView mLastCharged;
	private TextView mLevevel;
	private Button current,voltage,temp;
	private TableRow mRowDockLevel, mRowDockStatus, mRowDockLastConnected;
	ImageView batteryLevel;

	private boolean tempUnitsC;
	private int temperature, appWidgetId;

	// private XYSeries mMainSeries, mDockSeries;
	// private GraphicalView mChartView;
	private LinearLayout mChartContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battery_info_table);
		mStatus = (TextView) findViewById(R.id.status);
		batteryLevel = (ImageView) findViewById(R.id.image);
		mLevel = (TextView) findViewById(R.id.level);
		mLevevel = (TextView) findViewById(R.id.levevel);
		mScale = (TextView) findViewById(R.id.scale);
		mHealth = (TextView) findViewById(R.id.health);
		mVoltage = (TextView) findViewById(R.id.voltage);
		mTemperature = (TextView) findViewById(R.id.temperature);
		mTechnology = (TextView) findViewById(R.id.technology);
		mDockStatus = (TextView) findViewById(R.id.dock_status);
		mDockLevel = (TextView) findViewById(R.id.dock_level);
		mDockLastConnected = (TextView) findViewById(R.id.dock_last_connected);
		mLastCharged = (TextView) findViewById(R.id.last_charged);
		mRowDockLevel = (TableRow) findViewById(R.id.row_dock_level);
		mRowDockStatus = (TableRow) findViewById(R.id.row_dock_status);
		mRowDockLastConnected = (TableRow) findViewById(R.id.row_dock_lastConnected);
		current = (Button)findViewById(R.id.button1);
		voltage = (Button)findViewById(R.id.button2);
		temp = (Button)findViewById(R.id.button3);
//		String s= "Hello Everyone";
//		 SpannableString ss1=  new SpannableString(s);
//		 ss1.setSpan(new RelativeSizeSpan(2f), 0,8, 0); // set size
//		 ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 8, 0);// set color
//		 TextView tv= (TextView) findViewById(R.id.textView1);
//		 tv.setText(ss1); 
		if (mChartContainer != null)
			mChartContainer.removeAllViews();
		mChartContainer = (LinearLayout) findViewById(R.id.chart);

		findViewById(R.id.batterySummary).setOnClickListener(
				batterySummaryListener);
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//
//		BatteryManager bat = (BatteryManager) getSystemService(BATTERY_SERVICE);
//		bat.
//		String a = bat.EXTRA_LEVEL;
//		Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(batteryReceiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		registerReceiver(batteryReceiver, mIntentFilter);
		// buildChart();
	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// inflater.inflate(R.menu.widget_batteryinfo, menu);
	// }
	//
	// @Override
	// public void onPrepareOptionsMenu(Menu menu) {
	// super.onPrepareOptionsMenu(menu);
	// menu.findItem(R.id.temperature).setTitle(getMenuTitle());
	// }

	private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {

				int level = intent.getIntExtra("level", 0);
				level = level / 10;
				level = level * 10;
				String uri = "batt_" + level + "_90deg";

				int imageResource = getResources().getIdentifier(uri,
						"drawable", getPackageName());
				
				Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.batt_shape_90deg);
				Bitmap mBitmap2 = BitmapFactory.decodeResource(getResources(),
						imageResource);
				Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
						mBitmap.getHeight(), mBitmap.getConfig());
				Canvas canvas = new Canvas();
				canvas.setBitmap(bmOverlay);
				canvas.drawBitmap(mBitmap, new Matrix(), null);
				canvas.drawBitmap(mBitmap2, new Matrix(), null);
				batteryLevel.setImageBitmap(bmOverlay);

				//
				// // imageview= (ImageView)findViewById(R.id.imageView);
//				Drawable res = getResources().getDrawable(imageResource);
//				batteryLevel.setImageDrawable(res);
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_+"level"));
				//
				// switch (level) {
				// case 0:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_0));
				// case 10:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_10));
				// case 20:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_20_90deg));
				// case 30:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_30_90deg));
				// case 40:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_40_90deg));
				// case 50:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_50_90deg));
				// case 60:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_60_90deg));
				// case 70:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_70_90deg));
				// case 80:
				// batteryLevel.setImageDrawable(getDrawable(R.drawable.batt_80_90deg));
				//
				// break;
				//
				// default:
				// break;
				// }
				mLevel.setText("" + intent.getIntExtra("level", 0));

				mLevevel.setText("" + intent.getIntExtra("level", 0)+"%");
				mScale.setText("" + intent.getIntExtra("scale", 0));
				int voltage = intent.getIntExtra("voltage", 0);
				int voltageRes = voltage > 1000 ? R.string.battery_info_voltage_units_mV
						: R.string.battery_info_voltage_units_V;
				mVoltage.setText("" + voltage + " "
						+ context.getString(voltageRes));
				temperature = intent.getIntExtra("temperature", 0);
				updateTemperature();
				mTechnology.setText("" + intent.getStringExtra("technology"));

				int status = intent.getIntExtra("status",
						BatteryManager.BATTERY_STATUS_UNKNOWN);
				switch (status) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					String statusString = context
							.getString(R.string.battery_info_status_charging);
					int plugType = intent.getIntExtra("plugged", 0);
					if (plugType > 0) {
						statusString = statusString
								+ " "
								+ context
										.getString((plugType == BatteryManager.BATTERY_PLUGGED_AC) ? R.string.battery_info_status_charging_ac
												: R.string.battery_info_status_charging_usb);
					}
					mStatus.setText(statusString);
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					mStatus.setText(R.string.battery_info_status_discharging);
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					mStatus.setText(R.string.battery_info_status_not_charging);
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					mStatus.setText(R.string.battery_info_status_full);
					break;
				default:
					mStatus.setText(R.string.unknown);
					break;
				}

				switch (intent.getIntExtra("health",
						BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
				case BatteryManager.BATTERY_HEALTH_GOOD:
					mHealth.setText(R.string.battery_info_health_good);
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					mHealth.setText(R.string.battery_info_health_overheat);
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					mHealth.setText(R.string.battery_info_health_dead);
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					mHealth.setText(R.string.battery_info_health_over_voltage);
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					mHealth.setText(R.string.battery_info_health_unspecified_failure);
					break;
				default:
					mHealth.setText(R.string.unknown);
					break;
				}

				String lastCharged;
				if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
					lastCharged = "--";
				}
				// else if (BatteryLevel.lastCharged == null) {
				lastCharged = context.getString(R.string.unknown);
				// } else {
				// lastCharged =
				// DateFormat.getDateTimeInstance().format(BatteryLevel.lastCharged);
				// }
				mLastCharged.setText(lastCharged);

				if (intent.hasExtra("dock_status")) {
					mRowDockLevel.setVisibility(View.VISIBLE);
					mDockLevel
							.setText("" + intent.getIntExtra("dock_level", 0));

					mRowDockStatus.setVisibility(View.VISIBLE);
					int dockStatus = intent.getIntExtra("dock_status",
							Constants.DOCK_STATE_UNKNOWN);
					switch (dockStatus) {
					case Constants.DOCK_STATE_UNDOCKED:
						mDockStatus
								.setText(R.string.battery_info_dock_status_undocked);
						break;
					case Constants.DOCK_STATE_DOCKED:
						mDockStatus
								.setText(R.string.battery_info_dock_status_docked);
						break;
					case Constants.DOCK_STATE_CHARGING:
						mDockStatus
								.setText(R.string.battery_info_dock_status_charging);
						break;
					case Constants.DOCK_STATE_DISCHARGING:
						mDockStatus
								.setText(R.string.battery_info_dock_status_discharging);
						break;
					default:
						mDockStatus.setText(R.string.unknown);
						break;
					}

					mRowDockLastConnected.setVisibility(View.VISIBLE);
					String dockLastConnected;
					if (dockStatus >= Constants.DOCK_STATE_CHARGING) {
						dockLastConnected = "--";
					}
					// else if (BatteryLevel.dockLastConnected == null) {
					dockLastConnected = context.getString(R.string.unknown);
					// } else {
					// dockLastConnected =
					// DateFormat.getDateTimeInstance().format(BatteryLevel.dockLastConnected);
					// }
					mDockLastConnected.setText(dockLastConnected);
				}
			}
		}
	};

	public final OnClickListener batterySummaryListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent i = new Intent("android.intent.action.POWER_USAGE_SUMMARY");
			startActivity(i);
		}
	};

	/**
	 * Format a number of tenths-units as a decimal string without using a
	 * conversion to float. E.g. 347 -> "34.7"
	 * 
	 * @param x
	 *            a whole number
	 * @return x divided by 10, formatted
	 */
	private String tenthsToFixedString(int x) {
		int tens = x / 10;
		return tens + "." + (x - 10 * tens);
	}

	public int getMenuTitle() {
		return tempUnitsC ? R.string.battery_info_temperature_units_c
				: R.string.battery_info_temperature_units_f;
	}

	public void updateTemperature() {
		int tempVal = temperature;
		if (!tempUnitsC)
			tempVal = tempVal * 9 / 5 + 320;
		mTemperature
				.setText(tenthsToFixedString(tempVal)
						+ getString(tempUnitsC ? R.string.battery_info_temperature_units_c
								: R.string.battery_info_temperature_units_f));
	}

	public boolean onMenuItemSelected(MenuItem item) {
		Log.d(Constants.LOG, "menu id: " + item.getItemId());
		if (item.getItemId() == R.id.temperature) {
			tempUnitsC = !tempUnitsC;
			invalidateOptionsMenu();
			updateTemperature();
			// WidgetSettingsContainer.setTempUnits(this, appWidgetId,
			// tempUnitsC);
			return true;
		}
		return false;
	}

	// public void initChart() {
	// if (mChartView == null) {
	// XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	// mRenderer.setAxisTitleTextSize(16);
	// mRenderer.setChartTitleTextSize(20);
	// mRenderer.setLabelsTextSize(15);
	// mRenderer.setLegendTextSize(15);
	// mRenderer.setMargins(new int[]{20, 30, 15, 0});
	// mRenderer.setYAxisMin(0);
	// mRenderer.setYAxisMax(100);
	// mRenderer.setPanEnabled(true, false);
	// mRenderer.setZoomEnabled(true, false);
	// mRenderer.setShowGrid(true);
	// mRenderer.setZoomButtonsVisible(false);
	//
	// XYMultipleSeriesDataset mDataSet = new XYMultipleSeriesDataset();
	// mMainSeries = new XYSeries(getString(R.string.battery_main));
	// mDataSet.addSeries(mMainSeries);
	// XYSeriesRenderer mMainRenderer = new XYSeriesRenderer();
	// mMainRenderer.setColor(Color.GREEN);
	// mRenderer.addSeriesRenderer(mMainRenderer);
	//
	// if (BatteryLevel.getCurrent().is_dockFriendly()) {
	// mDockSeries = new XYSeries(getString(R.string.battery_dock));
	// mDataSet.addSeries(mDockSeries);
	// XYSeriesRenderer mDockRenderer = new XYSeriesRenderer();
	// mDockRenderer.setColor(Color.CYAN);
	// mRenderer.addSeriesRenderer(mDockRenderer);
	// }
	//
	// mChartView = ChartFactory.getTimeChartView(getActivity(), mDataSet,
	// mRenderer, null);
	// }
	// }

	private boolean chartPopulated = false;
	// public void buildChart() {
	// initChart();
	// if (mChartContainer.getChildCount() == 0) {
	// mChartContainer.addView(mChartView, new
	// ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
	// ViewGroup.LayoutParams.FILL_PARENT));
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	// mChartContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	// }
	//
	// if (chartPopulated) {
	// mChartView.repaint();
	// } else {
	// // populate chart
	// chartPopulated = true;
	// //new Thread(new Runnable() {
	// // @Override
	// // public void run() {
	// BatteryLevelAdapter adapter = new BatteryLevelAdapter(getActivity());
	// adapter.openRead();
	// Cursor c = adapter.getRecentEntries(7);
	// int oldLevel = -1, oldDockLevel = -1;
	// boolean dockSupported = BatteryLevel.getCurrent().is_dockFriendly();
	//
	// long time = System.currentTimeMillis();
	// boolean mainSkipped = false, dockSkipped = false;
	// if (c.moveToFirst())
	// do {
	// time = c.getLong(BatteryLevelAdapter.ORD_TIME);
	// int level = c.getInt(BatteryLevelAdapter.ORD_LEVEL);
	// int dock_status = c.getInt(BatteryLevelAdapter.ORD_DOCK_STATUS);
	// int dock_level = c.getInt(BatteryLevelAdapter.ORD_DOCK_LEVEL);
	//
	// mainSkipped = level == oldLevel;
	// if (!mainSkipped) {
	// mMainSeries.add(time, level);
	// oldLevel = level;
	// }
	// if (dockSupported && dock_status > 1) {
	// dockSkipped = dock_level == oldDockLevel;
	// if (!dockSkipped) {
	// mDockSeries.add(time, dock_level);
	// oldDockLevel = dock_level;
	// }
	// }
	// } while (c.moveToNext());
	// c.close();
	// adapter.close();
	// if (mainSkipped)
	// mMainSeries.add(time, oldLevel);
	// if (dockSkipped)
	// mDockSeries.add(time, oldDockLevel);
	//
	// mChartView.repaint();
	// // }
	// //}).start();
	// }
	//
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
}
