package cn.mdsoftware.mdframework.bean.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 地区市数据库
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2019-02-21 14:14:41
 */
public class AreaDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//地级市主键ID
	private Integer id;
	//地级市id
	private Long fatherid;
	//县级id
	private Long areaid;
	//
	private String area;

	/**
	 * 设置：地级市主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：地级市主键ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：地级市id
	 */
	public void setFatherid(Long fatherid) {
		this.fatherid = fatherid;
	}
	/**
	 * 获取：地级市id
	 */
	public Long getFatherid() {
		return fatherid;
	}
	/**
	 * 设置：县级id
	 */
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	/**
	 * 获取：县级id
	 */
	public Long getAreaid() {
		return areaid;
	}
	/**
	 * 设置：
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * 获取：
	 */
	public String getArea() {
		return area;
	}
}
