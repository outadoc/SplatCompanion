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

import fr.outadev.splatcompanion.R;

/**
 * The "regular" game mode.
 */
public class GameModeRegular extends GameMode {

	@Override
	public int getNameResId() {
		return R.string.mode_regular;
	}

	@Override
	public int getIconResId() {
		return R.drawable.ic_stage_regular;
	}

}
