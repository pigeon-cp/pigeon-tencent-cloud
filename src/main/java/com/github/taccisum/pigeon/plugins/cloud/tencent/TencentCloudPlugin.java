package com.github.taccisum.pigeon.plugins.cloud.tencent;

import com.github.taccisum.pigeon.core.entity.core.PigeonPlugin;
import com.github.taccisum.pigeon.plugins.cloud.tencent.spring.SpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginWrapper;

/**
 * Pigeon 腾讯云集成插件
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class TencentCloudPlugin extends PigeonPlugin {
    public TencentCloudPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected Class<?> getSpringConfigurationClass() {
        return SpringConfiguration.class;
    }
}
