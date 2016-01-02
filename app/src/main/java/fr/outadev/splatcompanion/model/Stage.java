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

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * A stage, or map. It has a name and a corresponding drawable.
 */
public class Stage implements Serializable {

	private String fallbackName;

	private
	@StringRes Integer nameResId;

	private
	@DrawableRes Integer previewResId;

	Stage() {
		this.fallbackName = null;
		this.nameResId = null;
		this.previewResId = null;
	}

	Stage(@StringRes int nameResId, @DrawableRes int previewResId) {
		this();
		this.nameResId = nameResId;
		this.previewResId = previewResId;
	}

	Stage(String fallbackName, @DrawableRes int previewResId) {
		this();
		this.fallbackName = fallbackName;
		this.previewResId = previewResId;
	}

	private int getNameResId() {
		return nameResId;
	}

	public String getNameRes(Context context) {
		if (nameResId != null) {
			return context.getResources().getString(getNameResId());
		} else {
			return fallbackName;
		}
	}

	public int getPreviewResId() {
		return previewResId;
	}
}
