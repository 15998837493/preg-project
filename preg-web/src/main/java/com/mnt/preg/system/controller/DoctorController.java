
package com.mnt.preg.system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.system.entity.Doctor;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.pojo.DoctorPojo;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.DoctorService;
import com.mnt.preg.system.service.UserService;
import com.mnt.preg.web.constants.SessionConstant;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 医师出诊排班Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
@Controller
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    @Resource
    private UserService userService;

    @Resource
    private PregDiagnosisService diagnosisService;

    /**
     * 初始化页面(用户)
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.DOCTOR_VIEW)
    public @ResponseBody
    WebResult<UserPojo> querySymptoms(User condition) {
        WebResult<UserPojo> webResult = new WebResult<UserPojo>();
        webResult.setData(userService.queryUsers(condition));
        return webResult;
    }

    /**
     * 回显
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.DOCTOR_MODEL_VIEW)
    public @ResponseBody
    WebResult<DoctorPojo> queryDoctorModelView(Doctor condition) {
        WebResult<DoctorPojo> webResult = new WebResult<DoctorPojo>();
        webResult.setData(doctorService.queryDoctorByCondition(condition));
        return webResult;
    }

    /**
     * 添加数据(用户id不可以重复，只能有一个)
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.DOCTOR_ADD)
    public @ResponseBody
    WebResult<DoctorPojo> addDoctor(Doctor doctor) {
        WebResult<DoctorPojo> webResult = new WebResult<DoctorPojo>();
        if (doctor.getUserId().contains(",")) {// 有多个用户
            String[] id = doctor.getUserId().split(",");
            for (int x = 0; x < id.length; x++) {
                Doctor doctors = new Doctor();
                doctors.setUserId(id[x]);
                doctors.setScheduleWeek(doctor.getScheduleWeek());
                doctors.setScheduleMaxPerson(doctor.getScheduleMaxPerson());
                doctorService.removeDoctor(doctors.getUserId());
                webResult.setSuc(doctorService.saveDoctor(doctors), WebMsgConstant.success_message);
            }
        } else {// 只有一个用户
            doctorService.removeDoctor(doctor.getUserId());
            webResult.setSuc(doctorService.saveDoctor(doctor), WebMsgConstant.success_message);
        }
        return webResult;
    }

    /**
     * 医嘱_复查预约（查询复查预约具体信息）
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.DOCTOR_QUERY)
    public @ResponseBody
    WebResult<DoctorPojo> queryDoctor(String diagnosisId, HttpServletRequest request) {
        WebResult<DoctorPojo> webResult = new WebResult<DoctorPojo>();
        List<DoctorPojo> list = new ArrayList<DoctorPojo>();
        Integer scheduleMaxPerson = 0;// 可预约最大人数
        LoginUser login = (LoginUser) request.getSession().getAttribute(SessionConstant.LOGIN_USER);
        String userId = login.getUserPojo().getUserId();// 当前登录的用户ID
        Doctor condition = new Doctor();
        condition.setUserId(userId);
        List<DoctorPojo> user = doctorService.queryDoctorByCondition(condition);// 根据userId查出来的只可能是1条或0条
        if (user.size() == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String scheduleWeek = user.get(0).getScheduleWeek();// 工作日时间
            scheduleMaxPerson = user.get(0).getScheduleMaxPerson();// 可预约最大人数
            for (int x = 1; x <= 35; x++) {// 5周后（一周按7天算，不包括当日）
                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, x);
                String time = sdf.format(c.getTime());// 格式：2017-10-20
                String week = this.getWeekByData(time);// 格式：周五
                if (scheduleWeek.contains(",")) {// 多个工作日
                    String[] weeks = scheduleWeek.split(",");
                    for (int y = 0; y < weeks.length; y++) {
                        DoctorPojo book = new DoctorPojo();
                        if (week.equals(weeks[y].substring(0, 2))) {
                            book.setScheduleWeek(weeks[y]);
                            book.setData(time);
                            list.add(book);
                        }
                    }
                } else {// 一个工作日(工作日永远不可能为空，不用做非空判断)
                    if (week.equals(scheduleWeek.substring(0, 2))) {
                        DoctorPojo book = new DoctorPojo();
                        book.setScheduleWeek(scheduleWeek);
                        book.setData(time);
                        list.add(book);
                    }
                }
            }
        }
        if (list.size() > 0) {// 有数据则添加预约状态
            for (DoctorPojo pojo : list) {
                DiagnosisBookingCondition bookingCondition = new DiagnosisBookingCondition();
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                String str = pojo.getData();
                int realCount = 0;// 已预约人数
                Date d;
                try {
                    d = sim.parse(str);
                    bookingCondition.setBookingDate(d);
                    bookingCondition.setBookingVisitTime(pojo.getScheduleWeek());
                    List<DiagnosisBookingPojo> DiagnosisBookingPojoList = diagnosisService
                            .queryDiagnosisBookings(bookingCondition);
                    if (DiagnosisBookingPojoList.size() > 0) {
                        for (DiagnosisBookingPojo book : DiagnosisBookingPojoList) {
                            if (diagnosisService.getDiagnosis(book.getDiagnosisId()) != null) {// 必须能在接诊表里查到数据的才算已预约的
                                realCount += 1;
                            }
                        }
                    }
                    pojo.setScheduleRealPerson(realCount);// 已预约人数
                    pojo.setScheduleMaxPerson(scheduleMaxPerson);// 最大可预约人数
                    DiagnosisBookingCondition bookingListCondition = new DiagnosisBookingCondition();
                    bookingListCondition.setDiagnosisId(diagnosisId);
                    pojo.setBookingList(diagnosisService.queryDiagnosisBookings(bookingListCondition));// 复诊预约信息
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            // 按日期正序
            for (int x = 0; x < list.size() - 1; x++) {
                for (int y = 0; y < list.size() - 1 - x; y++) {
                    int one = Integer.parseInt(list.get(y).getData().split("-")[0]
                            + list.get(y).getData().split("-")[1] + list.get(y).getData().split("-")[2]);
                    int two = Integer.parseInt(list.get(y + 1).getData().split("-")[0]
                            + list.get(y + 1).getData().split("-")[1] + list.get(y + 1).getData().split("-")[2]);
                    if (one > two) {
                        DoctorPojo temp = list.get(y);
                        list.set(y, list.get(y + 1));
                        list.set(y + 1, temp);
                    }
                }
            }
        }
        webResult.setData(list);
        return webResult;
    }

    // ---------------------------------------------工具方法------------------------------------------------------------------------------------------------

    /**
     * 返回当前日期是周几
     * 
     * @param 需要传入日期格式为
     *            ：2017-10-20
     * @return
     */
    private String getWeekByData(String newtime) {
        String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(newtime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
    }
}
