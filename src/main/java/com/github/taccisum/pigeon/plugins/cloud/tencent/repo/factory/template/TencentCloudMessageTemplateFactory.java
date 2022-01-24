package com.github.taccisum.pigeon.plugins.cloud.tencent.repo.factory.template;

import com.github.taccisum.pigeon.core.entity.core.MessageTemplate;
import com.github.taccisum.pigeon.core.repo.factory.MessageTemplateFactory;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.template.TencentCloudMailTemplate;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.template.TencentCloudSMSTemplate;
import com.github.taccisum.pigeon.plugins.cloud.tencent.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class TencentCloudMessageTemplateFactory implements MessageTemplateFactory {
    @Override
    public MessageTemplate create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case "MAIL":
                return new TencentCloudMailTemplate(id);
            case "SMS":
                return new TencentCloudSMSTemplate(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria criteria) {
        return SpType.TENCENT_CLOUD.match(criteria.getSpType());
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
