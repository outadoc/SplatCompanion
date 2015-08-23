package outadev.fr.splatcompanion;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * A fragment that displays the about screen (using preferences).
 */
public class FragmentAbout extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);

		try {
			PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			findPreference("pref_version").setSummary(getString(R.string.about_version_sum, pInfo.versionName));
		} catch(PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}

}
