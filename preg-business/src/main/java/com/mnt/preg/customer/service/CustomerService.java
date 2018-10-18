
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.condition.CustomerCondition;
import com.mnt.preg.customer.condition.ImportResultCondition;
import com.mnt.preg.customer.entity.Customer;
import com.mnt.preg.customer.entity.ImportResult;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.ImportResultPojo;
import com.mnt.preg.platform.entity.PregArchives;

/**
 * 
 * 会员管理<br>
 * 
 * <dl>
 * <dt>功能描述</dt>
 * <dd>1、查询会员</dd>
 * <dd>2、增加会员</dd>
 * <dd>3、批量增加会员</dd>
 * <dd>4、更新会员信息</dd>
 * <dd>5、客户激活服务</dd>
 * <dd>5、密码修改、重置</dd>
 * </dl>
 * 
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-11-6 zy 初版
 */
@Validated
public interface CustomerService {

    /**
     * 检索客户信息
     * 
     * @author zcq
     * @return
     */
    List<CustomerPojo> queryCustomer(CustomerCondition condition);

    /**
     * 分页检索患者信息（分娩结局）
     * 
     * @author zcq
     * @param condition
     * @return
     */
    CustomerCondition queryCustomerForBirthEnding(CustomerCondition customer);

    /**
     * 分页检索患者信息（诊疗登记一览）
     * 
     * @author zcq
     * @param condition
     * @return
     */
    CustomerCondition queryCustomerForDiagnosis(CustomerCondition condition);

    /**
     * 分页检索患者信息（诊疗登记一览）
     * 
     * @author zcq
     * @param condition
     * @return
     */
    CustomerCondition queryCustomerForDiagnosisPage(CustomerCondition condition);

    /**
     * 查询客户信息--根据【主键】
     * 
     * @author zcq
     * @param custId
     * @return
     */
    CustomerPojo getCustomer(String custId);

    /**
     * 添加客户信息
     * 
     * @author zcq
     * @param customer
     * @return
     */
    CustomerPojo addCustomer(Customer customer);

    /**
     * 
     * 添加客户档案信息
     * 
     * @author mnt_zhangjing
     * @param customer
     *            客户信息
     * @param pregnancyArchives
     *            档案信息
     * @return CustomerPojo
     */
    CustomerPojo addCustomer(Customer customer, PregArchives pregnancyArchives);

    /**
     * 修改客户信息
     * 
     * @author zcq
     * @param customer
     */
    void updateCustomer(Customer customer);

    /**
     * 
     * 修改客户档案信息
     * 
     * @author mnt_zhangjing
     * @param customer
     *            客户信息
     * @param pregnancyArchives
     *            档案信息
     */
    void updateCustomer(Customer customer, PregArchives pregnancyArchives);

    /**
     * 批量保存客户信息
     * 
     * @author zcq
     * @param custList
     * @param insId
     */
    void batchSaveCustomer(List<Customer> custList, String insId);

    /**
     * 保存批量导入结果
     * 
     * @author zcq
     * @param importResultBo
     */
    void addImportResult(ImportResult importResult);

    /**
     * 条件查询批量导入结果信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ImportResultPojo> queryImportResults(ImportResultCondition condition);

    /**
     * 
     * 查询不为指定ID，属性名为指定值的患者信息
     * 
     * @author mnt_zhangjing
     * @param propertyName
     *            属性名
     * @param propertyval
     *            属性值
     * @param custId
     *            患者Id
     * @return
     */
    List<CustomerPojo> queryCustomerByProperty(String propertyName, String propertyval, String custId);

    /**
     * 病案号编码验证
     * 
     * @author xdc
     * @param custSikeId
     * @return
     */
    Integer checkVaildSikeId(String custSikeId);

}
