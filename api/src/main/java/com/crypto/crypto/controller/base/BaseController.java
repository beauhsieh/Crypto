package com.crypto.crypto.controller.base;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


public abstract class BaseController {

    @Autowired(required = true)
    protected HttpServletRequest request;

    protected static final int FAKE_USER_ID = 1;
    protected static final int FAKE_ROLE_ID = 1;

}
