package cn.mdsoftware.mdframework.bean.entity;

import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-07-27 16:39:48
 */
public class BannerInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//创建人
	private Integer userId;
	//标题
	private String title;
	//图片
	private String mainPic;
	//链接
	private String linkUrl;
	//排序
	private Integer orderBy;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//状态
	private Integer status;
	//创建时间
	private String createTime = DateUtils.toDate(new Date(),Constants.DatetimePattern.DP19);
	//删除状态
	private Integer delStatus = 1;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：创建人
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：图片
	 */
	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}
	/**
	 * 获取：图片
	 */
	public String getMainPic() {
		return mainPic;
	}
	/**
	 * 设置：链接
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	/**
	 * 获取：链接
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderBy() {
		return orderBy;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
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
