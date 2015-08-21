package outadev.fr.splatcompanion.model;

import android.support.annotation.StringRes;

/**
 * Created by outadoc on 21/08/15.
 */
public class Rules {

	private
	@StringRes int nameResId;

	Rules(@StringRes int nameResId) {
		this.nameResId = nameResId;
	}

	public int getNameResId() {
		return nameResId;
	}

	public void setNameResId(@StringRes int nameResId) {
		this.nameResId = nameResId;
	}

}
