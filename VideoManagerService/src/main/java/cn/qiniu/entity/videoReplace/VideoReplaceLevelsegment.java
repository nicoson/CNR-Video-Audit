package cn.qiniu.entity.videoReplace;

import java.math.BigDecimal;
import java.util.List;

public class VideoReplaceLevelsegment {
	private String op;
	private int offsetBegin;
	private int offsetEnd;
	private String uri;
	private List<VideoReplaceLevelsegmentcuts> cuts;
	private String level;

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getOffsetBegin() {
		return offsetBegin;
	}

	public void setOffsetBegin(int offsetBegin) {
		this.offsetBegin = offsetBegin;
	}

	public int getOffsetEnd() {
		return offsetEnd;
	}

	public void setOffsetEnd(int offsetEnd) {
		this.offsetEnd = offsetEnd;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<VideoReplaceLevelsegmentcuts> getCuts() {
		return cuts;
	}

	public void setCuts(List<VideoReplaceLevelsegmentcuts> cuts) {
		this.cuts = cuts;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
