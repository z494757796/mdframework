package cn.mdsoftware.mdframework.bean.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 县级市数据库
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2019-02-21 14:14:42
 */
public class CityDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//地级市id
	private Integer fatherid;
	//县级市id
	private Long cityid;
	//
	private String city;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：地级市id
	 */
	public void setFatherid(Integer fatherid) {
		this.fatherid = fatherid;
	}
	/**
	 * 获取：地级市id
	 */
	public Integer getFatherid() {
		return fatherid;
	}
	/**
	 * 设置：县级市id
	 */
	public void setCityid(Long cityid) {
		this.cityid = cityid;
	}
	/**
	 * 获取：县级市id
	 */
	public Long getCityid() {
		return cityid;
	}
	/**
	 * 设置：
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：
	 */
	public String getCity() {
		return city;
	}
}
