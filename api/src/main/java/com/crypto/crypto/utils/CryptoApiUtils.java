package com.crypto.crypto.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
public class CryptoApiUtils {
    @Value("${crypto.priceApi.prod.host}")
    private String cryptoPriceUrl;
    @Value("${crypto.api.prod.host}")
    private String cryptoUrl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public CloseableHttpClient httpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
            RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
            .build());

        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(sslContext)
            .setConnectionManager(cm)
            .build();

        return httpClient;
    }

    public JSONObject getAllCurrency() {
        JSONObject allCurrencyData = null;
        String resp = "";
        String getPriceUrl = cryptoPriceUrl + "currency/all";

        logger.info("url:{}", getPriceUrl);

        try {
            logger.info("Send process start");

            Unirest.setTimeouts(0, 0);
            com.mashape.unirest.http.HttpResponse<JsonNode> response
                    = Unirest.get(getPriceUrl)
                    .header("Cookie", "__cf_bm=KYWap79.uACrT1CyL1Y7EUYMzCG1ONV6ezjIfU_SiIw-1649135622-0-AYo6BoTGJAeZyyhaWsX5cKl6aRVzCJONBTwnyrClH66eq/Fkaoape3G+9dFy0BY+Ff8h5qo7/0mdJJbmmD2xwPM=")
                    .asJson();
            
            logger.info(String.valueOf(response.getStatus()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatus());
            if (statusCode.equals("200")) {
                try {
                    JSONObject resJson = JSONObject.parseObject(response.getBody().toString());
                    logger.info("{}", resJson.toJSONString());
                    allCurrencyData = resJson.getJSONObject("data");

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                logger.error("send status code : " + statusCode.toString());
                logger.error("send ErrorMsg : " + resp);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return allCurrencyData;
    }

    public JSONArray getCandlestick(String instrumentName, String timeframe) {
        JSONArray candlestickData = null;
        String resp = "";
        String getCandlestickUrl = cryptoUrl + "public/get-candlestick?instrument_name=%s&timeframe=%s";
        getCandlestickUrl = String.format(getCandlestickUrl, instrumentName, timeframe);

        logger.info("url:{}", getCandlestickUrl);

        try {
            HttpClient httpClient = httpClient();
            HttpGet get = new HttpGet(getCandlestickUrl);

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(get);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            if (statusCode.equals("200")) {
                try {
                    JSONObject resJson = JSONObject.parseObject(resp);
                    logger.info("{}", resJson.toJSONString());
                    JSONObject resultObject = resJson.getJSONObject("result");
                    if (resultObject != null) {
                        candlestickData = resultObject.getJSONArray("data");
                    }

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                logger.error("send status code : " + statusCode.toString());
                logger.error("send ErrorMsg : " + resp);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return candlestickData;
    }


}
