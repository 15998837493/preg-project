
package com.mnt.preg.customer.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.customer.condition.CustomerCondition;
import com.mnt.preg.customer.entity.Customer;
import com.mnt.preg.customer.form.CustomerForm;
import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.mapping.CustomerPageName;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.entity.PregArchives;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.util.FoodsFormulaUtil;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 会员管理Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Controller
public class CustomerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Resource
    private PregArchivesService pregArchivesService;

    /**
     * 
     * 获取用户建档记录
     * 
     * @author scd
     * @param custId
     * @return
     */
    @RequestMapping(value = CustomerMapping.QUERY_CUST_PREG_RECPRD)
    public @ResponseBody WebResult<PregArchivesPojo> queryCustomerPregRecprd(PregArchives pregArchives) {
        WebResult<PregArchivesPojo> webResult = new WebResult<PregArchivesPojo>();
        webResult.setData(pregArchivesService.queryCustomerPregRecprd(pregArchives.getCustId()));
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 
     * 根据建档主键获取建档信息以及用户信息
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.QUERY_CUST_PREG_INFO)
    public String getDiagnosisArchiveByDiagnosisId(String id, Model model) {
        PregArchivesPojo pregPojo = pregArchivesService.getPregArchivesPojoById(id);
        CustomerPojo customerPojo = customerService.getCustomer(pregPojo.getCustId());
        model.addAttribute("pregPojo", NetJsonUtils.objectToJson(pregPojo));
        model.addAttribute("customerPojo", NetJsonUtils.objectToJson(customerPojo));
        return CustomerPageName.PREG_ARCHIVES_INFO;
    }

    /**
     * 检索客户信息
     * 
     * @author zcq
     * @param condition
     * @return
     * 
     */
    @RequestMapping(value = CustomerMapping.QUERY_CUST_DIAGNOSIS)
    public @ResponseBody CustomerCondition queryCustomer(CustomerCondition condition) {
        return customerService.queryCustomerForDiagnosis(condition);
    }

    /**
     * 检索分娩登记分页信息
     * 
     * @author wjc
     * @param condition
     * @return
     * 
     */
    @RequestMapping(value = CustomerMapping.QUERY_CUST_BIRTHENDING)
    public @ResponseBody CustomerCondition queryCustomerForBirthEnding(CustomerCondition condition) {
        return customerService.queryCustomerForBirthEnding(condition);
    }

    /**
     * 检索客户信息诊疗平台登记
     * 
     * @author scd
     * @param condition
     * @return
     * 
     */
    @RequestMapping(value = CustomerMapping.QUERY_CUST_DIAGNOSIS_REGISTER)
    public @ResponseBody CustomerCondition queryCustomerForDiagnosis(CustomerCondition condition) {
        CustomerCondition queryCustomerForDiagnosisPage = customerService.queryCustomerForDiagnosisPage(condition);
        return queryCustomerForDiagnosisPage;
    }

    /**
     * 会员增加
     * 
     * @param request
     * @param customerForm
     *            会员信息
     * @return
     */
    @RequestMapping(value = CustomerMapping.ADD_CUST)
    public @ResponseBody WebResult<CustomerPojo> addCustomer(CustomerForm customerForm) {
        WebResult<CustomerPojo> webResult = new WebResult<CustomerPojo>();
        Customer customer = TransformerUtils.transformerProperties(Customer.class, customerForm);
        PregArchives pregArchives = TransformerUtils.transformerProperties(PregArchives.class,
                customerForm);

        if (customer.getCustWeight() != null && customer.getCustHeight() != null) {
            double bmi = FoodsFormulaUtil.getBmi(customer.getCustWeight().doubleValue(), customer.getCustHeight()
                    .doubleValue());
            if (bmi > 100) {
                webResult.setError("身高或体重参数不合法");
                return webResult;
            }
            customer.setCustBmi(new BigDecimal(bmi));
            pregArchives.setBmi(new BigDecimal(bmi));
        }
        pregArchives.setHeight(customerForm.getCustHeight());
        pregArchives.setWeight(customerForm.getCustWeight());
        pregArchives.setWaistline(customerForm.getCustWaistline());
        CustomerPojo customerPojo = customerService.addCustomer(customer, pregArchives);
        webResult.setSuc(customerPojo);
        return webResult;
    }
    
    /**
             *  会员增加（单表操作）
     * 
     * @param request
     * @param customerForm
             *            会员信息
     * @return
     */
    @RequestMapping(value = CustomerMapping.ADD_CUST_ONLY)
    public @ResponseBody WebResult<CustomerPojo> addCustomerOnly(CustomerForm customerForm) {
        WebResult<CustomerPojo> webResult = new WebResult<CustomerPojo>();
        Customer customer = TransformerUtils.transformerProperties(Customer.class, customerForm);
        
        if (customer.getCustWeight() != null && customer.getCustHeight() != null) {
            double bmi = FoodsFormulaUtil.getBmi(customer.getCustWeight().doubleValue(), customer.getCustHeight().doubleValue());
            if (bmi > 100) {
                webResult.setError("身高或体重参数不合法");
                return webResult;
            }
            customer.setCustBmi(new BigDecimal(bmi));
        }
        CustomerPojo customerPojo = customerService.addCustomer(customer);
        webResult.setSuc(customerPojo);
        return webResult;
    }

    /**
     * 初始化客户修改页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    // @RequestMapping(value = CustomerMapping.INIT_UPDATE_CUST)
    // public String initUpdateCust(String custId, Model model) {
    // CustomerPojo CustomerPojo = customerService.getCustomer(custId);
    // CustomerForm form = TransformerUtils.transformerProperties(CustomerForm.class, CustomerPojo);
    // PregArchivesVo pregArchivesPojo = pregArchivesService.getLastPregnancyArchive(custId);
    // if (pregArchivesPojo != null) {
    // form.setId(pregArchivesPojo.getId());
    // form.setLmp(pregArchivesPojo.getLmp());
    // form.setCreateDate(pregArchivesPojo.getCreateDate());
    // form.setCreateLocale(pregArchivesPojo.getCreateLocale());
    // form.setDoctorName(pregArchivesPojo.getDoctorName());
    // form.setPregnancyDueDate(pregArchivesPojo.getPregnancyDueDate());
    // }
    // model.addAttribute("custVo", NetJsonUtils.objectToJson(form));
    //
    // return CustomerPageName.CUSTOMER_UPDATE;
    // }
    /**
     * 
     * 编辑页面参数获取
     * 
     * @author dell
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.INIT_UPDATE_CUST)
    public Boolean initUpdateCust(String custId, Model model) {
        CustomerPojo CustomerPojo = customerService.getCustomer(custId);
        CustomerForm form = TransformerUtils.transformerProperties(CustomerForm.class, CustomerPojo);
        PregArchivesPojo pregArchivesPojo = pregArchivesService.getLastPregnancyArchive(custId);
        boolean flag = false;
        if (pregArchivesPojo != null) {
            flag = true;
            form.setId(pregArchivesPojo.getId());
            form.setLmp(pregArchivesPojo.getLmp());
            form.setCreateDate(pregArchivesPojo.getCreateDate());
            form.setCreateLocale(pregArchivesPojo.getCreateLocale());
            form.setPregnancyDueDate(pregArchivesPojo.getPregnancyDueDate());
        }
        model.addAttribute("custVo", NetJsonUtils.objectToJson(form));

        return flag;
    }

    /**
     * 修改客户信息
     * 
     * @author zcq
     * @param customerForm
     * @return
     */
    @RequestMapping(value = CustomerMapping.UPDATE_CUST)
    public @ResponseBody WebResult<Boolean> updateCustomer(CustomerForm customerForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Customer customer = TransformerUtils.transformerProperties(Customer.class, customerForm);

        PregArchivesPojo pregArchivesPojo = pregArchivesService.getLastPregnancyArchive(customer.getCustId());
        if (pregArchivesPojo != null) {
            customerForm.setId(pregArchivesPojo.getId());
        }
        PregArchives pregArchives = TransformerUtils.transformerProperties(PregArchives.class,
                customerForm);
        pregArchives.setCustId(customer.getCustId());
        pregArchives.setHeight(customerForm.getCustHeight());
        pregArchives.setWeight(customerForm.getCustWeight());
        pregArchives.setWaistline(customerForm.getCustWaistline());
        if (customer.getCustWeight() != null && customer.getCustHeight() != null) {
            double bmi = FoodsFormulaUtil.getBmi(customer.getCustWeight().doubleValue(), customer
                    .getCustHeight()
                    .doubleValue());
            customer.setCustBmi(new BigDecimal(bmi));
            pregArchives.setBmi(new BigDecimal(bmi));
        }
        customerService.updateCustomer(customer, pregArchives);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 删除客户
     * 
     * @author zcq
     * @param custId
     * @return
     */
    @RequestMapping(value = CustomerMapping.REMOVE_CUST)
    public @ResponseBody WebResult<Boolean> removeCustomer(String custId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Customer customer = new Customer();
        customer.setCustId(custId);
        customer.setFlag(0);
        customerService.updateCustomer(customer);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 验证ID
     * 
     * @author zcq
     * @param custPatientId
     * @return
     */
    @RequestMapping(value = CustomerMapping.VALIDATE_PATIENTID, method = RequestMethod.POST)
    @ResponseBody
    public boolean validatePatientId(String custPatientId) {
        int count = this.validCode("cust_patient_id", custPatientId, Customer.class);
        if (count == 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证病案号
     * 
     * @author zcq
     * @param custSikeId
     * @return
     */
    @RequestMapping(value = CustomerMapping.VALIDATE_SIKEId)
    @ResponseBody
    public boolean validateCustSikeId(String custSikeId) {
        int count = this.validCode("cust_sike_id", custSikeId, Customer.class);
        if (count == 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证身份证号
     * 
     * @author zcq
     * @param custIcard
     * @return
     */
    @RequestMapping(value = CustomerMapping.VALIDATE_ICARD, method = RequestMethod.POST)
    @ResponseBody
    public boolean validateCustIcard(String custIcard) {
        int count = this.validCode("cust_icard", custIcard, Customer.class);
        if (count == 0) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 验证身份证号（患者管理）
     * 
     * @author dhs
     * @param custIcard
     * @param custId
     * @return
     */
    @RequestMapping(value = CustomerMapping.VALIDATE_PATIENT_ICARD, method = RequestMethod.POST)
    @ResponseBody
    public boolean validatePatientCustIcard(String custIcard, String oldCustIcard) {
        boolean result = false;
        String propertyName = "cust_icard";
        custIcard = custIcard.trim();
        List<CustomerPojo> CustomerPojos = customerService.queryCustomerByProperty(propertyName, custIcard, "");
        if (StringUtils.isEmpty(oldCustIcard)) {// 添加
            if (CollectionUtils.isEmpty(CustomerPojos)) {
                return true;
            }
        } else {// 编辑
            if (custIcard.equals(oldCustIcard.trim())) {
                return true;
            } else {
                if (CollectionUtils.isEmpty(CustomerPojos)) {
                    return true;
                }
            }
        }
        return result;
    }

    /**
     * 
     * 验证病案号
     * 
     * @author mnt_zhangjing
     * @param custPatientId
     * @param custId
     * @return
     */
    @RequestMapping(value = CustomerMapping.VALIDATE_SIKEID)
    @ResponseBody
    public boolean validateSikeId(String custSikeId, String custSikeIdOld) {
        boolean result = false;
        if (StringUtils.isEmpty(custSikeId)) {
            return true;
        }
        Integer index = customerService.checkVaildSikeId(custSikeId);
        if (StringUtils.isEmpty(custSikeIdOld)) {// 添加
            if (index < 1) {
                return true;
            }
        } else {// 修改
            if (custSikeId.equals(custSikeIdOld)) {
                return true;// 如果修改后的编码和之前的一样，返回true；
            } else {
                if (index < 1) {
                    return true;// 修改后的编码和之前的不一样，并且在数据库中没有改编码，返回true
                }
            }
        }
        return result;
    }

    /**
     * 同步体检数据
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = CustomerMapping.QUERY_FTP_DATA)
    public @ResponseBody WebResult<List<String>> queryFtpData(String ftpDate) {
        WebResult<List<String>> webResult = new WebResult<List<String>>();
        if (StringUtils.isEmpty(ftpDate)) {
            webResult.setError("请先选择同步日期！");
            return webResult;
        }
        ftpService.queryDataForFTP(ftpDate.replace("-", ""));
        List<String> fileNameList = new ArrayList<String>();
        String unZipPath = publicConfig.getResourceAbsolutePath() + "resource/core_db";
        if (new File(unZipPath).exists()) {
            File[] tempList = new File(unZipPath).listFiles();
            if (tempList != null && tempList.length > 0) {
                for (File file : tempList) {
                    String size = "";
                    long fileS = file.length();
                    DecimalFormat df = new DecimalFormat("#.00");
                    if (fileS < 1024) {
                        size = df.format((double) fileS) + "BT";
                    } else if (fileS < 1048576) {
                        size = df.format((double) fileS / 1024) + "KB";
                    } else if (fileS < 1073741824) {
                        size = df.format((double) fileS / 1048576) + "MB";
                    } else {
                        size = df.format((double) fileS / 1073741824) + "GB";
                    }
                    fileNameList.add("resource/core_db/" + file.getName() + "," + size);
                }
            }
        }
        webResult.setSuc(fileNameList);
        return webResult;
    }

    // ------------------------------------------------个人信息----------------------------------------------------------

    /**
     * 跳转到个人信息向导页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.CUST_GUIDE_PAGE)
    public String toCustGuidePage(String custId, Model model) {
        model.addAttribute("custId", custId);
        return CustomerPageName.CUST_GUIDEPAGE;
    }

    /**
     * 获取个人资料页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.CUST_GUIDE_GEREN)
    public String getCustGeren(String custId, Model model) {
        try {
            CustomerPojo customerInfo = customerService.getCustomer(custId);
            BigDecimal weight = customerInfo.getCustWeight();
            BigDecimal height = customerInfo.getCustHeight();
            if (weight != null && height != null) {
                double bmi = FoodsFormulaUtil.getBmi(weight.doubleValue(), height.doubleValue());
                model.addAttribute("bmi", bmi);
            }
            model.addAttribute("customerInfo", customerInfo);
        } catch (Exception e) {
            LOGGER.error("[获取客户信息]接口异常[" + e.getMessage() + "];", e);
        }
        return CustomerPageName.CUST_GEREN;
    }

    /**
     * 获取健康报告页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.CUST_GUIDE_BAOGAO)
    public String getCustBaogao(String custId, Model model) {
        model.addAttribute("healthReportCustId", custId);
        return CustomerPageName.CUST_BAOGAO;
    }

    /**
     * 获取诊疗记录页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.CUST_GUIDE_JILU)
    public String getCustJilu(String custId, Model model) {
        try {
            CustomerPojo customerInfo = customerService.getCustomer(custId);
            double bmi = FoodsFormulaUtil.getBmi(customerInfo.getCustWeight().doubleValue(), customerInfo
                    .getCustHeight().doubleValue());
            model.addAttribute("customerInfo", customerInfo);
            model.addAttribute("bmi", bmi);
        } catch (Exception e) {
            LOGGER.error("[获取客户信息]接口异常[" + e.getMessage() + "];", e);
        }
        return CustomerPageName.CUST_JILU;
    }

    /**
     * 获取干预方案页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.CUST_GUIDE_FANGAN)
    public String getCustFangan(String custId, Model model) {
        model.addAttribute("custId", custId);
        return CustomerPageName.CUST_FANGAN;
    }

}
