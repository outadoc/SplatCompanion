package outadev.fr.splatcompanion.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by outadoc on 21/08/15.
 */
public class Stage {

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
