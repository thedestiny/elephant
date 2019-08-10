package com.destiny.elephant.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.mapper.BlogCommentMapper;
import com.destiny.elephant.entity.BlogComment;
import com.destiny.elephant.service.BlogCommentService;
import com.destiny.elephant.util.Constants;
import com.google.common.collect.Maps;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客评论 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements BlogCommentService {

    @Override
    public Integer getMaxFloor(Long articleId) {

        BlogComment comment = new BlogComment();

       /* Object o = null; */
        if (articleId != null) {
            comment.setArticleId(articleId);
         /*   o = selectObj(Condition.create().setSqlSelect("max(floor)").eq("article_id", articleId));*/
        } else {
            comment.setType(Constants.COMMENT_TYPE_LEVING_A_MESSAGE);
            /*o = selectObj(Condition.create().setSqlSelect("max(floor)").eq("type", Constants.COMMENT_TYPE_LEVING_A_MESSAGE));*/
        }

        Integer count = baseMapper.selectMaxSort(comment);

        Integer floor = 0;
        if (count != null) {
            floor = count;
        }
        return floor;
    }

    @Override
    public Integer getMaxFloorByReply(Long replyId) {


        BlogComment comment = new BlogComment();
        comment.setReplyId(replyId);
        /*Object o = selectObj(Condition.create().setSqlSelect("max(floor)").eq("reply_id", replyId));*/
        Integer count = baseMapper.selectMaxSort(comment);
        Integer floor = 0;
        if (count != null) {
            floor = count;
        }
        return floor;
    }

    @Override
    public Page<BlogComment> getArticleComments(Long articleId, Integer type, Page<BlogComment> page) {
        Map<String, Object> map = Maps.newHashMap();
        if (articleId != null) {
            map.put("articleId", articleId);
        }
        map.put("type", type);
//        map.put("start",page.getCurrent() == 1 ? 0 : (page.getCurrent()-1)*page.getLimit());
//        map.put("limit",page.getLimit());
//        List<BlogComment> list = baseMapper.selectArticleComments(map);
//        Integer total = baseMapper.selectArticleCommentsCount(map);
//        page.setRecords(list);
//        page.setTotal(total);
        List<BlogComment> list = baseMapper.selectArticleCommentsByPlus(map, page);
        page.setRecords(list);
        return page;
    }

    @CacheEvict(value = "commentData", key = "'article_'+#blogComment.articleId+'_commentcount'")
    @Override
    public void saveOrUpdateBlogComment(BlogComment blogComment) {
        saveOrUpdate(blogComment);
    }

    @Cacheable(value = "commentData", key = "'article_'+#articleId+'_commentcount'")
    @Override
    public Integer getArticleCommentsCount(Long articleId) {
        QueryWrapper<BlogComment> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("type", Constants.COMMENT_TYPE_ARTICLE_COMMENT);
        wrapper.eq("article_id", articleId);
        Integer count = count(wrapper);
        return count;
    }
}
