package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.github.taccisum.domain.core.DomainException;
import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.data.ThirdAccountDO;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.tencentcloudapi.ses.v20201002.models.SendEmailRequest;
import com.tencentcloudapi.ses.v20201002.models.SendEmailResponse;
import com.tencentcloudapi.ses.v20201002.models.Simple;
import com.tencentcloudapi.ses.v20201002.models.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

/**
 * 腾讯云账号
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class TencentCloudAccount extends ThirdAccount {
    public TencentCloudAccount(Long id) {
        super(id);
    }

    /**
     * 通过腾讯云邮件服务指定账号发送一封邮件，详见 https://cloud.tencent.com/document/api/1288/51034
     *
     * @param account    发信账号，最终会显示在发信人上（如 robot -> robot@smtp.foo.com）
     * @param toAddress  目标地址
     * @param subject    主题
     * @param templateId 模板 id
     * @param params     参数
     * @param tag        邮件标签
     * @return 腾讯云消息 id
     */
    public String sendMail(String account, String toAddress, String subject, long templateId, String params, String tag)
            throws MailSendException {
        SesClient sesClient = this.getSesClient();
        SendEmailRequest request = new SendEmailRequest();
        request.setSubject(String.format("【%s】%s", tag, subject));
        Template template = new Template();
        template.setTemplateID(templateId);
        template.setTemplateData(params);
        request.setTemplate(template);
        request.setDestination(new String[]{toAddress});
        request.setFromEmailAddress(account);
        try {
            SendEmailResponse resp = sesClient.SendEmail(request);
            return resp.getMessageId();
        } catch (TencentCloudSDKException e) {
            throw new MailSendException(e);
        }
    }

    /**
     * 通过腾讯云邮件服务指定账号发送一封邮件，详见 https://cloud.tencent.com/document/api/1288/51034
     *
     * @param account   发信账号，最终会显示在发信人上（如 robot -> robot@smtp.foo.com）
     * @param toAddress 目标地址
     * @param subject   主题
     * @param htmlBody  内容（支持 HTML）
     * @param tag       邮件标签
     * @return 腾讯云消息 id
     */
    public String sendMail(String account, String toAddress, String subject, String htmlBody, String tag)
            throws MailSendException {
        SesClient sesClient = this.getSesClient();
        SendEmailRequest request = new SendEmailRequest();
        request.setSubject(String.format("【%s】%s", tag, subject));
        Simple simple = new Simple();
        simple.setHtml(Base64Utils.encodeToString(htmlBody.getBytes()));
        request.setSimple(simple);
        request.setDestination(new String[]{toAddress});
        request.setFromEmailAddress(account);
        try {
            SendEmailResponse resp = sesClient.SendEmail(request);
            return resp.getMessageId();
        } catch (TencentCloudSDKException e) {
            throw new MailSendException(e);
        }
    }

    private SesClient getSesClient() {
        // TODO:: optimize
        ThirdAccountDO data = this.data();
        Credential cred = new Credential(data.getAppId(), data.getAppSecret());
        SesClient sesClient = new SesClient(cred, TencentCloud.Region.HONG_KONG.key());
        return sesClient;
    }

    /**
     * 发送短信
     *
     * @param templateId 短信模板 id
     * @param phone      接受方手机号码（列表，逗号隔开）
     * @param signature  短信签名
     * @param params     参数
     */
    public void sendSMS(int templateId, String phone, String signature, String[] params)
            throws SMSSendException {
        // TODO:: sms client
        SmsSingleSender sender = new SmsSingleSender(this.getAppid(), this.data().getAppSecret());
        try {
            SmsSingleSenderResult result = sender.sendWithParam("86", phone, templateId, params, signature, null, null);
            if (result.result > 1000) {
                throw new SMSSendException("请求腾讯云发送短信失败，result: %d, msg: %s", result.result, result.errMsg);
            }
        } catch (HTTPException e) {
            throw new SMSSendException("HTTP 请求错误", e);
        } catch (IOException e) {
            throw new SMSSendException("I/O 异常", e);
        }
    }

    /**
     * 获取账号 app id 并解析为 int 类型
     */
    private int getAppid() {
        try {
            return Integer.parseInt(this.data().getAppId());
        } catch (NumberFormatException e) {
            throw new DataErrorException("腾讯云账号", this.id(), "app id 应为整型");
        }
    }

    /**
     * 获取主账号下的子账号实体
     *
     * @param name 子账号名称（用户名即可，示例：sub，会被识别为 sub@main_id.onaliyun.com）
     */
    public TencentCloudAccount getSubAccount(String name) {
        throw new NotImplementedException();
    }

    /**
     * 腾讯云 Open API 访问异常
     */
    public static class OApiAccessException extends DomainException {
        public OApiAccessException(String message) {
            super(message);
        }

        public OApiAccessException(String message, Object... args) {
            super(message, args);
        }

        public OApiAccessException(String message, Throwable cause) {
            super(message, cause);
        }

        public OApiAccessException(TencentCloudSDKException cause) {
            super(String.format("腾讯云 Open API 请求失败：%s", cause.getMessage()), cause);
        }
    }

    public static class MailSendException extends OApiAccessException {
        public MailSendException(String message) {
            super(message);
        }

        public MailSendException(TencentCloudSDKException cause) {
            super(cause);
        }
    }

    public static class SMSSendException extends OApiAccessException {
        public SMSSendException(String message, Object... args) {
            super(message, args);
        }

        public SMSSendException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
