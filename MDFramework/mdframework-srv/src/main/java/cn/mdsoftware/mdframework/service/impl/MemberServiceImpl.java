package cn.mdsoftware.mdframework.service.impl;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mdsoftware.mdframework.dao.MemberDao;
import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.service.MemberService;



@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public MemberDO get(Integer id){
		return memberDao.get(id);
	}
	
	@Override
	public PageInfo<List<MemberDO>> list(Map<String, Object> map){
	    Integer offset = (Integer)map.get("offset");
        Integer limit = (Integer)map.get("limit");
        if(offset != null && limit != null){
            PageHelper.offsetPage(offset,limit);
        }
        List<MemberDO> list = memberDao.list(map);
		return new PageInfo(list);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return memberDao.count(map);
	}
	
	@Override
	public int save(MemberDO member){
		return memberDao.save(member);
	}
	
	@Override
	public int update(MemberDO member){
		return memberDao.update(member);
	}
	
	@Override
	public int remove(Integer id){
		return memberDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return memberDao.batchRemove(ids);
	}

    @Override
    public HttpResponse<PageInfo> listByWhere(Integer pageNum, Integer pageSize){
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<MemberDO> list = memberDao.list(new HashMap<String,Object>(){{

        }});
        if(list.size() > 0){
            return HttpResponse.createBySuccess(new PageInfo(list));
        }
        return HttpResponse.createByErrorResponseEnum(HttpResponseEnum.NO_RECORDS);
    }

    @Override
    public HttpResponse saveMember(MemberDO member){
        int result = memberDao.save(member);
        if(result == 0){
            return HttpResponse.createByErrorMessage("保存失败");
        }
        return HttpResponse.createBySuccess("保存成功");
    }

    @Override
    public HttpResponse updateMember(MemberDO member){
        int result = memberDao.update(member);
        if(result == 0){
            return HttpResponse.createByErrorMessage("更新失败");
        }
        return HttpResponse.createBySuccess("更新成功");
    }

    @Override
    public HttpResponse<MemberDO> getMember(Integer id){
        MemberDO member = memberDao.get(id);
        if(member != null){
            return HttpResponse.createBySuccess(member);
        }
        return HttpResponse.createByErrorResponseEnum(HttpResponseEnum.NO_RECORDS);
    }

    @Override
    public HttpResponse deleteMember(Integer id){
        int result = memberDao.remove(id);
        if(result == 0){
            return HttpResponse.createByErrorMessage("删除失败");
        }
        return HttpResponse.createBySuccess("删除成功");
    }
	
}
