package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.template;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.entity.core.template.SMSTemplate;

/**
 * 腾讯云消息模板
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class TencentCloudSMSTemplate extends SMSTemplate {
    public TencentCloudSMSTemplate(Long id) {
        super(id);
    }

    /**
     * 获取第三方模板 code 并转换为腾讯云整型模板 id
     */
    public int getThirdTemplateId() {
        String code = this.data().getThirdCode();
        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            throw new DataErrorException("腾讯云 SMS 模板", this.id(), "关联的腾讯云模板 id 格式有误");
        }
    }
}
