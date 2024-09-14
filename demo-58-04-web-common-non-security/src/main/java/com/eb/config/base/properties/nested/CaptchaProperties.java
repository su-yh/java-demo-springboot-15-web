package com.eb.config.base.properties.nested;

import lombok.Data;

/**
 * 验证码
 *
 * @author suyh
 * @since 2024-09-02
 */
@Data
public class CaptchaProperties {
    /**
     * 启用/禁用 2FA
     */
    private boolean twoFactorAuthEnabled = true;
}
