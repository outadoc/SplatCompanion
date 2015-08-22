package outadev.fr.splatcompanion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import outadev.fr.splatcompanion.model.GameMode;

/**
 * Created by outadoc on 21/08/15.
 */
public class FragmentRegularBattles extends FragmentMapRotation {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		txtGameMode.setText(R.string.rules_turf_war);
		return view;
	}

	@Override
	protected GameMode getActiveMode() {
		return schedule.getRegularMode();
	}

}
