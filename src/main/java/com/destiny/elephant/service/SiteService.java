package com.destiny.elephant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.destiny.elephant.entity.Site;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangl
 * @since 2017-12-30
 */
public interface SiteService extends IService<Site> {

    Site getCurrentSite();

    void updateSite(Site site);
	
}
