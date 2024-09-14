package com.eb.business.controller;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.business.dto.UuidPlus;
import com.eb.business.dto.sms.req.SmartSmsQueryReqDto;
import com.eb.business.dto.sms.req.SmartSmsStatisticQueryReqDto;
import com.eb.business.dto.sms.rsp.SmartSmsStatisticRspDto;
import com.eb.business.service.SmartSmsRecordService;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SmartSmsRecordEntity;
import com.eb.util.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "smartHelper短信记录")
@RestController
@RequestMapping("/smart/sms")
@RequiredArgsConstructor
@Validated
@Slf4j
public class SmartSmsRecordController {
    private final SmartSmsRecordService baseService;

    @Operation(summary = "短信收款-明细列表(分页)")
    @RequestMapping(value = "/listPage/detail", method = RequestMethod.GET)
    public PageResult<SmartSmsRecordEntity> listPage(
            PageParam pageParam, SmartSmsQueryReqDto queryReqDto) {
        if (queryReqDto.getDates() == null) {
            Integer dates = DateUtils.convertToInteger(LocalDate.now());
            queryReqDto.setDates(dates);
        }
        return baseService.listPage(pageParam, queryReqDto);
    }

    @Operation(summary = "短信收款统计-代理(分页)")
    @RequestMapping(value = "/listPage/statistic", method = RequestMethod.GET)
    public PageResult<SmartSmsStatisticRspDto> listPageStatistic(
            PageParam pageParam, SmartSmsStatisticQueryReqDto queryReqDto) {
        DatesRangeQueryReqDto.defaultDatesRange(queryReqDto, -3, -1);
        PageResult<SmartSmsStatisticRspDto> pageResult = baseService.listPageStatistic(pageParam, queryReqDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }
}
