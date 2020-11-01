package com.robert.java.unioviscope;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Robert Ene
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UniOviScopeApplicationTest {

	@Autowired
	private UniOviScopeApplication uniOviScopeApplication;

	@Test
	public void testUniOviScopeApplication() throws Exception {
		assertNotNull(uniOviScopeApplication);
	}
}
