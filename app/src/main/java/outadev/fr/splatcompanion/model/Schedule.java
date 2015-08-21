package outadev.fr.splatcompanion.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by outadoc on 21/08/15.
 */
public class Schedule {

	private long startTime;
	private long endTime;

	private List<GameMode> gameModes;

	public Schedule(long startTime, long endTime) {
		this.gameModes = new ArrayList<>();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public List<GameMode> getGameModes() {
		return gameModes;
	}

}
