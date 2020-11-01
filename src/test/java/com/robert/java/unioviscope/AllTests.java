package com.robert.java.unioviscope;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.robert.java.unioviscope.business.student.StudentServiceTest;
import com.robert.java.unioviscope.business.teacher.TeacherServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ UniOviScopeApplicationTest.class, StudentServiceTest.class, TeacherServiceTest.class })
public class AllTests {

}