package cn.qiniu.util;/** *  Project：Id2NmFormatterVo*  statement：code转名称注解信息数据对象*  @Author ：lvweijun*  @Date ：2016-11-03*/public class Id2NmFormatterVo {	/** 数据库名称 **/	private String database;	/** 表名称 **/	private String tbl;	/** key项目 **/	private String key;	/** key值对应名称 **/	private String nm;	/** 查询条件key **/	private String schKey;	/** 名称别名 **/	private String transNm;	/** 附加条件 **/	private String andStr;		public String getDatabase() {		return database;	}	public void setDatabase(String database) {		this.database = database;	}	public String getTbl() {		return tbl;	}	public void setTbl(String tbl) {		this.tbl = tbl;	}	public String getKey() {		return key;	}	public void setKey(String key) {		this.key = key;	}	public String getNm() {		return nm;	}	public void setNm(String nm) {		this.nm = nm;	}	public String getSchKey() {		return schKey;	}	public void setSchKey(String schKey) {		this.schKey = schKey;	}	public String getTransNm() {		return transNm;	}	public void setTransNm(String transNm) {		this.transNm = transNm;	}	public String getAndStr() {		return andStr;	}	public void setAndStr(String andStr) {		this.andStr = andStr;	}		}