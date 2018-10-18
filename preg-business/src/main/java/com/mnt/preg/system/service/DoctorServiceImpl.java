
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
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.dao.PregDiagnosisDAO;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.system.dao.DoctorDao;
import com.mnt.preg.system.entity.Doctor;
import com.mnt.preg.system.pojo.DoctorPojo;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Resource
    private DoctorDao doctorDao;
    
    @Resource
    private PregDiagnosisDAO PregDiagnosisDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorPojo> queryDoctorByCondition(Doctor condition) {
        return doctorDao.queryDoctorByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public DoctorPojo saveDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        String id = (String) doctorDao.save(doctor);
        return doctorDao.getTransformerBean(id, Doctor.class, DoctorPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public DoctorPojo updateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("scheduleId", HQLSymbol.EQ.toString(), doctor.getScheduleId()));
        doctorDao.updateHQL(doctor, conditionParams);
        return doctorDao.getTransformerBean(doctor.getScheduleId(), Doctor.class, DoctorPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeDoctor(String id) {
        doctorDao.deleteDoctorByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryBooks(DiagnosisCondition condition) {
       return PregDiagnosisDAO.queryDiagnosis(condition);
    }
}
