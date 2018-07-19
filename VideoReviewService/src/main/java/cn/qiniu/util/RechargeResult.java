package cn.qiniu.util;


/**
 * Project    ：ChargeResult
 * statement  ：账户充值
 * @Author    ：声通
 * Date       ：2016-12-14
 */
public class RechargeResult {
	/**充值结果状态**/
    private boolean success;
    /**充值结果信息**/
    private String msg;
    /**充值流水号(系统内订单号)**/
    private String rechargeId;
    
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}
	
}
