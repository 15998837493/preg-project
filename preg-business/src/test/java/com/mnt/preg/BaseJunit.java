
package com.mnt.preg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.customer.dao.CustomerDAO;
import com.mnt.preg.customer.dao.PregCourseBookingDAO;
import com.mnt.preg.customer.dao.QuestionAnswerDAO;
import com.mnt.preg.examitem.dao.ExamResultRecordDAO;
import com.mnt.preg.examitem.service.NutritiousSurveyService;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.master.dao.basic.ProductCatalogDAO;
import com.mnt.preg.master.dao.basic.ProductDAO;
import com.mnt.preg.master.dao.items.ItemDAO;
import com.mnt.preg.master.dao.question.QuestionDAO;
import com.mnt.preg.master.service.basic.ProductCatalogService;
import com.mnt.preg.master.service.basic.ProductService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.dao.PregArchivesDAO;
import com.mnt.preg.platform.dao.PregDiagnosisDAO;
import com.mnt.preg.platform.dao.PregDiagnosisExtenderDAO;
import com.mnt.preg.platform.dao.PregDiagnosisItemDAO;
import com.mnt.preg.platform.dao.PregPlanDAO;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.statistic.dao.StatisticDAO;
import com.mnt.preg.system.dao.DoctorDao;
import com.mnt.preg.system.dao.PrimaryKeyDao;
import com.mnt.preg.system.service.DoctorService;

/**
 * junit单元测试基础类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-10 zcq 初版
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 由此引入Spring-Test框架支持
@ContextConfiguration(locations = {"classpath*:test-application-service.xml"})
// 导入配置文件
@Transactional
// 这个非常关键，如果不加入这个注解配置，事务控制就会完全失效
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
// 这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时指定自动回滚（defaultRollback = true）这样做操作的数据才不会污染数据库！
public abstract class BaseJunit {

    @Resource
    public PrimaryKeyDao primaryKeyDao;

    @Resource
    public CustomerDAO customerDAO;

    @Resource
    public PregDiagnosisDAO diagnosisDAO;

    @Resource
    public QuestionDAO questionDAO;

    @Resource
    public QuestionAnswerDAO questionAnswerDAO;

    @Resource
    public PregDiagnosisService diagnosisService;

    @Resource
    public QuestionService questionService;

    @Resource
    public QuestionAnswerService questionAnswerService;

    @Resource
    public ProductDAO productDAO;

    @Resource
    public ProductCatalogDAO productCatalogDAO;

    @Resource
    public ProductService productService;

    @Resource
    public ProductCatalogService productCatalogService;

    @Resource
    public ItemDAO itemDAO;

    @Resource
    public ItemService itemService;

    @Resource
    public DoctorDao doctorDAO;

    @Resource
    public DoctorService doctorService;

    @Resource
    public PregDiagnosisItemDAO pregDiagnosisItemDAO;

    @Resource
    public ExamResultRecordDAO examResultRecordDAO;

    @Resource
    public PregArchivesDAO archivesDAO;

    @Resource
    public PregArchivesService archivesService;

    @Resource
    public PregPlanService pregPlanService;

    @Resource
    public PregDiagnosisExtenderDAO pregDiagnosisExtenderDao;

    @Resource
    public PregPlanDAO pregPlanDAO;

    @Resource
    public PregCourseBookingDAO pregCourseBookingDAO;

    @Resource
    public NutritiousSurveyService nutritiousSurveyService;

    @Resource
    public StatisticDAO statisticDAO;

    @Before
    public void setUp() throws Exception {
    }

    /**
     * 读取配置文件
     * 
     * @author zcq
     */
    public Properties readProperties() {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("config-web.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }

}
