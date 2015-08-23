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

package outadev.fr.splatcompanion.model;

import java.io.Serializable;

/**
 * A rotation schedule. It has a start time, an end time, and contains
 * game modes with their stages.
 */
public class Schedule implements Serializable {

	private long startTime;
	private long endTime;

	private GameModeRegular regularMode;
	private GameModeRanked rankedMode;

	public Schedule(long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public GameModeRegular getRegularMode() {
		return regularMode;
	}

	public void setRegularMode(GameModeRegular regularMode) {
		this.regularMode = regularMode;
	}

	public GameModeRanked getRankedMode() {
		return rankedMode;
	}

	public void setRankedMode(GameModeRanked rankedMode) {
		this.rankedMode = rankedMode;
	}
}
