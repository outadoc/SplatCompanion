package outadev.fr.splatcompanion.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class GameMode {

	private List<Stage> stages;

	public GameMode() {
		this.stages = new ArrayList<>();
	}

	public abstract int getNameResId();

	public List<Stage> getStages() {
		return stages;
	}

}
