package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.mapper.QuartzTaskLogMapper;
import com.destiny.elephant.entity.QuartzTaskLog;
import com.destiny.elephant.service.QuartzTaskLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 任务执行日志 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTaskLogServiceImpl extends ServiceImpl<QuartzTaskLogMapper, QuartzTaskLog> implements QuartzTaskLogService {

}
