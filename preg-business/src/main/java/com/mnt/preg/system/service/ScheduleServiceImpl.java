
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.system.dao.ScheduleDao;
import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.pojo.SchedulePojo;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    /** 元素库 */
    @Resource
    private ScheduleDao scheduleDao;

    @Override
    @Transactional(readOnly = true)
    public List<SchedulePojo> queryScheduleByCondition(Schedule condition) {
        return scheduleDao.queryScheduleByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public SchedulePojo saveSchedule(Schedule schedule) {
        if (schedule == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        String id = (String) scheduleDao.save(schedule);
        return scheduleDao.getTransformerBean(id, Schedule.class, SchedulePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public SchedulePojo updateSchedule(Schedule schedule) {
        if (schedule == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("scheduleId", HQLSymbol.EQ.toString(), schedule.getScheduleId()));
        scheduleDao.updateHQL(schedule, conditionParams);
        return scheduleDao.getTransformerBean(schedule.getScheduleId(), Schedule.class, SchedulePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeSchedule(String id) {
        // 根据主键id查询
        Schedule schedule = scheduleDao.get(Schedule.class, id);
        if (schedule == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        scheduleDao.deleteSchedules(schedule.getScheduleId());
    }

}
