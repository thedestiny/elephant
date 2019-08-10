package com.destiny.elephant.util.quartz.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.destiny.elephant.entity.BlogArticle;
import com.destiny.elephant.service.BlogArticleService;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangl on 2018/1/26.
 * todo: 系统定时任务
 */
@Component("systemTask")
public class SystemTask {

    private static Log log = LogFactory.get();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BlogArticleService blogArticleService;


    /**
     * 同步文章点击量
     *
     * @param params
     */
    public void synchronizationArticleView(String params) {

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        List<BlogArticle> list = blogArticleService.list(wrapper);
        for (BlogArticle article : list) {
            String key = "article_click_id_" + article.getId();
            if (operations.get(key) != null) {
                Integer count = Integer.valueOf(operations.get(key).toString());
                if (count > 0) {
                    article.setClick(blogArticleService.getArticleClick(article.getId()));
                    if (StringUtils.isNotBlank(params)) {
                        article.setUpdateId(Long.valueOf(params));
                    }
                    blogArticleService.updateById(article);
                }
            }
        }
    }

    /**
     * 生成文章搜索索引
     */
    public void createArticleIndex(String params) {
        blogArticleService.createArticleIndex();
    }

}
