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

import android.app.Application;
import android.test.ApplicationTestCase;

import org.json.JSONException;

import java.util.List;

import fr.outadev.splatcompanion.model.Schedule;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

	public ApplicationTest() {
		super(Application.class);
	}

	public void testParseNormalSchedule() throws JSONException {
		String rawJson = "{\"updateTime\":1440151275135,\"schedule\":[{\"startTime\":1440151200000,\"endTime\":1440165600000,\"regular\":{\"maps\":[{\"nameJP\":\"ネギトロ炭鉱\",\"nameEN\":\"Bluefin Depot\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"モンガラキャンプ場\",\"nameEN\":\"Camp Triggerfish\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチヤグラ\",\"rulesEN\":\"Tower Control\"}},{\"startTime\":1440165600000,\"endTime\":1440180000000,\"regular\":{\"maps\":[{\"nameJP\":\"デカライン高架下\",\"nameEN\":\"Urchin Underpass\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"タチウオパーキング\",\"nameEN\":\"Moray Towers\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチホコ\",\"rulesEN\":\"Rainmaker\"}},{\"startTime\":1440180000000,\"endTime\":1440194400000,\"regular\":{\"maps\":[{\"nameJP\":\"ハコフグ倉庫\",\"nameEN\":\"Walleye Warehouse\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}]},\"ranked\":{\"maps\":[{\"nameJP\":\"ホッケふ頭\",\"nameEN\":\"Port Mackerel\"},{\"nameJP\":\"ヒラメが丘団地\",\"nameEN\":\"Flounder Heights\"}],\"rulesJP\":\"ガチエリア\",\"rulesEN\":\"Splat Zones\"}}]}";
		List<Schedule> schedules = StageRotationUpdater.parseSchedules(rawJson);

		assertNotNull(schedules);
		assertEquals(3, schedules.size());
	}

	public void testParseEmptySchedule() throws JSONException {
		String rawJson = "{\"updateTime\":1440242244200,\"schedule\":[]}";
		List<Schedule> schedules = StageRotationUpdater.parseSchedules(rawJson);

		assertNotNull(schedules);
		assertEquals(0, schedules.size());
	}

}