package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.dao.DynamicDao;
import cn.zzuzl.xblog.exception.ErrorCode;
import cn.zzuzl.xblog.exception.ServiceException;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.Dynamic;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 动态相关service
 */
@Service
public class DynamicService {
    @Resource
    private DynamicDao dynamicDao;

    /**
     * 获取用户的动态
     *
     * @param userId
     * @param page
     * @param count
     * @return
     */
    public Result<Dynamic> getDynamics(int userId, int page, int count) {
        if (userId < 1) {
            return null;
        }
        page = page < 1 ? 1 : page;
        count = count < 1 ? Common.DEFAULT_ITEM_COUNT : count;
        Result<Dynamic> result = new Result<Dynamic>(true);
        result.setList(dynamicDao.getDynamics(userId, (page - 1) * count, count));
        result.setTotalItem(dynamicDao.getDynamicCount(userId));
        result.setPage(page);
        result.setPageSize(count);

        return result;
    }

    /**
     * 插入动态
     *
     * @param dynamic
     * @return
     */
    public Result insertDynamic(Dynamic dynamic) {
        Result result = new Result();

        if (dynamicDao.insertDynamic(dynamic) > 0) {
            result.setSuccess(true);
        } else {
            throw new ServiceException(ErrorCode.DATA_ERROR, ErrorCode.DATA_ERROR.getDefaultMsg());
        }
        return result;
    }

    /**
     * 删除动态
     *
     * @param id
     * @return
     */
    public Result deleteDynamic(int id) {
        Result result = new Result();

        if (dynamicDao.deleteDynamic(id) > 0) {
            result.setSuccess(true);
        } else {
            throw new ServiceException(ErrorCode.DATA_ERROR, ErrorCode.DATA_ERROR.getDefaultMsg());
        }
        return result;
    }

    /**
     * 通过id获取动态
     *
     * @param id
     * @return
     */
    public Dynamic getDynamicById(int id) {
        if (id < 1) {
            return null;
        }
        return dynamicDao.getDynamicById(id);
    }
}
