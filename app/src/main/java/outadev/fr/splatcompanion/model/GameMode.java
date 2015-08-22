package outadev.fr.splatcompanion.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A game mode.
 * Example: regular or ranked.
 */
public abstract class GameMode implements Serializable {

	private List<Stage> stages;

	public GameMode() {
		this.stages = new ArrayList<>();
	}

	public abstract int getNameResId();

	public abstract int getIconResId();

	public List<Stage> getStages() {
		return stages;
	}

}
