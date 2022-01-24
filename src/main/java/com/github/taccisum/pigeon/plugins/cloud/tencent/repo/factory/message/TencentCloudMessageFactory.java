package com.github.taccisum.pigeon.plugins.cloud.tencent.repo.factory.message;

import com.github.taccisum.pigeon.core.entity.core.Message;
import com.github.taccisum.pigeon.core.repo.factory.MessageFactory;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.message.TencentCloudSMS;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.message.TencentCloudMail;
import com.github.taccisum.pigeon.plugins.cloud.tencent.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class TencentCloudMessageFactory implements MessageFactory {
    @Override
    public Message create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case "MAIL":
                return new TencentCloudMail(id);
            case "SMS":
                return new TencentCloudSMS(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.TENCENT_CLOUD.match(o.getSpType());
    }
}
