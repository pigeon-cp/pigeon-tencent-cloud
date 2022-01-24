package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.entity.core.MessageTemplate;
import com.github.taccisum.pigeon.core.entity.core.message.SMS;
import com.github.taccisum.pigeon.core.entity.core.sp.SMSServiceProvider;
import com.github.taccisum.pigeon.core.repo.MessageTemplateRepo;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp.TencentCloud;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp.TencentCloudAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 腾讯云短信消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class TencentCloudSMS extends SMS {
    @Autowired
    private MessageTemplateRepo messageTemplateRepo;

    public TencentCloudSMS(Long id) {
        super(id);
    }

    @Override
    public boolean isRealTime() {
        return false;
    }

    @Override
    protected void doDelivery() throws Exception {
        MessageDO data = this.data();

        TencentCloudAccount account = this.getServiceProvider()
                .getAccountOrThrow(data.getSpAccountId());

        MessageTemplate template = this.getTemplate();

        if (template != null) {
            account.sendSMS(
                    template.data().getThirdCode(),
                    data.getTarget(),
                    "豌豆思维VIPThink",
                    data.getParams()
            );
        } else {
            throw new UnsupportedOperationException("暂不支持非模板消息");
        }
    }

    private MessageTemplate getTemplate() {
        return messageTemplateRepo.get(this.data().getTemplateId())
                .orElse(null);
    }

    @Override
    public TencentCloud getServiceProvider() {
        SMSServiceProvider sp = super.getServiceProvider();
        if (sp instanceof TencentCloud) {
            return (TencentCloud) sp;
        }
        throw new DataErrorException("TencentCloudSMS.ServiceProvider", this.id(), "腾讯云短信消息可能关联了错误的服务提供商：" + sp.getType() + "，请检查数据是否异常");
    }
}
