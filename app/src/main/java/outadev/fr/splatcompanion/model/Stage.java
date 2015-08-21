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

	public Stage(@StringRes int nameResId, @DrawableRes int previewResId) {
		this.nameResId = nameResId;
		this.previewResId = previewResId;
	}
}
