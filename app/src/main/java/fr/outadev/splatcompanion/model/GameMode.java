/*
 * Splat Companion - Stage rotation schedule viewer for Splatoon(tm)
 * Copyright (C) 2015 - 2016  Baptiste Candellier
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A game mode.
 * Example: regular or ranked.
 */
public abstract class GameMode implements Serializable {

	private Rules gameRules;
	private List<Stage> stages;

	public GameMode() {
		this.stages = new ArrayList<>();
	}

	public abstract int getNameResId();

	public abstract int getIconResId();

	public List<Stage> getStages() {
		return stages;
	}

	public Rules getGameRules() {
		return gameRules;
	}

	public void setGameRules(Rules gameRules) {
		this.gameRules = gameRules;
	}


}
