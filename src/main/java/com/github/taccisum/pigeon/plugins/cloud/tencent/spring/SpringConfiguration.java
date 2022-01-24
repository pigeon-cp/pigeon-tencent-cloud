package com.github.taccisum.pigeon.plugins.cloud.tencent.spring;

import com.github.taccisum.pigeon.plugins.cloud.tencent.TencentCloudPlugin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Configuration
@ComponentScan(basePackageClasses = TencentCloudPlugin.class)
public class SpringConfiguration {
}
