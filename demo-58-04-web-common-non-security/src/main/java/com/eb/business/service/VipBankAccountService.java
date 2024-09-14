package com.eb.business.service;

import com.eb.constant.ErrorCodeConstants;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.mp.mysql.entity.business.VipBankAccountEntity;
import com.eb.mp.mysql.mapper.business.VipBankAccountMapper;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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
public class VipBankAccountService {
    private final VipBankAccountMapper baseMapper;

    public VipBankAccountEntity queryById(Long id) {
        if (id == null) {
            return null;
        }

        return baseMapper.selectById(id);
    }

    public VipBankAccountEntity queryByCardNo(@NonNull String cardNo) {
        return baseMapper.queryEntityByCardNo(cardNo);
    }

    @Transactional
    public boolean enableBankAccount(String code, Long ID) {
        if (code == null) {
            log.error("enableBankAccount failed, code is null");
            return false;
        }

        if (ID == 0) {
            log.error("enableBankAccount failed, ID is 0");
            return false;
        }

//        VipBankAccountEntity historyEntity = queryByCardNo(cardNo);
//        if (historyEntity == null) {
//            log.error("enableBankAccount failed, code: {}, cardNo: {}", code, cardNo);
//            return false;
//        }

        VipBankAccountEntity historyEntity = new VipBankAccountEntity().setCode(code).setId(ID).setEnable(EnableStatusEnums.ENABLE);
        baseMapper.disableByCode(code);
        historyEntity.setUpdated(new Date());
        baseMapper.updateById(historyEntity);

        return true;
    }

    public void disableByCode(String code) {
        baseMapper.disableByCode(code);
    }

    public void updateById(VipBankAccountEntity updateEntity) {
        if (updateEntity == null) {
            return;
        }

        if (updateEntity.getId() == null) {
            log.error("updateById failed, {} id is null", VipBankAccountEntity.class.getSimpleName());
            throw ExceptionUtil.business(ErrorCodeConstants.KEY_ID_LOST);
        }

        VipBankAccountEntity historyEntity = queryByCardNo(updateEntity.getCardNo());
        if (historyEntity != null && !historyEntity.getId().equals(updateEntity.getId())) {
            throw ExceptionUtil.business(ErrorCodeConstants.BANK_NO_EXISTS, updateEntity.getCardNo());
        }

        updateEntity.setUpdated(new Date());
        baseMapper.updateById(updateEntity);
    }

    public Long create(VipBankAccountEntity createEntity) {
        if (createEntity == null) {
            return null;
        }

        if (createEntity.getId() != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.KEY_MUST_NULL);
        }

        VipBankAccountEntity historyEntity = queryByCardNo(createEntity.getCardNo());
        if (historyEntity != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.BANK_NO_EXISTS, createEntity.getCardNo());
        }

        Date now = new Date();
        createEntity.setUpdated(now).setCreated(now);
        baseMapper.insert(createEntity);
        return createEntity.getId();
    }

    public VipBankAccountEntity queryEntityByCode(@Nullable String code) {
        if (code == null) {
            return null;
        }

        return baseMapper.queryEntityByCodeAndEnable(code);
    }
}
