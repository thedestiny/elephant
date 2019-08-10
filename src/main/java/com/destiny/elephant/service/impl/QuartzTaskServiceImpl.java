package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.elephant.entity.QuartzTask;
import com.destiny.elephant.mapper.QuartzTaskMapper;
import com.destiny.elephant.service.QuartzTaskService;
import com.destiny.elephant.util.Constants;
import com.destiny.elephant.util.quartz.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTaskServiceImpl extends ServiceImpl<QuartzTaskMapper, QuartzTask> implements QuartzTaskService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     * todo init
     */
    @PostConstruct
    public void init() {
        QueryWrapper<QuartzTask> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        List<QuartzTask> scheduleJobList = list(wrapper);
        for (QuartzTask scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public QuartzTask queryObject(Long jobId) {
        return baseMapper.selectById(jobId);
    }

    @Override
    public IPage<QuartzTask> queryList(QueryWrapper<QuartzTask> wrapper, Page<QuartzTask> page) {
        return page(page, wrapper);
    }

    @Override
    public void saveQuartzTask(QuartzTask quartzTask) {
        baseMapper.insert(quartzTask);
        ScheduleUtils.createScheduleJob(scheduler, quartzTask);
    }

    @Override
    public void updateQuartzTask(QuartzTask quartzTask) {
        baseMapper.updateById(quartzTask);
        ScheduleUtils.updateScheduleJob(scheduler, quartzTask);
    }

    @Override
    public void deleteBatchTasks(List<Long> ids) {
        for (Long id : ids) {
            ScheduleUtils.deleteScheduleJob(scheduler, id);
        }
        removeByIds(ids);
    }

    @Override
    public int updateBatchTasksByStatus(List<Long> ids, Integer status) {
        Collection<QuartzTask> list = listByIds(ids);
        for (QuartzTask task : list) {
            task.setStatus(status);
        }
        updateBatchById(list);
        return 0;
    }

    @Override
    public void run(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    @Override
    public void pause(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        updateBatchTasksByStatus(jobIds, Constants.QUARTZ_STATUS_PAUSE);
    }

    @Override
    public void resume(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatchTasksByStatus(jobIds, Constants.QUARTZ_STATUS_NORMAL);
    }
}
