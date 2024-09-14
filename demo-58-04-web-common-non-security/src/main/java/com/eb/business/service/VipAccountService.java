package com.eb.business.service;

import com.eb.mp.mysql.mapper.business.VipAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VipAccountService {
    private final VipAccountMapper baseMapper;
}
