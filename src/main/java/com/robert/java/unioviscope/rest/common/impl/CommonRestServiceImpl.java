package com.robert.java.unioviscope.rest.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.robert.java.unioviscope.business.common.CommonService;
import com.robert.java.unioviscope.model.User;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.rest.common.CommonRestService;

/**
 * Clase que implementa la interfaz CommonRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.common.CommonRestService
 */
@RestController
public class CommonRestServiceImpl implements CommonRestService {

	@Autowired
	private CommonService commonService;

	@Override
	public User findUserDetails(String userName) throws BusinessException {
		return commonService.findUserDetails(userName);
	}
}
