package com.leomaster.mp.entity.mysql;

import lombok.Data;

import java.io.Serializable;

/**
 * @author suyh
 * @since 2024-08-23
 */
@Data
public abstract class AbstractUserEntity implements Serializable {
    private static final long serialVersionUID = 5437316891585713486L;

    private String uid;

    /**
     * gaid
     */
    private String gaid;

    /**
     * 归因渠道号
     */
    private String channel;
}
