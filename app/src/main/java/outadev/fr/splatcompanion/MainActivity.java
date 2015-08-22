package outadev.fr.splatcompanion;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.util.List;

import outadev.fr.splatcompanion.model.GameModeRanked;
import outadev.fr.splatcompanion.model.GameModeRegular;
import outadev.fr.splatcompanion.model.RulesFactory;
import outadev.fr.splatcompanion.model.Schedule;
import outadev.fr.splatcompanion.model.StageFactory;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

		viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);
	}

	private Schedule getDummySchedule() {

		try {
			String rawJson = "{\"updateTime\":1440151275135,\"schedule\":[{\"startTime\":1440151200000,\"endTime\":1440165600000,\"regular\":{\"maps\":[{\"nameJP\":\"ネギトロ炭鉱\",\"nameEN\":\"Bluefin Depot\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"モンガラキャンプ場\",\"nameEN\":\"Camp Triggerfish\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチヤグラ\",\"rulesEN\":\"Tower Control\"}},{\"startTime\":1440165600000,\"endTime\":1440180000000,\"regular\":{\"maps\":[{\"nameJP\":\"デカライン高架下\",\"nameEN\":\"Urchin Underpass\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"タチウオパーキング\",\"nameEN\":\"Moray Towers\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチホコ\",\"rulesEN\":\"Rainmaker\"}},{\"startTime\":1440180000000,\"endTime\":1440194400000,\"regular\":{\"maps\":[{\"nameJP\":\"ハコフグ倉庫\",\"nameEN\":\"Walleye Warehouse\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"ホッケふ頭\",\"nameEN\":\"Port Mackerel\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチエリア\",\"rulesEN\":\"Splat Zones\"}}]}";
			List<Schedule> schedules = MapRotationUpdater.parseSchedules(rawJson);
			return schedules.get(0);
		} catch(JSONException e) {
			e.printStackTrace();

			// Return a dummy schedule anyway :<
			Schedule schedule = new Schedule(System.currentTimeMillis(), System.currentTimeMillis() + 1000 * 60);

			GameModeRegular regular = new GameModeRegular();
			GameModeRanked ranked = new GameModeRanked();

			regular.getStages().add(StageFactory.create("Urchin Underpass"));
			regular.getStages().add(StageFactory.create("Urchin Underpass"));

			ranked.setGameRules(RulesFactory.create("Splat Zones"));
			ranked.getStages().add(StageFactory.create("Urchin Underpass"));

			schedule.setRankedMode(ranked);
			schedule.setRegularMode(regular);

			return schedule;
		}
	}

	public class SectionPagerAdapter extends FragmentPagerAdapter {

		public SectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			FragmentMapRotation frag;

			Bundle bundle = new Bundle();
			bundle.putSerializable("schedule", getDummySchedule());

			switch(position) {
				case 0:
				default:
					frag = new FragmentRegularBattles();
					break;
				case 1:
					frag = new FragmentRankedBattles();
					break;
			}

			frag.setArguments(bundle);
			return frag;
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
					return "Regular";
				case 1:
					return "Ranked";
			}
		}
	}


}
