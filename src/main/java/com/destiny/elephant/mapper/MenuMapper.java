package com.destiny.elephant.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.elephant.entity.Menu;
import com.destiny.elephant.entity.VO.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2017-10-31
 */

public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> showAllMenusList(Map map);

    List<Menu> getMenus(Map map);

    List<ShowMenu> selectShowMenuByUser(Map<String,Object> map);

    Integer selectMaxSort(Menu menu1);
}