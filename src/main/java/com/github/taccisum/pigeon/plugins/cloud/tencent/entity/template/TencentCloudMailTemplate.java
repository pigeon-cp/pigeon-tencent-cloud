package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.template;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.entity.core.template.MailTemplate;

/**
 * 腾讯云邮件模板
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class TencentCloudMailTemplate extends MailTemplate {
    public TencentCloudMailTemplate(Long id) {
        super(id);
    }

    public long getThirdTemplateId() {
        String code = this.data().getThirdCode();
        try {
            return Long.parseLong(code);
        } catch (NumberFormatException e) {
            throw new DataErrorException("腾讯云邮件模板", this.id(), "关联的腾讯云邮件模板 id 格式有误");
        }
    }
}
