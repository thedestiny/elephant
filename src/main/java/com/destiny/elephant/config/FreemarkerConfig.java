package com.destiny.elephant.config;

import com.destiny.elephant.freemark.*;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
  freemarker 全局配置
 */
@Component
public class FreemarkerConfig {

    @Autowired
    private Configuration configuration;

    @Autowired
    private SystemDirective systemDirective;

    @Autowired
    private ArticleDirective articleDirective;

    @Autowired
    private IndexArticleDirective indexArticleDirective;

    @Autowired
    private ChannelDirective channelDirective;

    @Autowired
    private ParentChannelListDirective parentChannelListDirective;

    @Autowired
    private ArticleClickTempleModel articleClickTempleModel;

    @Autowired
    private SysUserTemplateModel sysUserTemplateModel;

    @Autowired
    private TagsTemplateModel tagsTemplateModel;

    @Autowired
    private NewCommentArticleTemplateModel newCommentArticleTemplateModel;

    @Autowired
    private LookLikeArticlesTempleModel lookLikeArticlesTempleModel;

    @Autowired
    private CommentNumberTempletModel commentNumberTempletModel;

    @PostConstruct
    public void setSharedVariable() {
        //系统字典标签
        configuration.setSharedVariable("my", systemDirective);
        //博客文章标签
        configuration.setSharedVariable("ar", articleDirective);
        //博客首页文章列表标签
        configuration.setSharedVariable("myindex", indexArticleDirective);
        //博客栏目标签
        configuration.setSharedVariable("mychannel", channelDirective);
        //博客当前栏目所有父目录集合标签
        configuration.setSharedVariable("articleChannelList", parentChannelListDirective);

        //获取文章点击量标签
        configuration.setSharedVariable("clickNumber", articleClickTempleModel);
        //获取文章评论数量
        configuration.setSharedVariable("commentNumber", commentNumberTempletModel);
        //获取系统用户信息
        configuration.setSharedVariable("sysuser", sysUserTemplateModel);
        //获取标签集合
        configuration.setSharedVariable("tags", tagsTemplateModel);
        //最新评论文章集合
        configuration.setSharedVariable("nca", newCommentArticleTemplateModel);
        //当前文章相似的文章
        configuration.setSharedVariable("same", lookLikeArticlesTempleModel);
    }
}
