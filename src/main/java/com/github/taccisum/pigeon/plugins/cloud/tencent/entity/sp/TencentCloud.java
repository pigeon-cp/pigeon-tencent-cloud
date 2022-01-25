package com.github.taccisum.pigeon.plugins.cloud.tencent.entity.sp;

import com.github.taccisum.domain.core.DomainException;
import com.github.taccisum.domain.core.exception.DataNotFoundException;
import com.github.taccisum.pigeon.core.entity.core.ServiceProvider;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.entity.core.sp.MailServiceProvider;
import com.github.taccisum.pigeon.core.entity.core.sp.SMSServiceProvider;
import com.github.taccisum.pigeon.core.repo.ThirdAccountRepo;
import com.github.taccisum.pigeon.plugins.cloud.tencent.enums.SpType;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Random;

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
     * 可用区域，更多请参考 https://cloud.tencent.com/document/product/213/6091
     */
    public enum Region {
        /**
         * 华南地区-广州
         */
        GUANG_ZHOU("ap-guangzhou", 7),
        /**
         * 华南地区-深圳金融
         */
        SHEN_ZHEN_FSI("ap-shenzhen-fsi", 3),
        /**
         * 华南地区-上海
         */
        SHANG_HAI("ap-shanghai", 5),
        SHANG_HAI_FSI("ap-shanghai-fsi", 3),
        NAN_JING("ap-nanjing", 3),
        BEI_JING("ap-beijing", 7),
        CHENG_DU("ap-chengdu", 2),
        CHONG_QING("ap-chongqing", 1),
        HONG_KONG("ap-hongkong", 3),
        ;

        private static final Random R = new Random();

        /**
         * 区域 key
         */
        private final String key;
        /**
         * 可用区最大索引
         */
        private final int maxIndex;

        Region(String key, int maxIndex) {
            this.key = key;
            this.maxIndex = maxIndex;
        }

        /**
         * 获取此区域 key
         */
        public String key() {
            return this.key;
        }

        /**
         * 例 {@code Region.GUANG_ZHOU.key(1)} -> "ap-guangzhou-1"
         *
         * @param index 可用区索引
         */
        public String zoneKey(int index) {
            index = Math.max(index, 1);
            index = Math.min(index, maxIndex);
            return String.format("%s-%d", this.key, index);
        }

        /**
         * 获取可用区 key（随机索引）
         */
        public String zoneKey() {
            // [1, max_index] 随机取值
            int index = R.nextInt(maxIndex) + 1;
            return this.zoneKey(index);
        }
    }
}
