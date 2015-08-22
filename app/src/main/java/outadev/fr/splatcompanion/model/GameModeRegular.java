package outadev.fr.splatcompanion.model;

import outadev.fr.splatcompanion.R;

/**
 * The "regular" game mode.
 */
public class GameModeRegular extends GameMode {

	@Override
	public int getNameResId() {
		return R.string.mode_regular;
	}

	@Override
	public int getIconResId() {
		return R.drawable.ic_stage_regular;
	}

}
