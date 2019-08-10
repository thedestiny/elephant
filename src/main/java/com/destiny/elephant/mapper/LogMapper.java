package com.destiny.elephant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.elephant.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-14
 */
public interface LogMapper extends BaseMapper<Log> {

    List<Map> selectSelfMonthData();
}
