package outadev.fr.splatcompanion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import outadev.fr.splatcompanion.model.GameMode;

/**
 * Created by outadoc on 21/08/15.
 */
public class FragmentRegularBattles extends FragmentMapRotation {

	@Override
	protected GameMode getActiveMode() {
		return schedule.getRegularMode();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		TextView txtGameModeTitle = (TextView) view.findViewById(R.id.game_mode_title);
		txtGameModeTitle.setText(R.string.rules_turf_war);

		return view;
	}

}
