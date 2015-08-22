package outadev.fr.splatcompanion.model;

import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * The game rules. Used for the ranked game mode.
 * Example: Splat Zones, Rainmaker...
 */
public class Rules implements Serializable {

	private
	@StringRes int nameResId;

	Rules(@StringRes int nameResId) {
		this.nameResId = nameResId;
	}

	public int getNameResId() {
		return nameResId;
	}

	public void setNameResId(@StringRes int nameResId) {
		this.nameResId = nameResId;
	}

}
