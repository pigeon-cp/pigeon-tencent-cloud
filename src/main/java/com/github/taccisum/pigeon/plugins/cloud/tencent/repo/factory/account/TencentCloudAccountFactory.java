package com.github.taccisum.pigeon.plugins.cloud.tencent.repo.factory.account;

import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.repo.factory.ThirdAccountFactory;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp.TencentCloudAccount;
import com.github.taccisum.pigeon.plugins.cloud.tencent.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class TencentCloudAccountFactory implements ThirdAccountFactory {
    @Override
    public ThirdAccount create(Long id, Criteria criteria) {
        return new TencentCloudAccount(id);
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.TENCENT_CLOUD.match(o.getSpType());
    }
}
