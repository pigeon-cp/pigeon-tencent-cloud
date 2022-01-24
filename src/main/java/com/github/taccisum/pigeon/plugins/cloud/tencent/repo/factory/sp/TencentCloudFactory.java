package com.github.taccisum.pigeon.plugins.cloud.tencent.repo.factory.sp;

import com.github.taccisum.pigeon.core.entity.core.ServiceProvider;
import com.github.taccisum.pigeon.core.repo.factory.ServiceProviderFactory;
import com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp.TencentCloud;
import com.github.taccisum.pigeon.plugins.cloud.tencent.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class TencentCloudFactory implements ServiceProviderFactory {
    @Override
    public ServiceProvider create(String id, Criteria o) {
        return new TencentCloud();
    }

    @Override
    public boolean match(String id, Criteria o) {
        return SpType.TENCENT_CLOUD.match(o.getSpType());
    }
}
