package outadev.fr.splatcompanion.model;

import android.support.annotation.NonNull;

import outadev.fr.splatcompanion.R;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class StageFactory {

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
				return new Stage(R.string.stage_arowana_mall, R.drawable.stage_arowana_mall);
			default:
				// TODO new default image
				return new Stage(R.string.unknown, R.drawable.ic_stage_regular);
		}
	}

}
