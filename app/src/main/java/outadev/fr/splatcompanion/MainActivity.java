package outadev.fr.splatcompanion;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import outadev.fr.splatcompanion.model.Schedule;

/**
 * The main activity of the app.
 * Contains a view pager with fragments to present the schedules.
 */
public class MainActivity extends AppCompatActivity {

	public static final int TIMER_UPDATE_INTERVAL = 1000;

	private FragmentRegularBattles fragmentRegularBattles;
	private FragmentRankedBattles fragmentRankedBattles;

	private TextView countdown;
	private Schedule schedule;

	private Timer timer;
	private TimerTask countdownTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentRegularBattles = new FragmentRegularBattles();
		fragmentRankedBattles = new FragmentRankedBattles();

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

		viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);

		countdown = (TextView) findViewById(R.id.txt_countdown);

		if(savedInstanceState == null) {
			(new FetchRotationSchedule()).execute();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		timer.cancel();
	}

	@Override
	protected void onResume() {
		super.onResume();
		countdownTask = new UpdateCountdownTimerTask();

		timer = new Timer();
		timer.schedule(countdownTask, 0, TIMER_UPDATE_INTERVAL);
	}

	private Schedule getDummySchedule() {
		try {
			String rawJson = "{\"updateTime\":1440151275135,\"schedule\":[{\"startTime\":1440151200000,\"endTime\":1440165600000,\"regular\":{\"maps\":[{\"nameJP\":\"ネギトロ炭鉱\",\"nameEN\":\"Bluefin Depot\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"モンガラキャンプ場\",\"nameEN\":\"Camp Triggerfish\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチヤグラ\",\"rulesEN\":\"Tower Control\"}},{\"startTime\":1440165600000,\"endTime\":1440180000000,\"regular\":{\"maps\":[{\"nameJP\":\"デカライン高架下\",\"nameEN\":\"Urchin Underpass\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"タチウオパーキング\",\"nameEN\":\"Moray Towers\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチホコ\",\"rulesEN\":\"Rainmaker\"}},{\"startTime\":1440180000000,\"endTime\":1440194400000,\"regular\":{\"maps\":[{\"nameJP\":\"ハコフグ倉庫\",\"nameEN\":\"Walleye Warehouse\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"ホッケふ頭\",\"nameEN\":\"Port Mackerel\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチエリア\",\"rulesEN\":\"Splat Zones\"}}]}";
			List<Schedule> schedules = MapRotationUpdater.parseSchedules(rawJson);
			// Set end time to 4 hours from now
			schedules.get(0).setEndTime(System.currentTimeMillis() + 1000 * 60 * 60 * 4);
			return schedules.get(0);
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void displayErrorMessage(String title, String message) {
		(new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message))
				.setPositiveButton(android.R.string.ok, null)
				.show();
	}

	public class SectionPagerAdapter extends FragmentPagerAdapter {

		public SectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
				case 0:
				default:
					return fragmentRegularBattles;
				case 1:
					return fragmentRankedBattles;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch(position) {
				case 0:
				default:
					return getString(R.string.mode_regular);
				case 1:
					return getString(R.string.mode_ranked);
			}
		}
	}

	private class UpdateCountdownTimerTask extends TimerTask {

		@Override
		public void run() {
			if(schedule == null) {
				return;
			}

			MainActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// Calculate the hours/minutes/seconds until the next map rotation
					long millisToEnd = schedule.getEndTime() - System.currentTimeMillis();

					int seconds = (int) (millisToEnd / 1000) % 60;
					int minutes = (int) ((millisToEnd / (1000 * 60)) % 60);
					int hours = (int) ((millisToEnd / (1000 * 60 * 60)) % 24);

					// Display a countdown until the rotation
					countdown.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
				}
			});
		}
	}

	private class FetchRotationSchedule extends AsyncTask<Void, Void, String> {

		private Exception e;

		@Override
		protected String doInBackground(Void... params) {
			try {
				return MapRotationUpdater.getScheduleDataFromAPI();
			} catch(IOException e) {
				this.e = e;
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);

			if(e != null) {
				displayErrorMessage("Error", e.getMessage());
			}

			try {
				List<Schedule> schedules = MapRotationUpdater.parseSchedules(s);

				if(schedules.isEmpty()) {
					displayErrorMessage("Can't get current stages",
							"We couldn't get the current stages right now. Maybe there's a Splatfest going on?");
					return;
				}

				schedule = schedules.get(0);

				fragmentRegularBattles.updateSchedule(schedule);
				fragmentRankedBattles.updateSchedule(schedule);
			} catch(JSONException e) {
				e.printStackTrace();
				displayErrorMessage("Error", e.getMessage());
			}
		}

	}

}
