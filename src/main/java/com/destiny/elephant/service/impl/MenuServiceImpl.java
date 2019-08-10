package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.mapper.MenuMapper;
import com.destiny.elephant.entity.Menu;
import com.destiny.elephant.entity.VO.ShowMenu;
import com.destiny.elephant.entity.VO.ZtreeVO;
import com.destiny.elephant.service.MenuService;
import com.destiny.elephant.util.Constants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2017-10-31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Cacheable(value = "allMenus", key = "'allMenus_isShow_'+#map['isShow'].toString()", unless = "#result == null or #result.size() == 0")
    @Override
    public List<Menu> selectAllMenus(Map<String, Object> map) {
        logger.info("all menus ----->");
        return baseMapper.getMenus(map);
    }

    @Caching(evict = {
            @CacheEvict(value = "allMenus", allEntries = true),
            @CacheEvict(value = "user", allEntries = true)
    })
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(Menu menu) {
        saveOrUpdate(menu);
    }

    @Override
    public int getCountByPermission(String permission) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("permission", permission);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public int getCountByName(String name) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("name", name);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public Integer selectMaxSort(Menu menu1) {
        return baseMapper.selectMaxSort(menu1);
    }

    @Override
    public List<ZtreeVO> showTreeMenus() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("is_show", true);
        wrapper.orderByDesc("sort");
        List<Menu> totalMenus = baseMapper.selectList(wrapper);
        List<ZtreeVO> ztreeVOs = Lists.newArrayList();
        return getZTree(null, totalMenus, ztreeVOs);
    }

    @Cacheable(value = "allMenus", key = "'user_menu_'+T(String).valueOf(#id)", unless = "#result == null or #result.size() == 0")
    @Override
    public List<ShowMenu> getShowMenuByUser(Long id) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", id);
        map.put("parentId", Constants.parentId);
        return baseMapper.selectShowMenuByUser(map);
    }

    /**
     * 递归拉取菜单树的数据
     */
    private List<ZtreeVO> getZTree(ZtreeVO tree, List<Menu> total, List<ZtreeVO> result) {
        Long pid = tree == null ? null : tree.getId();
        List<ZtreeVO> childList = Lists.newArrayList();
        for (Menu m : total) {
            if (pid == m.getParentId()) {
                ZtreeVO ztreeVO = new ZtreeVO();
                ztreeVO.setId(m.getId());
                ztreeVO.setName(m.getName());
                ztreeVO.setPid(pid);
                childList.add(ztreeVO);
                getZTree(ztreeVO, total, result);
            }
        }
        if (tree != null) {
            tree.setChildren(childList);
        } else {
            result = childList;
        }
        return result;
    }

}
