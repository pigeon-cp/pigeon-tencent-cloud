package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.taccisum.pigeon.core.data.ThirdAccountDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/1/25
 */
class TencentCloudAccountTest {
    private TencentCloudAccount account;

    @BeforeEach
    void setUp() {
        account = spy(new TencentCloudAccount(1L));
    }

    @Nested
    @DisplayName("#sendSMS(...)")
    class SendSMSTest {
        @BeforeEach
        void setUp() {
            ThirdAccountDO data = new ThirdAccountDO();
            data.setAppId("you_app_id");
            data.setAppSecret("you_app_secret");
            doReturn(data).when(account).data();
        }

        @Test
        void index() {
            account.sendSMS(200523, "13790326938", "豌豆思维VIPThink", new String[]{"foo", "bar"});
        }
    }

    @Nested
    @DisplayName("#sendMail(...)")
    class SendMailTest {
        @BeforeEach
        void setUp() {
            ThirdAccountDO data = new ThirdAccountDO();
            data.setAppId("you_app_id");
            data.setAppSecret("you_app_secret");
            doReturn(data).when(account).data();
        }

        @Test
        void index() {
            account.sendMail("robot_01@tencent.66cn.top",
                    "liaojinfeng6938@dingtalk.com",
                    "demo mail",
                    "Hi {Gender}, i'm taccisum.\nGood morning, and in case I don't see you, good afternoon, good evening, and good night!",
                    "pigeon");
        }

        @Test
        void sendTemplateMail() throws JsonProcessingException {
            Map<String, Object> params = new HashMap<>();
            params.put("receiver", "taccisum");
            params.put("name", "pigeon");
            account.sendMail("robot_01@tencent.66cn.top",
                    "514162920@qq.com",
                    "demo mail",
                    23216L,
                    new ObjectMapper().writeValueAsString(params),
                    "pigeon");
        }
    }
}