
package com.mnt.preg.web.timertask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.DiagnosisHospitalItemServiceImpl;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregDiagnosisServiceImpl;
import com.mnt.preg.web.CacheProjectInfo;

/**
 * 接诊信息定时任务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-17 zcq 初版
 */
public class DiagnosisTimerJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosisTimerJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("定时任务--开始执行---接诊信息");
        // 方法执行开始时间
        long starttime = System.currentTimeMillis();
        WebApplicationContext applicationContext = CacheProjectInfo.getInstance().getApplicationContext();
        if (applicationContext == null) {
            throw new JobExecutionException("spring 容器未初始化");
        }
        // 执行接诊预约
        PregDiagnosisService diagnosisService = applicationContext.getBean(PregDiagnosisServiceImpl.class);
        diagnosisService.addDiagnosisTimer();

        // 删除空的报告单
        DiagnosisHospitalItemService diagnosisHospitalItemService = applicationContext
                .getBean(DiagnosisHospitalItemServiceImpl.class);
        diagnosisHospitalItemService.deleteDiagnosisHospitalEmptyReport("");

        // 方法执行时间
        long processTime = System.currentTimeMillis() - starttime;
        LOGGER.info("定时任务--执行完成---接诊信息----运行时间" + processTime + "ms;");
    }
}
