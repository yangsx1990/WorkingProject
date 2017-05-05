package com.hiersun.oohdear.util;/**
 * Created by liubaocheng on 2016/11/28.
 */

/**
 * @descrption(描述): 交易退款接口异常枚举类
 * @author(作者): liubaocheng
 * @date(创建日期): 2016-11-28 18:51
 * @history(更改记录):
 **/
public enum AlipayTradeRefundReponseEnum {

    SYSTEM_ERROR("CQ.SYSTEM_ERROR","系统错误","请使用相同的参数再次调用"),
    INVALID_PARAMETER("ACQ.INVALID_PARAMETER","参数无效","请求参数有错，重新检查请求后，再调用退款"),
    SELLER_BALANCE_NOT_ENOUGH("ACQ.SELLER_BALANCE_NOT_ENOUGH","卖家余额不足","商户支付宝账户充值后重新发起退款即可"),
    REFUND_AMT_NOT_EQUAL_TOTAL("ACQ.REFUND_AMT_NOT_EQUAL_TOTAL","退款金额超限","检查退款金额是否正确，重新修改请求后，重新发起退款"),
    REASON_TRADE_BEEN_FREEZEN("ACQ.REASON_TRADE_BEEN_FREEZEN","请求退款的交易被冻结","联系支付宝小二，确认该笔交易的具体情况"),
    TRADE_NOT_EXIST("ACQ.TRADE_NOT_EXIST","交易不存在","检查请求中的交易号和商户订单号是否正确，确认后重新发起"),
    TRADE_HAS_FINISHED("ACQ.TRADE_HAS_FINISHED","交易已完结","该交易已完结，不允许进行退款，确认请求的退款的交易信息是否正确"),
    TRADE_STATUS_ERROR("ACQ.TRADE_STATUS_ERROR","交易状态非法","查询交易，确认交易是否已经付款"),
    DISCORDANT_REPEAT_REQUEST("ACQ.DISCORDANT_REPEAT_REQUEST","不一致的请求","检查该退款号是否已退过款或更换退款号重新发起请求"),
    REASON_TRADE_REFUND_FEE_ERR("ACQ.REASON_TRADE_REFUND_FEE_ERR","退款金额无效","检查退款请求的金额是否正确"),
    TRADE_NOT_ALLOW_REFUND("ACQ.TRADE_NOT_ALLOW_REFUND","当前交易不允许退款","检查当前交易的状态是否为交易成功状态以及签约的退款属性是否允许退款，确认后，重新发起请求");


    private String subCode;
    private String subMsg;
    private String subSolution;

    private AlipayTradeRefundReponseEnum(String subCode, String subMsg, String subSolution){
        this.subCode = subCode;
        this.subMsg = subMsg;
        this.subSolution = subSolution;
    }

    public String getSubCode() {
		return subCode;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public String getSubSolution() {
		return subSolution;
	}

	/**
     * 获取实体信息
     * @param subCode
     * @return
     */
    public static AlipayTradeRefundReponseEnum getEntity(String subCode){
        for(AlipayTradeRefundReponseEnum t: AlipayTradeRefundReponseEnum.values()){
            if(t.subCode.equals(subCode))
                return t;
        }
        return null;
    }

    /**
     * 查看错误描述
     * @param subCode
     * @return
     */
    public static String getSubMsg(String subCode){
        for(AlipayTradeRefundReponseEnum t: AlipayTradeRefundReponseEnum.values()){
            if(t.subCode.equals(subCode))
                return t.subMsg;
        }
        return null;
    }

    /**
     * 查看解决方案
     * @param subCode
     * @return
     */
    public static String getSubSolution(String subCode){
        for(AlipayTradeRefundReponseEnum t: AlipayTradeRefundReponseEnum.values()){
            if(t.subCode.equals(subCode))
                return t.subSolution;
        }
        return null;
    }

    /**
     * 错误码是否可用（就是查看错误码是否有相应的属性）
     * @param subCode
     * @return
     */
    public static boolean isValid(String subCode){
        for(AlipayTradeRefundReponseEnum t: AlipayTradeRefundReponseEnum.values()){
            if(t.subCode.equals(subCode))
                return true;
        }
        return false;
    }
}
