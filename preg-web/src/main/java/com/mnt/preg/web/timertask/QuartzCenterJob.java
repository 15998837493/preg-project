
package com.mnt.preg.web.timertask;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnt.health.utils.times.JodaTimeTools;

/**
 * 定时任务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-17 zcq 初版
 */
public class QuartzCenterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzCenterJob.class);

    // --------------------------------【代码表】------------------------------------

//    // 代码表--定时任务组
//    final String codeJobGroup = "code";
//
//    // 代码表--开始时间
//    Calendar codeStartTime = JodaTimeTools.getCalendarAfterSecond(10);
//
//    // 代码表--任务触发时间 【服务启动10秒后第一次执行任务，之后每隔12小时执行一次】
//    final String code_trigger_time = codeStartTime.get(Calendar.SECOND) + " " + codeStartTime.get(Calendar.MINUTE) + " "
//            + codeStartTime.get(Calendar.HOUR) + "/12 * * ?";

    // --------------------------------【接诊预约】------------------------------------

    // 接诊预约--定时任务组
    final String diagnosisBookingGroup = "diagnosisBooking";

    // 接诊预约--触发时间【凌晨一点执行定时任务】
    final String booking_trigger_time = "0 0 1 ? * *";

    // --------------------------------【定时任务】------------------------------------

    private SchedulerFactory sf = new StdSchedulerFactory();// 定时任务工厂

    private Scheduler sched = null;// 定时任务程序调度

    final String triggerEndName = "Trigger";// 触发器后缀名

    public boolean start() {
        boolean bResult = false;
        try {
            // 启动链路定时任务
            sched = sf.getScheduler();

//            // 代码表定时任务
//            JobDetail codeJob = new JobDetail(CodeTimerJob.class.getSimpleName(), codeJobGroup, CodeTimerJob.class);
//            CronTrigger codeTrigger = new CronTrigger(CodeTimerJob.class.getSimpleName() + triggerEndName,
//                    codeJobGroup + triggerEndName, CodeTimerJob.class.getSimpleName(),
//                    codeJobGroup,
//                    code_trigger_time);
//            sched.addJob(codeJob, true);
//            Date codeDate = sched.scheduleJob(codeTrigger);

            // 接诊预约定时任务
            JobDetail diagnosisJob = new JobDetail(DiagnosisTimerJob.class.getSimpleName(), diagnosisBookingGroup,
                    DiagnosisTimerJob.class);
            CronTrigger diagnosisTrigger = new CronTrigger(DiagnosisTimerJob.class.getSimpleName() + triggerEndName,
                    diagnosisBookingGroup + triggerEndName, DiagnosisTimerJob.class.getSimpleName(),
                    diagnosisBookingGroup,
                    booking_trigger_time);
            sched.addJob(diagnosisJob, true);
            Date diagnosisDate = sched.scheduleJob(diagnosisTrigger);

            // 启动
            sched.start();
            bResult = sched.isStarted();
            if (bResult) {
//                LOGGER.info("启动-代码表定时任务启动成功,启动时间" + JodaTimeTools.toString(codeDate, JodaTimeTools.FORMAT_2));
                LOGGER.info("启动-接诊表定时任务启动成功,启动时间" + JodaTimeTools.toString(diagnosisDate, JodaTimeTools.FORMAT_2));
            } else {
                LOGGER.error("启动-定时任务失败!");
            }
        } catch (Exception e) {
            LOGGER.error("启动-定时任务失败,出现异常!", e);
        }
        return bResult;
    }

    public boolean shutdown() {
        try {
            if (sched != null && sched.isStarted()) {
                sched.shutdown();
                return sched.isShutdown();
            }
        } catch (SchedulerException e) {
            LOGGER.error("停止-定时任务失败,出现异常!", e);
        }
        return false;
    }

    public boolean isStart() {
        if (sched != null) {
            try {
                return sched.isStarted();
            } catch (SchedulerException e) {
                LOGGER.error("定时任务状态-取定时任务状态失败,出现异常!", e);
            }
        }
        return false;
    }

}
