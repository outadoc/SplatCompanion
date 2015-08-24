/*
 * Splat Companion - Stage rotation schedule viewer for Splatoon(tm)
 * Copyright (C) 2015  Baptiste Candellier
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.outadev.splatcompanion.model;

import android.support.annotation.NonNull;

import fr.outadev.splatcompanion.R;

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
