package outadev.fr.splatcompanion.model;

import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class GameMode {

	private
	@StringRes int nameResId;

	private List<Stage> stages;

	public GameMode(@StringRes int nameResId) {
		this.stages = new ArrayList<>();
		this.nameResId = nameResId;
	}

	public int getNameResId() {
		return nameResId;
	}

	public void setNameResId(@StringRes int nameResId) {
		this.nameResId = nameResId;
	}

	public List<Stage> getStages() {
		return stages;
	}

}
