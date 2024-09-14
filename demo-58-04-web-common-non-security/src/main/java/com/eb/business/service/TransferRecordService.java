package com.eb.business.service;

import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.TransferRecordEntity;
import com.eb.mp.mysql.mapper.business.TransferRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransferRecordService {
    private final TransferRecordMapper baseMapper;

    public PageResult<TransferRecordEntity> listPageByUidLike(
            PageParam pageParam, @Nullable String uidLike) {
        return baseMapper.listPageByUidLike(pageParam, uidLike);
    }
}
