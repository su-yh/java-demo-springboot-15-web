package com.eb.business.service;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.WeightConfigEntity;
import com.eb.mp.mysql.mapper.business.WeightConfigMapper;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeightConfigService {
    private final WeightConfigMapper baseMapper;

    public PageResult<WeightConfigEntity> listPage(
            PageParam pageParam, @NonNull WeightConfigEntity queryEntity) {
        return baseMapper.queryPage(pageParam, queryEntity);
    }

    public WeightConfigEntity queryById(@NonNull Long id) {
        return baseMapper.selectById(id);
    }

    public void create(@NonNull WeightConfigEntity entity) {
        baseMapper.insert(entity);
    }

    @Transactional
    public void updateById(@NonNull WeightConfigEntity entity) {
        WeightConfigEntity historyEntity = baseMapper.selectById(entity.getId());
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.WEIGHT_CONFIG_ID_NOT_EXISTS, entity.getId());
        }

        baseMapper.updateById(entity);
    }

    @Transactional
    public void deleteById(@NonNull Long id) {
        WeightConfigEntity historyEntity = baseMapper.selectById(id);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.WEIGHT_CONFIG_ID_NOT_EXISTS, id);
        }

        baseMapper.deleteById(id);
    }
}
