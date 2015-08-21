package outadev.fr.splatcompanion.model;

import java.io.Serializable;

/**
 * Created by outadoc on 21/08/15.
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
