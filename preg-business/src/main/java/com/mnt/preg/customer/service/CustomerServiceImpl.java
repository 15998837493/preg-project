/**
 * 
 */

package com.mnt.preg.customer.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.Md5;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.maths.CreateRandomCode;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.condition.CustomerCondition;
import com.mnt.preg.customer.condition.ImportResultCondition;
import com.mnt.preg.customer.dao.CustomerDAO;
import com.mnt.preg.customer.entity.Customer;
import com.mnt.preg.customer.entity.ImportResult;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.ImportResultPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.dao.PregArchivesDAO;
import com.mnt.preg.platform.entity.PregArchives;

/**
 * 客户管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

    @Resource
    private CustomerDAO customerDAO;// 患者DAO

    @Resource
    private PregArchivesDAO pregArchivesDAO;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerPojo> queryCustomer(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        List<CustomerPojo> customerList = customerDAO.queryCustomer(condition);
        return customerList;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerCondition queryCustomerForBirthEnding(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        return customerDAO.queryCustomerForBirthEnding(condition);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CustomerCondition queryCustomerForDiagnosis(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        return customerDAO.queryCustomerForDiagnosis(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerCondition queryCustomerForDiagnosisPage(CustomerCondition condition) {
        return customerDAO.queryCustomerForDiagnosisPage(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerPojo getCustomer(String custId) {
        return customerDAO.getCustomer(custId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public CustomerPojo addCustomer(Customer customer) {
        if (customer == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        customer.setCustCode(createCustCode());// 设置客户编码=十位随机数字
        customer.setCustId(this.getInsId() + customer.getCustCode());// 设置主键=机构ID+用户编码
        // 设置其他参数

        // 设置客户密码
        if (StringUtils.isEmpty(customer.getCustPassword())) {
            customer.setCustPassword(this.getParamValue("customer_default_password"));
        }
        try {
            customer.setCustPassword(Md5.getMD5Digest(customer.getCustPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ResultCode.ERROR_90004);
        }
        // 去掉名称空格
        if (StringUtils.isNotEmpty(customer.getCustName())) {
            customer.setCustName(customer.getCustName().trim());
        }
        // 保存客户信息
        customerDAO.save(customer);

        return customerDAO.getTransformerBean(customer.getCustId(), Customer.class, CustomerPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public CustomerPojo addCustomer(Customer customer, PregArchives pregnancyArchives) {
        if (customer == null || pregnancyArchives == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        customer.setCustCode(createCustCode());// 设置客户编码=十位随机数字
        // customer.setCustId(this.getInsId() + customer.getCustCode());// 设置主键=机构ID+用户编码
        // 设置客户密码
        if (StringUtils.isEmpty(customer.getCustPassword())) {
            customer.setCustPassword(this.getParamValue("customer_default_password"));
        }
        try {
            customer.setCustPassword(Md5.getMD5Digest(customer.getCustPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ResultCode.ERROR_90004);
        }
        // 去掉名称空格
        if (StringUtils.isNotEmpty(customer.getCustName())) {
            customer.setCustName(customer.getCustName().trim());
        }
        // 保存客户信息
        String custId = (String) customerDAO.save(customer);
        pregnancyArchives.setCustId(custId);
        pregnancyArchives.setId(UUID.randomUUID().toString());
        pregArchivesDAO.save(pregnancyArchives);

        return customerDAO.getTransformerBean(customer.getCustId(), Customer.class, CustomerPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateCustomer(Customer customer) {
        if (customer == null || StringUtils.isEmpty(customer.getCustId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("custId", HQLSymbol.EQ.toString(), customer.getCustId()));
        // 去掉名称空格
        if (StringUtils.isNotEmpty(customer.getCustName())) {
            customer.setCustName(customer.getCustName().trim());
        }
        int count = customerDAO.updateHQL(customer, conditionParams);
        if (count != 1) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateCustomer(Customer customer, PregArchives pregnancyArchives) {
        if (customer == null || StringUtils.isEmpty(customer.getCustId()) || pregnancyArchives == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("custId", HQLSymbol.EQ.toString(), customer.getCustId()));
        // 去掉姓名两端的空格
        if (StringUtils.isNotEmpty(customer.getCustName())) {
            customer.setCustName(customer.getCustName().trim());
        }
        int count = customerDAO.updateHQL(customer, conditionParams);
        if (count != 1) {
            throw new HibernateException("更新客户基本信息失败");
        }

        conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), pregnancyArchives.getId()));
        if (pregnancyArchives.getId() == null) {
            // 保存客户信息
            String custId = customer.getCustId();
            pregnancyArchives.setCustId(custId);
            // TODO 许道川 待修改
            pregnancyArchives.setId(UUID.randomUUID().toString());
            pregArchivesDAO.save(pregnancyArchives);
        } else {
            count = pregArchivesDAO.updateHQL(pregnancyArchives, conditionParams);
        }
        if (count != 1) {
            throw new ServiceException("更新客户档案信息失败");
        }
    }

    /**
     * 创建客户编号
     * 
     * @author zcq
     * @return userMaiyaCode
     */
    private String createCustCode() {
        String userMaiyaCode = CreateRandomCode.getRandomNumber(10);
        if (customerDAO.checkCustCodeValid(userMaiyaCode) > 0) {
            userMaiyaCode = createCustCode();
        }
        return userMaiyaCode;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void batchSaveCustomer(List<Customer> custList, String insId) {
        if (CollectionUtils.isNotEmpty(custList)) {
            for (Customer custBo : custList) {
                try {
                    Customer customer = TransformerUtils.transformerProperties(Customer.class, custBo);
                    customer.setCustCode(createCustCode());// 设置客户编码=十位随机数字
                    customer.setCustId(insId + customer.getCustCode());// 设置主键=机构ID+用户编码
                    // 设置密码
                    if (StringUtils.isEmpty(customer.getCustPassword())) {
                        customer.setCustPassword(this.getParamValue("customer_default_password"));
                    } else {
                        customer.setCustPassword(Md5.getMD5Digest(customer.getCustPassword()));
                    }
                    // 保存客户信息
                    customerDAO.save(customer);
                } catch (NoSuchAlgorithmException e) {
                    throw new ServiceException(ResultCode.ERROR_90004);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addImportResult(ImportResult importResult) {
        importResult.setImportId(JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_7)
                + CreateRandomCode.getRandomNumber(10));
        customerDAO.save(importResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImportResultPojo> queryImportResults(ImportResultCondition condition) {
        if (condition == null) {
            condition = new ImportResultCondition();
        }
        List<ImportResultPojo> importResultVos = customerDAO.queryImportResult(condition);
        return importResultVos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerPojo> queryCustomerByProperty(String propertyName, String propertyval, String custId) {
        if (StringUtils.isEmpty(propertyName) || StringUtils.isEmpty(propertyval)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        return customerDAO.queryCustomerByProperty(propertyName, propertyval, custId);
    }

    @Override
    public Integer checkVaildSikeId(String custSikeId) {
        return this.validCode("custSikeId", custSikeId, Customer.class);
    }
}
