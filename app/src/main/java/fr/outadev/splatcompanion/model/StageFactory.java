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
 * Facilitates the creation of stages.
 */
public abstract class StageFactory {

	/**
	 * Creates a stage using its English name.
	 * @param stageName The English version of the stage's name.
	 * @return The corresponding stage.
	 */
	public static Stage create(@NonNull String stageName) {
		switch(stageName.trim().toUpperCase()) {
			case "AROWANA MALL":
				return new Stage(R.string.stage_arowana_mall, R.drawable.stage_arowana_mall);
			case "BLACKBELLY SKATEPARK":
				return new Stage(R.string.stage_blackbelly_skatepark, R.drawable.stage_blackbelly_skatepark);
			case "BLUEFIN DEPOT":
				return new Stage(R.string.stage_bluefin_depot, R.drawable.stage_bluefin_depot);
			case "CAMP TRIGGERFISH":
				return new Stage(R.string.stage_camp_triggerfish, R.drawable.stage_camp_triggerfish);
			case "FLOUNDER HEIGHTS":
				return new Stage(R.string.stage_flounder_heights, R.drawable.stage_flounder_heights);
			case "HAMMERHEAD BRIDGE":
				return new Stage(R.string.stage_hammerhead_bridge, R.drawable.stage_hammerhead_bridge);
			case "KELP DOME":
				return new Stage(R.string.stage_kelp_dome, R.drawable.stage_kelp_dome);
			case "MORAY TOWERS":
				return new Stage(R.string.stage_moray_towers, R.drawable.stage_moray_towers);
			case "PORT MACKEREL":
				return new Stage(R.string.stage_port_mackerel, R.drawable.stage_port_mackerel);
			case "SALTSPRAY RIG":
				return new Stage(R.string.stage_saltspray_rig, R.drawable.stage_saltspray_rig);
			case "URCHIN UNDERPASS":
				return new Stage(R.string.stage_urchin_underpass, R.drawable.stage_urchin_underpass);
			case "WALLEYE WAREHOUSE":
				return new Stage(R.string.stage_walleye_warehouse, R.drawable.stage_walleye_warehouse);
			case "MUSEUM D'ALFONSINO":
				return new Stage(R.string.stage_museum_dalfonsino, R.drawable.stage_museum_dalfonsino);
			default:
				return new Stage(R.string.unknown, R.drawable.stage_unknown);
		}
	}

}
