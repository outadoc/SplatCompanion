package outadev.fr.splatcompanion.model;

import android.support.annotation.NonNull;

import outadev.fr.splatcompanion.R;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class StageFactory {

	public static Stage create(@NonNull String stageName) {
		switch(stageName.trim().toUpperCase()) {
			case "URCHIN UNDERPASS":
				return new Stage(R.string.stage_urchin_underpass, R.drawable.stage1);
			default:
				// TODO new default image
				return new Stage(R.string.unknown, R.drawable.ic_stage_regular);
		}
	}

}
