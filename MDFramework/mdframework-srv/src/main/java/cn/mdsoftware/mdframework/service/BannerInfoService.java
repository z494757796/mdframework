package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.entity.BannerInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-07-27 16:39:48
 */
public interface BannerInfoService {
	
	BannerInfoDO get(Integer id);
	
	List<BannerInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BannerInfoDO bannerInfo);
	
	int update(BannerInfoDO bannerInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
