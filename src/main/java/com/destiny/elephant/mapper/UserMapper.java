package com.destiny.elephant.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.elephant.entity.Role;
import com.destiny.elephant.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2017-10-31
 */
public interface UserMapper extends BaseMapper<User> {

    User selectUserByMap(Map<String, Object> map);

    User selectUserByEntity(User user);

    void saveUserRoles(@Param("userId") Long id, @Param("roleIds") Set<Role> roles);

    void dropUserRolesByUserId(@Param("userId") Long userId);

    Map selectUserMenuCount();
}