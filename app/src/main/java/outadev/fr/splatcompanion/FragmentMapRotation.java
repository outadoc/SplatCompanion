package outadev.fr.splatcompanion;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by outadoc on 21/08/15.
 */
public class FragmentMapRotation extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_schedule, container, false);
		Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "project_paintball_beta_2.otf");

		TextView txtGameMode = (TextView) view.findViewById(R.id.game_mode_title);
		TextView txtMapName = (TextView) view.findViewById(R.id.txt_map_title);

		txtGameMode.setTypeface(typeface);
		txtMapName.setTypeface(typeface);

		return view;
	}

}
