package com.eb.business.service;

import com.eb.constant.ErrorCodeConstants;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.mp.mysql.entity.business.VipTppEntity;
import com.eb.mp.mysql.mapper.business.VipTppMapper;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VipTppService {
    private final VipTppMapper baseMapper;

    public VipTppEntity queryByTpp(@NonNull String tpp) {
        return baseMapper.queryByTpp(tpp);
    }

    @Transactional
    public boolean enableTpp(String code, Long ID) {
        if (code == null) {
            log.error("enableTpp failed, code is null");
            return false;
        }

        if (ID == 0) {
            log.error("enableTpp failed, id is 0");
            return false;
        }

//        VipTppEntity historyEntity = queryByTpp(tpp);
//        if (historyEntity == null) {
//            log.error("enableTpp failed, code: {}, tpp: {}", code, tpp);
//            return false;
//        }

        baseMapper.disableByCode(code);
        //historyEntity.setEnable(EnableStatusEnums.ENABLE);
        VipTppEntity historyEntity = new VipTppEntity().setId(ID).setCode(code).setEnable(EnableStatusEnums.ENABLE);
        historyEntity.setUpdated(new Date());
        baseMapper.updateById(historyEntity);

        return true;
    }

    public void disableByCode(String code)
    {
        baseMapper.disableByCode(code);
    }

    public void updateById(VipTppEntity updateEntity) {
        if (updateEntity == null) {
            return;
        }

        if (updateEntity.getId() == null) {
            log.error("updateById failed, {} id is null", VipTppEntity.class.getSimpleName());
            throw ExceptionUtil.business(ErrorCodeConstants.KEY_ID_LOST);
        }

        VipTppEntity historyEntity = queryByTpp(updateEntity.getTpp());
        if (historyEntity != null && !historyEntity.getId().equals(updateEntity.getId())) {
            throw  ExceptionUtil.business(ErrorCodeConstants.TPP_EXISTS, updateEntity.getTpp());
        }

        updateEntity.setUpdated(new Date());
        baseMapper.updateById(updateEntity);
    }

    public Long create(VipTppEntity createEntity) {
        if (createEntity == null) {
            return null;
        }

        if (createEntity.getId() != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.KEY_MUST_NULL);
        }

        VipTppEntity historyEntity = queryByTpp(createEntity.getTpp());
        if (historyEntity != null) {
            throw  ExceptionUtil.business(ErrorCodeConstants.TPP_EXISTS, createEntity.getTpp());
        }

        Date now = new Date();
        createEntity.setUpdated(now).setCreated(now);
        baseMapper.insert(createEntity);

        return createEntity.getId();
    }

    public VipTppEntity queryEntityByCode(String code) {
        return baseMapper.queryEntityByCodeAndEnable(code);
    }

    public VipTppEntity queryById(Long id) {
        if (id == null) {
            return null;
        }

        return baseMapper.selectById(id);
    }
}
