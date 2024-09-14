package com.eb.business.service;

import com.eb.business.dto.vip.req.VipAccountQueryReqDto;
import com.eb.business.dto.vip.rsp.VipAccountRspDto;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.TransferRecordEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * @author suyh
 * @since 2024-09-06
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SupportTeamService {
    private final SchedulingTransferRecordService schedulingTransferRecordService;
    private final TransferRecordService transferRecordService;
    private final VipService vipService;

    public PageResult<SchedulingTransferRecordEntity> listPageByCpOrSvipOrderLike(
            PageParam pageParam, @Nullable String orderLike) {
        return schedulingTransferRecordService.listPageByCpOrSvipOrderLike(pageParam, orderLike);
    }

    public PageResult<SchedulingTransferRecordEntity> listPageByUtrLike(
            PageParam pageParam, @Nullable String utrLike) {
        return schedulingTransferRecordService.listPageByUtrLike(pageParam, utrLike);
    }

    public PageResult<VipAccountRspDto> listPageByFinancialLike(
            PageParam pageParam, VipAccountQueryReqDto queryDto) {
        return vipService.accountListPage(pageParam, queryDto);
    }

    public PageResult<TransferRecordEntity> listPageByUidLike(
            PageParam pageParam, @Nullable String uidLike) {
        return transferRecordService.listPageByUidLike(pageParam, uidLike);
    }
}
