package outadev.fr.splatcompanion;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by outadoc on 23/08/15.
 */
public class FragmentAbout extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);
	}

}
