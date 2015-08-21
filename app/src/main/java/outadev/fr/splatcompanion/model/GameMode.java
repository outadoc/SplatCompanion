package outadev.fr.splatcompanion.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class GameMode {

	private List<Stage> stages;

	GameMode() {
		this.stages = new ArrayList<>();
	}

	public abstract int getNameResId();

	public abstract int getIconResId();

	public List<Stage> getStages() {
		return stages;
	}

}
