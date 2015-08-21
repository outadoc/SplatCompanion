package outadev.fr.splatcompanion.model;

import android.support.annotation.StringRes;

/**
 * Created by outadoc on 21/08/15.
 */
public class GameModeRanked extends GameMode {

	private Rules gameRules;

	public GameModeRanked(@StringRes int nameResId, Rules gameRules) {
		super(nameResId);
		this.gameRules = gameRules;
	}

	public Rules getGameRules() {
		return gameRules;
	}

	public void setGameRules(Rules gameRules) {
		this.gameRules = gameRules;
	}

}
