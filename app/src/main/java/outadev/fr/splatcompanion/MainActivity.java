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
		stopTimer();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeTimer();
	}

	public void stopTimer() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void resumeTimer() {
		countdownTask = new UpdateCountdownTimerTask();

		timer = new Timer();
		timer.schedule(countdownTask, 0, TIMER_UPDATE_INTERVAL);
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
				(new FetchRotationSchedule()).execute();
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

	private class FetchRotationSchedule extends AsyncTask<Void, Void, List<Schedule>> {

		private Exception e;

		@Override
		protected List<Schedule> doInBackground(Void... params) {
			try {
				return MapRotationUpdater.getFreshestData(getApplicationContext());
			} catch(Exception e) {
				e.printStackTrace();

				this.e = e;
				return null;
			}
		}

		@Override
		protected void onPreExecute() {
			stopTimer();
		}

		@Override
		protected void onPostExecute(List<Schedule> schedules) {
			if(e != null) {
				displayErrorMessage(getString(R.string.error), e.getMessage());
			}

			if(schedules.isEmpty()) {
				displayErrorMessage(getString(R.string.error_no_stages_title),
						getString(R.string.error_no_stages_message));
				return;
			}

			schedule = schedules.get(0);

			fragmentRegularBattles.updateSchedule(schedule);
			fragmentRankedBattles.updateSchedule(schedule);

			resumeTimer();
		}

	}

}
