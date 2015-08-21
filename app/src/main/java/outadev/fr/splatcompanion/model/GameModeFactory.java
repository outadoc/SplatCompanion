package outadev.fr.splatcompanion.model;

import android.support.annotation.NonNull;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class GameModeFactory {

	public static GameMode create(@NonNull String gameMode) {
		switch(gameMode.trim().toUpperCase()) {
			case "RANKED":
				return new GameModeRanked();
			case "REGULAR":
			default:
				return new GameModeRegular();
		}
	}

}
