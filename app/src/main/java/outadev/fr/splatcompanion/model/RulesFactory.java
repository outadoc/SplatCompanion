package outadev.fr.splatcompanion.model;

import android.support.annotation.NonNull;

import outadev.fr.splatcompanion.R;

/**
 * Facilitates the creation of game rules.
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
			case "TOWER CONTROL":
				return new Rules(R.string.rules_tower_control);
			case "TURF WAR":
				return new Rules(R.string.rules_turf_war);
			default:
				return new Rules(R.string.unknown);
		}
	}

}
