package com.mnt.preg.master.service.basic;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-application-service.xml")  
@TransactionConfiguration(transactionManager="targetTransactionManager", defaultRollback=false)
public class ImportFmDataServiceTest {
	
	@Autowired
	private ImportFmDataService importFmDataService;

	@Test
	public void testImportFmData() {
		importFmDataService.importFmData();
	}

}
