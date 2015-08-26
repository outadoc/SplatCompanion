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

package fr.outadev.splatcompanion;

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

import fr.outadev.splatcompanion.model.GameModeRanked;
import fr.outadev.splatcompanion.model.GameModeRegular;
import fr.outadev.splatcompanion.model.RulesFactory;
import fr.outadev.splatcompanion.model.Schedule;
import fr.outadev.splatcompanion.model.Stage;
import fr.outadev.splatcompanion.model.StageFactory;

/**
 * Manages the retrieval of stage rotation schedules from the API.
 */
public class StageRotationUpdater {

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
						Log.d(StageRotationUpdater.class.getName(), "Using cached data");
						return cachedSchedules;
					}
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}

		Log.d(StageRotationUpdater.class.getName(), "Fetching fresh data");

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
			//schedule.setEndTime(System.currentTimeMillis() + 5000); // testing
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
