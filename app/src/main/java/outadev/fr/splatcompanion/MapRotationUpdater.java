package outadev.fr.splatcompanion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import outadev.fr.splatcompanion.model.GameModeRanked;
import outadev.fr.splatcompanion.model.GameModeRegular;
import outadev.fr.splatcompanion.model.RulesFactory;
import outadev.fr.splatcompanion.model.Schedule;
import outadev.fr.splatcompanion.model.Stage;
import outadev.fr.splatcompanion.model.StageFactory;

/**
 * Created by outadoc on 22/08/15.
 */
public class MapRotationUpdater {

	public static List<Schedule> parseSchedules(String rawJson) throws JSONException {
		List<Schedule> schedules = new ArrayList<>();

		JSONObject baseObject = new JSONObject(rawJson);
		JSONArray scheduleArray = baseObject.getJSONArray("schedule");

		// For each schedule in the array
		for(int i = 0; i < scheduleArray.length(); i++) {
			JSONObject scheduleObject = scheduleArray.getJSONObject(i);
			Schedule schedule = new Schedule(scheduleObject.getLong("startTime"), scheduleObject.getLong("endTime"));

			// Parse Regular game mode
			GameModeRegular regular = new GameModeRegular();
			regular.getStages().addAll(getStagesForJSONObject(scheduleObject.getJSONObject("regular")));

			// Parse Ranked game mode
			JSONObject rankedObject = scheduleObject.getJSONObject("ranked");

			GameModeRanked ranked = new GameModeRanked();
			ranked.setGameRules(RulesFactory.create(rankedObject.getString("rulesEN")));
			ranked.getStages().addAll(getStagesForJSONObject(rankedObject));

			schedule.setRegularMode(regular);
			schedule.setRankedMode(ranked);

			schedules.add(schedule);
		}

		return schedules;
	}

	/**
	 * Get the stages for a JSON object containing maps
	 * "regular":{ "maps":[{...
	 */
	private static List<Stage> getStagesForJSONObject(JSONObject gameModeObject) throws JSONException {
		List<Stage> stages = new ArrayList<>();
		JSONArray mapsArray = gameModeObject.getJSONArray("maps");

		for(int i = 0; i < mapsArray.length(); i++) {
			JSONObject mapObject = mapsArray.getJSONObject(i);
			Stage stage = StageFactory.create(mapObject.getString("nameEN"));
			stages.add(stage);
		}

		return stages;
	}

}
