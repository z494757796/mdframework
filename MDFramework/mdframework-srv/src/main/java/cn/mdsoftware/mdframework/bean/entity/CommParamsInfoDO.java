package cn.mdsoftware.mdframework.bean.entity;


import cn.mdsoftware.mdframework.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-01-24 11:20:01
 */
public class CommParamsInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//所属类型名称
	private String typeName;
	//类型唯一标识
	private String uniqueType;
	//数据名称
	private String keyName;
	//数据对应值
	private String keyValue;
	//排序
	private Integer orderBy;
	//创建时间
	private String createTime= DateUtils.toDate(new Date(),"yyyy-MM-dd HH:mm:ss");
	//删除状态
	private Integer delStatus=1;

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
	 * 设置：所属类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：所属类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：类型唯一标识
	 */
	public void setUniqueType(String uniqueType) {
		this.uniqueType = uniqueType;
	}
	/**
	 * 获取：类型唯一标识
	 */
	public String getUniqueType() {
		return uniqueType;
	}
	/**
	 * 设置：数据名称
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	 * 获取：数据名称
	 */
	public String getKeyName() {
		return keyName;
	}
	/**
	 * 设置：数据对应值
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	/**
	 * 获取：数据对应值
	 */
	public String getKeyValue() {
		return keyValue;
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
