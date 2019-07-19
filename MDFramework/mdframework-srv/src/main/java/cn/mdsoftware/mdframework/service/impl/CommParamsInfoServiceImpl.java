package cn.mdsoftware.mdframework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.mdsoftware.mdframework.dao.CommParamsInfoDao;
import cn.mdsoftware.mdframework.bean.entity.CommParamsInfoDO;
import cn.mdsoftware.mdframework.service.CommParamsInfoService;



@Service
public class CommParamsInfoServiceImpl implements CommParamsInfoService {
	@Autowired
	private CommParamsInfoDao commParamsInfoMapper;
	
	@Override
	public CommParamsInfoDO get(Integer id){
		return commParamsInfoMapper.get(id);
	}
	
	@Override
	public List<CommParamsInfoDO> list(Map<String, Object> map){
		return commParamsInfoMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return commParamsInfoMapper.count(map);
	}
	
	@Override
	public int save(CommParamsInfoDO commParamsInfo){
		return commParamsInfoMapper.save(commParamsInfo);
	}
	
	@Override
	public int update(CommParamsInfoDO commParamsInfo){
		return commParamsInfoMapper.update(commParamsInfo);
	}
	
	@Override
	public int remove(Integer id){
		return commParamsInfoMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return commParamsInfoMapper.batchRemove(ids);
	}

	@Override
	public List<Map<String,Object>> listByUnique(Map<String, Object> map){return commParamsInfoMapper.listByUnique(map);}
	public List<Map<String,Object>> listByUniqueApi(Map<String, Object> map){return commParamsInfoMapper.listByUniqueApi(map);}

}
