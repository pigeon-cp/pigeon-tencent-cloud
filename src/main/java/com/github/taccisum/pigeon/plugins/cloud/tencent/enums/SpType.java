package com.github.taccisum.pigeon.plugins.cloud.tencent.enums;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public enum SpType {
    /**
     * 腾讯云
     */
    TENCENT_CLOUD(Lists.newArrayList("TENCENT_CLOUD", "TENCENT-CLOUD"));

    /**
     * 可用 keys
     */
    private List<String> availableKeys;

    SpType(List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    public boolean match(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return this.availableKeys.contains(key.toUpperCase());
    }
}
