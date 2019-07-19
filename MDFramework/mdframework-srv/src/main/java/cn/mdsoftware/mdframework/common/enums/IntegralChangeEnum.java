package cn.mdsoftware.mdframework.common.enums;

public enum IntegralChangeEnum {


    LOTTERY(1,"积分抽奖"),
    SIGN(2,"积分签到"),
    INVITATION(3,"邀请新用户"),
    WECHAT_SHARE(4,"微信分享"),
    INTEGRAL_DEAL(5,"积分兑换"),
    GOODS_RETURN(5,"商品退还");


    IntegralChangeEnum(Integer code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    private Integer code;

    private String describe;

    public Integer getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }
}
