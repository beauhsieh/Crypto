package com.crypto.crypto.controller.v1.user;

import com.crypto.crypto.controller.base.BaseController;
import com.crypto.crypto.service.user.UserService;
import com.crypto.crypto.service.user.model.ExtraBenefitPackageResVO;
import com.crypto.crypto.service.user.model.ExtraBenefitPackageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UsersController.class);

    private Map<String, String> userSortable = new HashMap<String, String>();
    private Map<String, String> userSearchable = new HashMap<String, String>();

    @Autowired
    private UserService userService;

    @PostMapping(path = "/get-user-extra-benefit-package")
    public ExtraBenefitPackageResVO getUserExtraBenefitPackage(@Valid @RequestBody ExtraBenefitPackageVO vo) {
        return userService.getUserExtraBenefitPackage(vo);
    }

}
