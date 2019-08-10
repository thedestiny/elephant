package com.destiny.elephant.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.service.GroupService;
import com.destiny.elephant.mapper.GroupMapper;
import com.destiny.elephant.entity.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangl
 * @since 2017-10-31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
	
}
