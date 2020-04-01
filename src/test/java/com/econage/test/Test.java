package com.econage.test;

import com.econage.runner.Application;
import com.econage.runner.db.entity.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
/*
@TestExecutionListeners({
        TransactionalTestExecutionListener.class
})
@Transactional
*/
@SpringBootTest(classes = Application.class)
public class Test {
    @Autowired
    TestMapper testMapper;



}
