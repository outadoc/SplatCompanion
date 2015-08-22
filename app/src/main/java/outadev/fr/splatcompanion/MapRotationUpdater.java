package outadev.fr.splatcompanion;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import outadev.fr.splatcompanion.model.GameModeRanked;
import outadev.fr.splatcompanion.model.GameModeRegular;
import outadev.fr.splatcompanion.model.RulesFactory;
import outadev.fr.splatcompanion.model.Schedule;
import outadev.fr.splatcompanion.model.Stage;
import outadev.fr.splatcompanion.model.StageFactory;

/**
 * Manages the retrieval of map rotation schedules from the API.
 */
public class MapRotationUpdater {

	public static final String SCHEDULE_ENDPOINT_URL = "https://splatoon.ink/schedule.json";

	private static final OkHttpClient client = new OkHttpClient();

	/**
	 * Retrieves the freshest schedule from the API
	 * @return The raw response from the API
	 * @throws IOException
	 */
	public static String getScheduleDataFromAPI() throws IOException {
		Request request = new Request.Builder()
				.url(SCHEDULE_ENDPOINT_URL)
				.build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	/**
	 * Parses the schedules of the current and next Splatoon map rotations.
	 * This method parses the JSON data and creates the required objects corresponding to the data.
	 *
	 * @param rawJson The raw JSON data returned by the API
	 * @return A list of schedules containing the parsed data
	 * @throws JSONException
	 */
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
