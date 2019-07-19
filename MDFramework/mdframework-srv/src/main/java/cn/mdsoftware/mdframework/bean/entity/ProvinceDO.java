package cn.mdsoftware.mdframework.bean.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 省份数据库
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2019-02-21 14:14:42
 */
public class ProvinceDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private Integer id;
	//省份id、省份编号
	private Integer provinceid;
	//省份名称
	private String province;

	/**
	 * 设置：主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：省份id、省份编号
	 */
	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}
	/**
	 * 获取：省份id、省份编号
	 */
	public Integer getProvinceid() {
		return provinceid;
	}
	/**
	 * 设置：省份名称
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省份名称
	 */
	public String getProvince() {
		return province;
	}
}
