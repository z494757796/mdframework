package cn.mdsoftware.mdframework.bean.entity;

import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * 组织架构
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-12-07 15:24:13
 */
public class OrganizationInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//父级id
	private Integer parentId;
	private String parentName;
	//所有父级id
	private String allParentIds;
	//名称
	private String name;
	//创建人
	private Integer createId;
	//创建时间
	private String createTime= DateUtils.toDate(new Date(), Constants.DatetimePattern.DP19);
	//更新人
	private Integer updateId;
	//更新时间
	private String updateTime= DateUtils.toDate(new Date(), Constants.DatetimePattern.DP19);
	//状态
	private Integer status;
	//删除状态
	private Integer delStatus=1;


	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	private Integer orderBy;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

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
	 * 设置：父级id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级id
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：所有父级id
	 */
	public void setAllParentIds(String allParentIds) {
		this.allParentIds = allParentIds;
	}
	/**
	 * 获取：所有父级id
	 */
	public String getAllParentIds() {
		return allParentIds;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getCreateId() {
		return createId;
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
	 * 设置：更新人
	 */
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	/**
	 * 获取：更新人
	 */
	public Integer getUpdateId() {
		return updateId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public String getUpdateTime() {
		return updateTime;
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
