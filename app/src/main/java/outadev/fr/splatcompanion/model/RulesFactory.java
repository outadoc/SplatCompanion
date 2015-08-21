package outadev.fr.splatcompanion.model;

import android.support.annotation.NonNull;

import outadev.fr.splatcompanion.R;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class RulesFactory {

	/**
	 * Returns a rule corresponding to the English name passed as a parameter.
	 * @param ruleName The name of the rules, in English.
	 * @return A Rules object.
	 */
	public static Rules create(@NonNull String ruleName) {
		switch(ruleName.trim().toUpperCase()) {
			case "RAINMAKER":
				return new Rules(R.string.rules_rainmaker);
			case "SPLAT ZONES":
				return new Rules(R.string.rules_splat_zones);
			default:
				return new Rules(R.string.unknown);
		}
	}

}
