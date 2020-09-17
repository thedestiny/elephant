
package com.destiny.elephant.base;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.destiny.elephant.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * 数据Entity类
 */
@Data
public abstract class DataEntity<T extends Model<?>> extends BaseEntity<T> {

    private static final long serialVersionUID = 1575482711707950988L;
    /**
     *  创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected Long createId;

    /**
     * 创建日期
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createDate;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    protected Long updateId;

    /**
     * 更新日期
      */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateDate;

    /**
     * 删除标记（Y：正常；N：删除；A：审核；）
     */
    @TableField(value = "del_flag")
    protected Boolean delFlag;

    /**
     * 备注
     */
    @TableField(value = "remarks",updateStrategy= FieldStrategy.IGNORED)
    protected String remarks;

    /**
     * 创建着
     */
    @TableField(exist = false)
    protected User createUser;

    /**
     * 修改者
     */
    @TableField(exist = false)
    protected User updateUser;
	
	
	public DataEntity() {
	}
	
	public DataEntity(Long id) {
		super(id);
	}
}
