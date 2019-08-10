package com.destiny.elephant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.destiny.elephant.entity.Resource;
/**
 * <p>
 * 系统资源 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-14
 */
public interface ResourceService extends IService<Resource> {

    int getCountByHash(String hash);

    Resource getResourceByHash(String hash);

}
