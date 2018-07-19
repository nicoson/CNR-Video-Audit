package cn.qiniu.util;

public class OpIdToNm {
	//tbl:表名
	private String tbl;
	//key:ID列名
	private String key;
	//keyVal:ID值
	private String keyVal;
	//nm:名称列名
	private String nm;
	//andStr：附加条件
	private String andStr;
	
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKeyVal() {
		return keyVal;
	}
	public void setKeyVal(String keyVal) {
		this.keyVal = keyVal;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getAndStr() {
		return andStr;
	}
	public void setAndStr(String andStr) {
		this.andStr = andStr;
	}
	
}
