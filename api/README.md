## Environment
Java 11  
Gradle 7.4.1

## Requirement
1. Every employee who joins the company, is entitled to receive an extra benefit package
   with the amount equivalent to the candidate’s onboarding base yearly package.
   The amount is calculated based on the CRO (crypto.com’s cryptocurrency) price, you can check the price here https://crypto.com/price/crypto-com-coin
2. The rewards are to be cashed out in 4 years on each anniversary, 1st year 10%, 2nd year 20%, 3rd year 30%, and 4th year 40%, the reward will be granted in USD or any local fiat currency.
3. The price of the CRO to be used to determine how many CROs are to be granted is determined by the daily based VWAP(Volume Weighted Average Price) in the first 365 days since the employee joined the company.
4. VWAP can be calculated with the public candlestick API
   https://exchange-docs.crypto.com/spot/index.html#public-get-candlestick

Assume that Alice Wong is receiving a base yearly package of X US dollars , and today is her
1st year anniversary, can you design a simple program to help Alice to calculate her actual
rewards in USD.

## Reference api
1. all currency https://price-api.crypto.com/price/v1/currency/all
2. candlestick CRO_USDT By D, https://api.crypto.com/v2/public/get-candlestick?instrument_name=CRO_USDT&timeframe=1D

## Api Doc

### Url
/api/v1/users/get-user-extra-benefit-package

### Header
| Name         | Type   | Size | Must | Description            |
|--------------|--------|------|------|------------------------|
| Content-Type | string | -    | Y    | application/json       |

### Parameter
| Name                     | Type   | Size | Must | Description                                                  |
|--------------------------|--------|------|------|--------------------------------------------------------------|
| receiveBaseYearlyPackage | int    | -    | Y    | Receiving a base yearly package of X US dollars              |
| onBoardDay               | string | -    | Y    | On board date <br/>yyyy-MM-dd<br/>yyyy/MM/dd<br/>yyyyMMdd    |

### Response
| Name                        | Type   | Size | Must | Description                                                                                 |
|-----------------------------|--------|------|------|---------------------------------------------------------------------------------------------|
| avgVWAP                     | int    | -    | Y    | Average of your VWAP                                                                        |
| yearExtraBenefitPackageCROs | object | -    | Y    | Year anniversary will receive X CROs; refer to following yearExtraBenefitPackageCROs object |
| sumCros                     | int    | -    | Y    | Sum of CROs                                                                                 |
| baseYearlyPackageUSD        | int    | -    | Y    | Your base yearly package US dollars                                                         |

### yearExtraBenefitPackageCROs Object
| Name | Type | Size | Must | Description                              |
|------|------|------|------|------------------------------------------|
| 4y   | int  | -    | Y    | 4st year anniversary will receive X CROs |
| 3y   | int  | -    | Y    | 3st year anniversary will receive X CROs |
| 2y   | int  | -    | Y    | 2st year anniversary will receive X CROs |
| 1y   | int  | -    | Y    | 1st year anniversary will receive X CROs |

### Error Code
| HTTP Status | Error         | Description                                                     |
|-------------|---------------|-----------------------------------------------------------------|
| 400         | badOnBoardDay | On board day format is invalid [On board day format is invalid] |
| 500         | internalError | Internal error                                                  |

### Example
```
POST http://localhost:8080/api/v1/users/get-user-extra-benefit-package
RequestBody
{
    "receiveBaseYearlyPackage":10000,
    "onBoardDay": "2021/06/04"
}

Success statusCode=200
ResponseBody
{
    "avgVWAP": 0.2934288493150685184218673384748399257659912109375,
    "yearExtraBenefitPackageCROs": {
        "4y": 85793.824000000004762513938771917310077697038650512695312500000000,
        "3y": 64345.3679999999976187430306140413449611514806747436523437500000000,
        "2y": 42896.9120000000023812569693859586550388485193252563476562500000000,
        "1y": 21448.4560000
    },
    "sumCros": 214484.5600000,
    "baseYearlyPackageUSD": 100000.0000000
}

Error statusCode=400
ErrorMessage
{
    "error": "badOnBoardDay",
    "description": "On board day format is invalid [On board day format is invalid]"
}
```

