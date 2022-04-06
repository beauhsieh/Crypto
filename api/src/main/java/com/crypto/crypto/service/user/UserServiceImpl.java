package com.crypto.crypto.service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crypto.crypto.service.AbstractService;
import com.crypto.crypto.service.user.model.ExtraBenefitPackageResVO;
import com.crypto.crypto.service.user.model.ExtraBenefitPackageVO;
import com.crypto.crypto.utils.CryptoApiUtils;
import com.crypto.crypto.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class UserServiceImpl extends AbstractService implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    CryptoApiUtils cryptoApiUtils;

    @Override
    public ExtraBenefitPackageResVO getUserExtraBenefitPackage(ExtraBenefitPackageVO vo) {
        //all currency https://price-api.crypto.com/price/v1/currency/all
        //CRO to USD By D, https://price-api.crypto.com/price/v2/d/crypto-com-coin
        //2022/4/3, 2021/4/4-2022/4/3
        ExtraBenefitPackageResVO result = new ExtraBenefitPackageResVO();
        Long onboardDataTimestamp = 0L;
        if (vo.getOnBoardDay() != null) {
            onboardDataTimestamp = DateUtils.parseOnBoardDay(vo.getOnBoardDay()) * 1000;
        } else {
            LocalDateTime onboardDataTime = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).minusYears(1).with(LocalTime.MIDNIGHT);
            onboardDataTimestamp = onboardDataTime.toEpochSecond(ZoneOffset.UTC) * 1000;
        }

        JSONObject allCurrencyObject = cryptoApiUtils.getAllCurrency();
        BigDecimal usdToCROExchangeRate = getUSDToCROExchangeRate(allCurrencyObject);
        //10%
        BigDecimal receiveBaseYearlyPackageUSDToCROs = vo.getReceiveBaseYearlyPackage().multiply(usdToCROExchangeRate);
        BigDecimal allBaseYearlyPackageCROs = receiveBaseYearlyPackageUSDToCROs.divide(new BigDecimal(0.1), 5);

        JSONArray candlestickData = cryptoApiUtils.getCandlestick("CRO_USDT", "1D");
        BigDecimal avgVWAP = getAvgVWAP(candlestickData, onboardDataTimestamp);

        result.setAvgVWAP(avgVWAP);
        JSONObject yearExtraBenefitPackage = new JSONObject();
        yearExtraBenefitPackage.put("1y", receiveBaseYearlyPackageUSDToCROs);
        yearExtraBenefitPackage.put("2y", allBaseYearlyPackageCROs.multiply(new BigDecimal(0.2)));
        yearExtraBenefitPackage.put("3y", allBaseYearlyPackageCROs.multiply(new BigDecimal(0.3)));
        yearExtraBenefitPackage.put("4y", allBaseYearlyPackageCROs.multiply(new BigDecimal(0.4)));

        result.setYearExtraBenefitPackageCROs(yearExtraBenefitPackage);
        result.setSumCros(allBaseYearlyPackageCROs);
        result.setBaseYearlyPackageUSD(allBaseYearlyPackageCROs.divide(usdToCROExchangeRate, 5));

        return result;
    }

    private BigDecimal getUSDToCROExchangeRate(JSONObject allCurrencyObject) {
        return allCurrencyObject.getJSONObject("crypto").getBigDecimal("CRO");
    }

    private BigDecimal getAvgVWAP(JSONArray candlestickData, Long onboardDataTimestamp) {
        BigDecimal avgVWAP = new BigDecimal(0);
        Long onboardDataAfter1YearTimestamp = onboardDataTimestamp + 31536000000L;
        double sum = 0;
        int count = 0;
        for (int i = 0; i < candlestickData.size(); i++) {
            if (candlestickData.getJSONObject(i).getLong("t") <= onboardDataTimestamp) {
                continue;
            }
            if (candlestickData.getJSONObject(i).getLong("t") > onboardDataAfter1YearTimestamp) {
                break;
            }
            sum += candlestickData.getJSONObject(i).getBigDecimal("c").doubleValue();
            count++;
        }
        avgVWAP = new BigDecimal(sum / count);

        return avgVWAP;
    }
}