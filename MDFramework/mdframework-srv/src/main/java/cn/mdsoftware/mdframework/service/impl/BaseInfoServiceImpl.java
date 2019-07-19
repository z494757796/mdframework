package cn.mdsoftware.mdframework.service.impl;

import cn.mdsoftware.mdframework.bean.entity.BaseInfoDO;
import cn.mdsoftware.mdframework.dao.BaseInfoDao;
import cn.mdsoftware.mdframework.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class BaseInfoServiceImpl implements BaseInfoService {
	@Autowired
	private BaseInfoDao baseInfoMapper;
	
	@Override
	public BaseInfoDO get(Integer id){
		return baseInfoMapper.get(id);
	}
	
	@Override
	public List<BaseInfoDO> list(Map<String, Object> map){
		return baseInfoMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseInfoMapper.count(map);
	}
	
	@Override
	public int save(BaseInfoDO baseInfo){
		return baseInfoMapper.save(baseInfo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(BaseInfoDO baseInfo){
		return baseInfoMapper.update(baseInfo);
	}
	
	@Override
	public int remove(Integer id){
		return baseInfoMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return baseInfoMapper.batchRemove(ids);
	}
	
}
