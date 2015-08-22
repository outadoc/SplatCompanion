package outadev.fr.splatcompanion.model;

import outadev.fr.splatcompanion.R;

/**
 * The "ranked" game mode.
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
