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

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * A stage, or map. It has a name and a corresponding drawable.
 */
public class Stage implements Serializable {

	private
	@StringRes int nameResId;

	private
	@DrawableRes int previewResId;

	Stage(@StringRes int nameResId, @DrawableRes int previewResId) {
		this.nameResId = nameResId;
		this.previewResId = previewResId;
	}

	public int getNameResId() {
		return nameResId;
	}

	public void setNameResId(int nameResId) {
		this.nameResId = nameResId;
	}

	public int getPreviewResId() {
		return previewResId;
	}

	public void setPreviewResId(int previewResId) {
		this.previewResId = previewResId;
	}
}
