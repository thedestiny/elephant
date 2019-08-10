package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.entity.BlogArticle;
import com.destiny.elephant.entity.BlogChannel;
import com.destiny.elephant.entity.VO.ZtreeVO;
import com.destiny.elephant.mapper.BlogChannelMapper;
import com.destiny.elephant.service.BlogChannelService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客栏目 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogChannelServiceImpl extends ServiceImpl<BlogChannelMapper, BlogChannel> implements BlogChannelService {

    @Cacheable(value = "channelData", key = "'articleZtree'", unless = "#result == null or #result.size() == 0")
    @Override
    public List<ZtreeVO> selectZtreeData() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("pid", null);
        return baseMapper.selectZtreeData(map);
    }

    @Cacheable(value = "channelData", key = "'channelList'", unless = "#result == null or #result.size() == 0")
    @Override
    public List<BlogChannel> selectChannelList() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("parentId", null);
        List<BlogChannel> list = Lists.newArrayList();
        try {
            list = baseMapper.selectChannelData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Caching(evict = {
            @CacheEvict(value = "channelData", allEntries = true),
            @CacheEvict(value = "myarticle", allEntries = true),
            @CacheEvict(value = "oneArticle", allEntries = true),
            @CacheEvict(value = "blogTagsData", allEntries = true)
    })
    @Override
    public void saveOrUpdateChannel(BlogChannel blogChannel) {
        try {
            saveOrUpdate(blogChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCountByName(String name) {
        QueryWrapper<BlogChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.eq("del_flag", false);
        return count(wrapper);
    }

    @Cacheable(value = "channelData", key = "'blog_channel_top_limit'+#limit", unless = "#result == null or #result.size() == 0")
    @Override
    public List<BlogChannel> getChannelListByWrapper(int limit, QueryWrapper<BlogChannel> wrapper) {
        IPage<BlogChannel> page = new Page<>(1, limit);

        return page(page, wrapper).getRecords();
    }

    @Cacheable(value = "channelData", key = "'blog_parent_channel_list_'+#channelId", unless = "#result == null or #result.size() == 0")
    @Override
    public List<BlogChannel> getParentsChannel(Long channelId) {
        QueryWrapper<BlogChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("id", channelId);
        BlogChannel blogChannel = getOne(wrapper);
        String[] parentIds = blogChannel.getParentIds().split(",");
        List<Long> ids = Lists.newArrayList();
        for (int i = 0; i < parentIds.length; i++) {
            ids.add(Long.valueOf(parentIds[i]));
        }
        List<BlogChannel> channelList = (List<BlogChannel>) listByIds(ids);
        return channelList;
    }

    @Override
    public BlogChannel getChannelByHref(String href) {
        QueryWrapper<BlogChannel> wrapper = new QueryWrapper<BlogChannel>();
        wrapper.eq("del_flag", false);
        wrapper.eq("href", href);
        BlogChannel c = getOne(wrapper);
        return c;
    }

    @Override
    public Integer selectMaxSort(BlogChannel article) {
        return baseMapper.selectMaxSort(article);
    }
}
