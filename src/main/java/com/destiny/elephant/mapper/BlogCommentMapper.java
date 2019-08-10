package com.destiny.elephant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.elephant.entity.BlogComment;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客评论 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface BlogCommentMapper extends BaseMapper<BlogComment> {

    /**
     * 查询文章评论 手动分页
     * @param map
     * @return
     */
    List<BlogComment> selectArticleComments(Map<String, Object> map);

    Integer selectArticleCommentsCount(Map<String, Object> map);

    List<BlogComment> selectArticleCommentsByPlus(Map<String, Object> map, Page page);

    List<BlogComment> getCommentByReplyId(Long replyId);

    Integer selectMaxSort(BlogComment comment);
}
