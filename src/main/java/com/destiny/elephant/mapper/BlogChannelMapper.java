package com.destiny.elephant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.elephant.entity.BlogArticle;
import com.destiny.elephant.entity.VO.ZtreeVO;
import com.destiny.elephant.entity.BlogChannel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客栏目 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface BlogChannelMapper extends BaseMapper<BlogChannel> {

    List<ZtreeVO> selectZtreeData(Map<String,Object> map);

    List<BlogChannel> selectChannelData(Map<String, Object> map);

    Integer selectMaxSort(BlogChannel article);
}
