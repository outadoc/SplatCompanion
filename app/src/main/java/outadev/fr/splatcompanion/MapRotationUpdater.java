package outadev.fr.splatcompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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
	public static final String KEY_LAST_CACHED_DATA = "cached_response_schedule";

	protected static final OkHttpClient client = new OkHttpClient();

	/**
	 * Fetches the freshest schedules available (from cache or from the API).
	 *
	 * @param context A context
	 * @return A list of schedules containing the data
	 *
	 * @throws IOException
	 * @throws JSONException
	 */
	public static List<Schedule> getFreshestData(Context context) throws IOException, JSONException {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String cache = prefs.getString(KEY_LAST_CACHED_DATA, null);

		if(cache != null) {
			try {
				List<Schedule> cachedSchedules = parseSchedules(cache);

				// If the cached schedules aren't empty
				if(cachedSchedules != null && !cachedSchedules.isEmpty()) {

					// If the cached schedules are still fresh
					if(cachedSchedules.get(0).getEndTime() > System.currentTimeMillis()) {
						Log.d(MapRotationUpdater.class.getName(), "Using cached data");
						return cachedSchedules;
					}
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}

		Log.d(MapRotationUpdater.class.getName(), "Fetching fresh data");

		String freshData = getScheduleDataFromAPI();
		prefs.edit()
				.putString(KEY_LAST_CACHED_DATA, freshData)
				.apply();

		return parseSchedules(freshData);
	}

	/**
	 * Retrieves the freshest schedule from the API
	 * @return The raw response from the API
	 * @throws IOException
	 */
	protected static String getScheduleDataFromAPI() throws IOException {
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
	protected static List<Schedule> parseSchedules(String rawJson) throws JSONException {
		List<Schedule> schedules = new ArrayList<>();

		JSONObject baseObject = new JSONObject(rawJson);
		JSONArray scheduleArray = baseObject.getJSONArray("schedule");

		// For each schedule in the array
		for(int i = 0; i < scheduleArray.length(); i++) {
			JSONObject scheduleObject = scheduleArray.getJSONObject(i);
			Schedule schedule = new Schedule(scheduleObject.getLong("startTime"), scheduleObject.getLong("endTime"));

			// Parse Regular game mode
			GameModeRegular regular = new GameModeRegular();
			regular.getStages().addAll(parseStages(scheduleObject.getJSONObject("regular")));

			// Parse Ranked game mode
			JSONObject rankedObject = scheduleObject.getJSONObject("ranked");

			GameModeRanked ranked = new GameModeRanked();
			ranked.setGameRules(RulesFactory.create(rankedObject.getString("rulesEN")));
			ranked.getStages().addAll(parseStages(rankedObject));

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
	protected static List<Stage> parseStages(JSONObject gameModeObject) throws JSONException {
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
