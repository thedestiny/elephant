package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.entity.Log;
import com.destiny.elephant.mapper.LogMapper;
import com.destiny.elephant.service.LogService;
import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    public List<Log> querySelfMonthDataByUser(){

        return new ArrayList<Log>();
    }



    @Override
    public List<Integer> selectSelfMonthData() {
        List<Map> list = baseMapper.selectSelfMonthData();
        //补全数据库中不存在的日期，订单数为0
        List<String> dayList = Lists.newArrayList();
        for (int i = -14; i <= 0; i++) {
            DateTime dateTime = DateUtil.offsetDay(new Date(), i);
            dayList.add(dateTime.toString("yyyy-MM-dd"));
        }
        List<Integer> pv = Lists.newArrayList();
        for (int i = 0; i < dayList.size(); i++) {
            Integer total = 0;
            for (Map map : list) {
                String date = (String) map.get("days");
                total = Integer.valueOf(map.get("total").toString());
                if (date.equalsIgnoreCase(dayList.get(i))) {
                    break;
                } else {
                    total = 0;
                }
            }
            pv.add(total);
        }
        return pv;
    }
}
