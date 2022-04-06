package com.crypto.crypto.service.user;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class UserServiceImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("getUserExtraBenefitPackage-ok")
    void getUserExtraBenefitPackageOK() throws Exception {
        JSONObject postBody = new JSONObject();
        postBody.put("receiveBaseYearlyPackage", 10000);
        postBody.put("onBoardDay", "2021/04/04");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/get-user-extra-benefit-package").content(postBody.toJSONString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("getUserExtraBenefitPackage-badDate1")
    void getUserExtraBenefitPackageBadDate1() throws Exception {
        JSONObject postBody = new JSONObject();
        postBody.put("receiveBaseYearlyPackage", 10000);
        postBody.put("onBoardDay", "2021/06/04");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/get-user-extra-benefit-package").content(postBody.toJSONString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"badOnBoardDay\",\"description\":\"On board day format is invalid [On board day format is invalid]\"}"));
    }

    @Test
    @DisplayName("getUserExtraBenefitPackage-error1")
    void getUserExtraBenefitPackageError1() throws Exception {
        JSONObject postBody = new JSONObject();
        postBody.put("onBoardDay", "2021/06/04");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/get-user-extra-benefit-package").content(postBody.toJSONString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("getUserExtraBenefitPackage-error2")
    void getUserExtraBenefitPackageError2() throws Exception {
        JSONObject postBody = new JSONObject();
        postBody.put("receiveBaseYearlyPackage", 10000);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/get-user-extra-benefit-package").content(postBody.toJSONString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getUserExtraBenefitPackage-error3")
    void getUserExtraBenefitPackageError3() throws Exception {
        JSONObject postBody = new JSONObject();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/get-user-extra-benefit-package").content(postBody.toJSONString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}