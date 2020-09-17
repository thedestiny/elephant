package com.destiny.elephant.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.destiny.elephant.base.DataEntity;
import com.google.common.collect.Sets;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户实体
 */
@Data
@TableName("sys_user")
public class User extends DataEntity<User> implements Serializable {
	
	private static final long serialVersionUID = 5279517318436710018L;
	/**
	 * 登录名
	 */
	@TableField("login_name")
	private String loginName;
	/**
	 * 昵称
	 */
	@TableField(value = "nick_name", updateStrategy = FieldStrategy.IGNORED)
	private String nickName;
	/**
	 * 密码
	 */
	@JSONField(serialize = false)
	private String password;
	/**
	 * shiro加密盐
	 */
	@JSONField(serialize = false)
	private String salt;
	/**
	 * 手机号码
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String tel;
	/**
	 * 邮箱地址
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String email;
	
	/**
	 * 账户是否锁定
	 */
	private Boolean locked;
	
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String icon;
	
	@TableField(exist = false)
	private Set<Role> roleLists = Sets.newHashSet();
	
	@TableField(exist = false)
	private Set<Menu> menus = Sets.newHashSet();
	
	
}
