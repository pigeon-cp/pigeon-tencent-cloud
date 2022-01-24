package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp;

import com.github.taccisum.domain.core.DomainException;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

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
     * 通过腾讯云邮件服务指定账号发送一封邮件，详见 TODO::
     *
     * @param account     发信账号，最终会显示在发信人上（如 robot -> robot@smtp.foo.com）
     * @param toAddresses 目标地址，支持指定多个，收件人之间用逗号隔开
     * @param subject     主题
     * @param htmlBody    内容（支持 HTML）
     * @param tag         邮件标签
     */
    public void sendMailVia(String account, String toAddresses, String subject, String htmlBody, String tag, MailOptions opts)
            throws MailSendException {
        // TODO:: 暂时写死
        TencentCloud.Region region = TencentCloud.Region.HANG_ZHOU;
        opts = Optional.ofNullable(opts).orElse(new MailOptions());
        log.info(htmlBody);
    }

    /**
     * 发送短信
     *
     * @param templateCode 短信模板 code
     * @param phone        接受方手机号码
     * @param signature    短信签名
     * @param params       参数
     */
    public void sendSMS(String templateCode, String phone, String signature, String params)
            throws SMSSendException {
//        System.setProperty("sun.net.client.defaultConnectTimeout", "");
//        System.setProperty("sun.net.client.defaultReadTimeout", "");

        TencentCloud.Region region = TencentCloud.Region.HANG_ZHOU;

        log.info("【{}】发送短信至 {}，使用模板 {}", signature, phone, templateCode);
    }

    /**
     * 获取主账号下的子账号实体
     *
     * @param name 子账号名称（用户名即可，示例：sub，会被识别为 sub@main_id.onaliyun.com）
     */
    public TencentCloudAccount getSubAccount(String name) {
        throw new NotImplementedException();
    }

    @Data
    public static class MailOptions {
        /**
         * 发信人昵称，长度小于 15 个字符。
         */
        private String nickname;
    }

    /**
     * 腾讯云 Open API 访问异常
     */
    public static class OApiAccessException extends DomainException {
        public OApiAccessException(String message) {
            super(message);
        }
    }

    public static class MailSendException extends OApiAccessException {
        public MailSendException(String message) {
            super(message);
        }
    }

    public static class SMSSendException extends OApiAccessException {
        public SMSSendException(String message) {
            super(message);
        }
    }
}
