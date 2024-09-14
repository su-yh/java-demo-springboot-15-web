package com.eb.business.service;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import com.eb.mp.mysql.mapper.business.UtrUploadRecordMapper;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UtrUploadRecordService {
    private final UtrUploadRecordMapper baseMapper;

    public Long createEntity(UtrUploadRecordEntity entity) {
        Date now = new Date();
        entity.setCreated(now).setUpdated(now);
        baseMapper.insert(entity);
        return entity.getId();
    }

    public UtrUploadRecordEntity queryEntityById(Long id) {
        if (id == null) {
            return null;
        }

        return baseMapper.selectById(id);
    }

    public void updateById(UtrUploadRecordEntity utrUploadRecordEntity) {
        if (utrUploadRecordEntity.getId() == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR_PARAM, "id cannot null");
        }

        baseMapper.updateById(utrUploadRecordEntity);
    }

    public UtrUploadRecordEntity queryEntityByCpOrder(String cpOrder) {
        if (cpOrder == null) {
            return null;
        }

        UtrUploadRecordEntity entity = baseMapper.queryEntityByCpOrderRevise(cpOrder);
        if (entity != null) {
            return entity;
        }

        entity = baseMapper.queryEntityByCpOrder(cpOrder);

        return entity;
    }

    public UtrUploadRecordEntity queryEntityByAuditOrder(String order) {
        if (!StringUtils.hasText(order)) {
            return null;
        }

        List<UtrUploadRecordEntity> entities = baseMapper.queryListByAuditOrder(order);
        if (entities == null || entities.isEmpty()) {
            return null;
        }

        // 取最新的一条记录
        if (entities.size() > 1) {
            log.warn("queryEntityByAuditOrder entities size > 1, size: {}", entities.size());
        }

        return entities.get(0);
    }
}
