package com.crypto.crypto.service.user;

import com.crypto.crypto.service.user.model.ExtraBenefitPackageResVO;
import com.crypto.crypto.service.user.model.ExtraBenefitPackageVO;

public interface UserService {
    ExtraBenefitPackageResVO getUserExtraBenefitPackage(ExtraBenefitPackageVO vo);
}