package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.mapper.ResourceMapper;
import com.destiny.elephant.entity.Resource;
import com.destiny.elephant.service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统资源 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-14
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public int getCountByHash(String hash) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("hash", hash);
        return count(wrapper);
    }

    @Override
    public Resource getResourceByHash(String hash) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("hash", hash);
        return getOne(wrapper);
    }
}
