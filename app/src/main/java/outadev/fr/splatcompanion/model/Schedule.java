package outadev.fr.splatcompanion.model;

import java.io.Serializable;

/**
 * A rotation schedule. It has a start time, an end time, and contains
 * game modes with their stages.
 */
public class Schedule implements Serializable {

	private long startTime;
	private long endTime;

	private GameModeRegular regularMode;
	private GameModeRanked rankedMode;

	public Schedule(long startTime, long endTime) {
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

	public GameModeRegular getRegularMode() {
		return regularMode;
	}

	public void setRegularMode(GameModeRegular regularMode) {
		this.regularMode = regularMode;
	}

	public GameModeRanked getRankedMode() {
		return rankedMode;
	}

	public void setRankedMode(GameModeRanked rankedMode) {
		this.rankedMode = rankedMode;
	}
}
