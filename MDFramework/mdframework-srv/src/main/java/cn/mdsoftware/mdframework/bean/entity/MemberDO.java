package cn.mdsoftware.mdframework.bean.entity;

import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-11-30 15:57:00
 */
public class MemberDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//姓名
	private String name;
	//手机号码
	private String mobile;
	//头像
	private String mainPic;
	//微信openid
	private String openId;
	//微信unionid
	private String unionId;
	//创建时间
	private String createTime = DateUtils.toDate(new Date(), Constants.DatetimePattern.DP19);
	//删除状态
	private Integer delStatus = 1;

	/**
	 * 设置：ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号码
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：头像
	 */
	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}
	/**
	 * 获取：头像
	 */
	public String getMainPic() {
		return mainPic;
	}
	/**
	 * 设置：微信openid
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：微信openid
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：微信unionid
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	/**
	 * 获取：微信unionid
	 */
	public String getUnionId() {
		return unionId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：删除状态
	 */
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	/**
	 * 获取：删除状态
	 */
	public Integer getDelStatus() {
		return delStatus;
	}
}
