package outadev.fr.splatcompanion.model;

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
