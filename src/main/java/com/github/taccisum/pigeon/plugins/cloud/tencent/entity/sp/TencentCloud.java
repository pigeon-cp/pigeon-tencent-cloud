package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp;

import com.github.taccisum.domain.core.DomainException;
import com.github.taccisum.domain.core.exception.DataNotFoundException;
import com.github.taccisum.pigeon.core.entity.core.ServiceProvider;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.entity.core.sp.MailServiceProvider;
import com.github.taccisum.pigeon.core.entity.core.sp.SMSServiceProvider;
import com.github.taccisum.pigeon.core.repo.ThirdAccountRepo;
import com.github.taccisum.pigeon.plugins.cloud.tencent.enums.SpType;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 腾讯云
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class TencentCloud extends ServiceProvider.Base implements
        MailServiceProvider,
        SMSServiceProvider {
    @Resource
    private ThirdAccountRepo thirdAccountRepo;

    public TencentCloud() {
        super("TENCENT_CLOUD");
    }

    /**
     * 获取腾讯云账号实体
     *
     * @param id 账号 id
     */
    @Override
    public TencentCloudAccount getAccountOrThrow(Long id) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("腾讯云账号", id);
        ThirdAccount account = this.getAccount(id)
                .orElseThrow(() -> ex);

        if (account instanceof TencentCloudAccount) {
            return (TencentCloudAccount) account;
        }
        throw ex;
    }

    /**
     * 获取腾讯云账号实体，可以直接获取主账号或子账号（无主账号权限时）
     *
     * @param name 账号名，主账号（账号 id，示例：15201132xxxxxxxx）或子账号（完全名称，示例：sub@15201132xxxxxxxx.onaliyun.com）均可
     */
    public TencentCloudAccount getAccount(String name) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("腾讯云账号", name);
        ThirdAccount account = thirdAccountRepo.getByUsername(name)
                .orElseThrow(() -> ex);

        if (account instanceof TencentCloudAccount) {
            return (TencentCloudAccount) account;
        }
        throw ex;
    }

    @Override
    protected boolean match(ThirdAccount thirdAccount) {
        return SpType.TENCENT_CLOUD.match(thirdAccount.data().getSpType());
    }

    /**
     * 可用区域
     */
    public enum Region {
        /**
         * 中国杭州
         */
        HANG_ZHOU("cn-hangzhou");

        @Getter
        @Accessors(fluent = true)
        private String key;

        Region(String key) {
            this.key = key;
        }
    }
}
