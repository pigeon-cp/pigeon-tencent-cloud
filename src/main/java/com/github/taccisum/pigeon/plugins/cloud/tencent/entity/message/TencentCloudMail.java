package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.entity.core.message.Mail;
import com.github.taccisum.pigeon.core.entity.core.sp.MailServiceProvider;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp.TencentCloud;
import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云邮件服务消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class TencentCloudMail extends Mail {
    public TencentCloudMail(Long id) {
        super(id);
    }

    @Override
    public boolean isRealTime() {
        return false;
    }

    @Override
    protected void doDelivery() {
        MessageDO data = this.data();
        this.getServiceProvider()
                .getAccountOrThrow(data.getSpAccountId())
                .sendMailVia(
                        data.getSender(),
                        data.getTarget(),
                        data.getTitle(),
                        data.getContent(),
                        data.getTag(),
                        null
                );
    }

    @Override
    public TencentCloud getServiceProvider() {
        MailServiceProvider sp = super.getServiceProvider();
        if (sp instanceof TencentCloud) {
            return (TencentCloud) sp;
        }
        throw new DataErrorException("TencentCloudMail.ServiceProvider", this.id(), "腾讯云邮件消息可能关联了错误的服务提供商：" + sp.getType() + "，请检查数据是否异常");
    }
}
