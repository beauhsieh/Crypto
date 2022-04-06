package com.crypto.crypto.service.user.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraBenefitPackageResVO {
    private BigDecimal avgVWAP;
    private JSONObject yearExtraBenefitPackageCROs;
    private BigDecimal sumCros;
    private BigDecimal baseYearlyPackageUSD;
}
