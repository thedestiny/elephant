package com.destiny.elephant.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.destiny.elephant.base.DataEntity;
import lombok.Data;

/**
 * <p>
 * 文件上传配置
 * </p>
 *
 * @author wangl
 * @since 2018-07-06
 */
@Data
@TableName("upload_info")
public class UploadInfo extends DataEntity<UploadInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 本地window系统上传路径
     */
    @TableField(value = "local_window_url")
    private String localWindowUrl;
    /**
     * 本地LINUX系统上传路径
     */
    @TableField(value = "local_linux_url")
    private String localLinuxUrl;
    /**
     * 七牛前缀路径
     */
    @TableField(value = "qiniu_base_path")
    private String qiniuBasePath;
    /**
     * 七牛bucket的目录名称
     */
    @TableField(value = "qiniu_bucket_name")
    private String qiniuBucketName;
    /**
     * 七牛文件存储目录
     */
    @TableField(value = "qiniu_dir", updateStrategy = FieldStrategy.IGNORED)
    private String qiniuDir;
    /**
     * 七牛qiniuAccess值
     */
    @TableField(value = "qiniu_access_key")
    private String qiniuAccessKey;
    /**
     * 七牛qiniuKey的值
     */
    @TableField(value = "qiniu_secret_key")
    private String qiniuSecretKey;
    /**
     * 七牛上传测试结果
     */
    @TableField("qiniu_test_access")
    private Boolean qiniuTestAccess;
    /**
     * 阿里云前缀路径
     */
    @TableField(value = "oss_base_path")
    private String ossBasePath;
    /**
     * 阿里云bucket的目录名称
     */
    @TableField(value = "oss_bucket_name")
    private String ossBucketName;
    /**
     * 阿里云文件上传目录
     */
    @TableField(value = "oss_dir", updateStrategy = FieldStrategy.IGNORED)
    private String ossDir;
    /**
     * 阿里云ACCESS_KEY_ID值
     */
    @TableField(value = "oss_key_id")
    private String ossKeyId;
    /**
     * 阿里云ACCESS_KEY_SECRET
     */
    @TableField(value = "oss_key_secret")
    private String ossKeySecret;
    /**
     * 阿里云ENDPOINT值
     */
    @TableField(value = "oss_endpoint")
    private String ossEndpoint;
    /**
     * 阿里云上传测试结果
     */
    @TableField(value = "oss_test_access")
    private Boolean ossTestAccess;

   
}
