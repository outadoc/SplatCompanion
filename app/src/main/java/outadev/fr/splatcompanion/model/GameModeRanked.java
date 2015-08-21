package outadev.fr.splatcompanion.model;

import outadev.fr.splatcompanion.R;

/**
 * Created by outadoc on 21/08/15.
 */
public class GameModeRanked extends GameMode {

	private Rules gameRules;

	@Override
	public int getNameResId() {
		return R.string.mode_ranked;
	}

	@Override
	public int getIconResId() {
		return R.drawable.ic_stage_ranked;
	}

	public Rules getGameRules() {
		return gameRules;
	}

	public void setGameRules(Rules gameRules) {
		this.gameRules = gameRules;
	}

}
