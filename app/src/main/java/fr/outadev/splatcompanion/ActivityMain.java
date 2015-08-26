/*
 * Splat Companion - Stage rotation schedule viewer for Splatoon(tm)
 * Copyright (C) 2015  Baptiste Candellier
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.outadev.splatcompanion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fr.outadev.splatcompanion.model.Schedule;

/**
 * The main activity of the app.
 * Contains a view pager with fragments to present the schedules.
 */
public class ActivityMain extends AppCompatActivity {

	public static final int TIMER_UPDATE_INTERVAL = 1000;

	private FragmentRegularBattles fragmentRegularBattles;
	private FragmentRankedBattles fragmentRankedBattles;

	private TextView countdown;
	private ProgressBar progressSpinner;

	private Schedule schedule;
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Instantiate fragments
		fragmentRegularBattles = new FragmentRegularBattles();
		fragmentRankedBattles = new FragmentRankedBattles();

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

		// Setup view pager to display the different fragments
		viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);

		countdown = (TextView) findViewById(R.id.txt_countdown);
		progressSpinner = (ProgressBar) findViewById(R.id.progress_spinner);

		// Set up the overflow button, using a popup menu
		final ImageButton buttonOverflow = (ImageButton) findViewById(R.id.overflow_button);
		final PopupMenu menu = new PopupMenu(this, buttonOverflow);
		menu.inflate(R.menu.menu_main);

		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch(item.getItemId()) {
					case R.id.menu_item_refresh:
						(new FetchRotationSchedule()).execute();
						return true;
					case R.id.menu_item_about:
						startActivity(new Intent(ActivityMain.this, ActivityAbout.class));
						return true;
					default:
						return false;
				}
			}

		});

		buttonOverflow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.show();
			}

		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopTimer();
	}

	@Override
	protected void onResume() {
		super.onResume();
		(new FetchRotationSchedule()).execute();
	}

	/**
	 * Stops the countdown update timer.
	 */
	public void stopTimer() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * Resumes the countdown update timer.
	 */
	public void resumeTimer() {
		timer = new Timer();
		timer.schedule(new UpdateCountdownTimerTask(), 0, TIMER_UPDATE_INTERVAL);
	}

	/**
	 * Displays an error message using a dialog.
	 *
	 * @param title The title of the error dialog.
	 * @param message The message of the error dialog.
	 */
	public void displayErrorMessage(String title, String message) {
		(new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message))
				.setPositiveButton(android.R.string.ok, null)
				.show();
	}

	/**
	 * A pager adapter that displays the different fragments corresponding to the different
	 * battle modes.
	 */
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

	/**
	 * A timer task that updates the countdown timer until the next planned stage rotation.
	 */
	private class UpdateCountdownTimerTask extends TimerTask {

		@Override
		public void run() {
			if(schedule == null || schedule.getEndTime() < System.currentTimeMillis()) {
				ActivityMain.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// Run this on UI thread because it updates the view
						(new FetchRotationSchedule()).execute();
					}
				});

				return;
			}

			ActivityMain.this.runOnUiThread(new Runnable() {
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

	/**
	 * Fetches the freshest (current) stage rotation schedule.
	 */
	private class FetchRotationSchedule extends AsyncTask<Void, Void, List<Schedule>> {

		private Exception e;

		@Override
		protected List<Schedule> doInBackground(Void... params) {
			try {
				return StageRotationUpdater.getFreshestData(getApplicationContext());
			} catch(Exception e) {
				e.printStackTrace();

				this.e = e;
				return null;
			}
		}

		@Override
		protected void onPreExecute() {
			stopTimer();
			progressSpinner.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(List<Schedule> schedules) {
			if(e != null) {
				displayErrorMessage(getString(R.string.error), e.getMessage());
				return;
			}

			if(schedules == null) {
				displayErrorMessage(getString(R.string.error), getString(R.string.error_generic_network_message));
				return;
			}

			// If there are no schedules (probably during a splatfest), we display a notice to the user
			if(schedules.isEmpty()) {
				displayErrorMessage(getString(R.string.error_no_stages_title),
						getString(R.string.error_no_stages_message));
				return;
			}

			// Update the schedule in the fragments, resume the countdown timer
			schedule = schedules.get(0);

			fragmentRegularBattles.updateSchedule(schedule);
			fragmentRankedBattles.updateSchedule(schedule);

			progressSpinner.setVisibility(View.INVISIBLE);
			resumeTimer();
		}

	}

}
