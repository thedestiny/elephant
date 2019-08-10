package com.destiny.elephant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.destiny.elephant.entity.Menu;
import com.destiny.elephant.entity.VO.ShowMenu;
import com.destiny.elephant.entity.VO.ZtreeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangl
 * @since 2017-10-31
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectAllMenus(Map<String,Object> map);

    List<ZtreeVO> showTreeMenus();

    List<ShowMenu> getShowMenuByUser(Long id);

    void saveOrUpdateMenu(Menu menu);

    int getCountByPermission(String permission);

    int getCountByName(String name);

    Integer selectMaxSort(Menu menu1);
}
