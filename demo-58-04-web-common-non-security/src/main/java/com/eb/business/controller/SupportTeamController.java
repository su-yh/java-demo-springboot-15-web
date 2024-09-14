package com.eb.business.controller;

import com.eb.business.dto.UuidPlus;
import com.eb.business.dto.vip.req.VipAccountQueryReqDto;
import com.eb.business.dto.vip.rsp.VipAccountRspDto;
import com.eb.business.service.SupportTeamService;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.TransferRecordEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客服模块
 *
 * @author suyh
 * @since 2024-09-04
 */
@Tag(name = "客服模块")
@RestController
@RequestMapping("/support/team")
@RequiredArgsConstructor
@Validated
@Slf4j
public class SupportTeamController {
    private final SupportTeamService baseService;

    @Operation(summary = "订单状态查询(分页)")
    @RequestMapping(value = "/listPage/byOrderNo", method = RequestMethod.GET)
    public PageResult<SchedulingTransferRecordEntity> listPageByCpOrSvipOrderLike(
            PageParam pageParam, @RequestParam(required = false) String orderNoLike) {
        return baseService.listPageByCpOrSvipOrderLike(pageParam, orderNoLike);
    }

    @Operation(summary = "UTR查询(分页)")
    @RequestMapping(value = "/listPage/byUtr", method = RequestMethod.GET)
    public PageResult<SchedulingTransferRecordEntity> listPageByUtrLike(
            PageParam pageParam, @RequestParam(required = false) String utrLike) {
        return baseService.listPageByUtrLike(pageParam, utrLike);
    }

    @Operation(summary = "收款信息查询(分页)")
    @RequestMapping(value = "/listPage/byFinancial", method = RequestMethod.GET)
    public PageResult<VipAccountRspDto> listPageByFinancialLike(
            PageParam pageParam, VipAccountQueryReqDto queryDto) {
        PageResult<VipAccountRspDto> pageResult = baseService.listPageByFinancialLike(pageParam, queryDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }

    @Operation(summary = "购买记录查询(分页)")
    @RequestMapping(value = "/listPage/byUid", method = RequestMethod.GET)
    public PageResult<TransferRecordEntity> listPageByUidLike(
            PageParam pageParam, @RequestParam(required = false) String uidLike) {
        return baseService.listPageByUidLike(pageParam, uidLike);
    }
}
