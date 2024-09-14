package com.eb.business.controller;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.business.dto.UuidPlus;
import com.eb.business.dto.statistic.req.StatisticByCodeQueryReqDto;
import com.eb.business.dto.statistic.req.StatisticByDayQueryReqDto;
import com.eb.business.dto.statistic.rsp.StatisticByCodeQueryRspDto;
import com.eb.business.dto.statistic.rsp.StatisticByDatesDQaueryRspDto;
import com.eb.business.service.SchedulingTransferRecordService;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.rouyi.excel.poi.RuoyiExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Tag(name = "统计")
@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatisticController {
    private final SchedulingTransferRecordService schedulingTransferRecordService;

    @Operation(summary = "统计图表展示")
    @RequestMapping(value = "/list/table", method = RequestMethod.GET)
    public List<StatisticByDatesDQaueryRspDto> listTableStatistic(StatisticByDayQueryReqDto queryDto) {
        DatesRangeQueryReqDto.defaultDatesRange(queryDto, -7, 0);
        List<StatisticByDatesDQaueryRspDto> rspDtos = schedulingTransferRecordService.listStatisticByDates(queryDto);
        UuidPlus.setUuidList(rspDtos);
        return rspDtos;
    }

    @Operation(summary = "统计查询-按天：不分页")
    @RequestMapping(value = "/list/byDay", method = RequestMethod.GET)
    public PageResult<StatisticByDatesDQaueryRspDto> listStatisticByDates(StatisticByDayQueryReqDto queryDto) {
        DatesRangeQueryReqDto.defaultDatesRange(queryDto, -7, 0);
        List<StatisticByDatesDQaueryRspDto> dtos = schedulingTransferRecordService.listStatisticByDates(queryDto);
        // 前端已经做了分页，这里直接返回一个分页的格式给前端
        UuidPlus.setUuidList(dtos);
        return new PageResult<>(dtos, (long) dtos.size());
    }

    @Operation(summary = "统计查询(导出)-按天")
    @RequestMapping(value = "/export/byDay", method = RequestMethod.GET)
    public void exportStatisticByDay(
            WebRequest webRequest,
            HttpServletResponse response,
            StatisticByDayQueryReqDto queryDto) {
        DatesRangeQueryReqDto.defaultDatesRange(queryDto, -7, 0);
        List<StatisticByDatesDQaueryRspDto> resultList = schedulingTransferRecordService.listStatisticByDates(queryDto);
        RuoyiExcelUtil<StatisticByDatesDQaueryRspDto> util
                = new RuoyiExcelUtil<>(StatisticByDatesDQaueryRspDto.class, webRequest.getLocale());
        util.exportExcel(response, resultList, "Sheet1");
    }

    @Operation(summary = "统计查询-按代理商code：分页")
    @RequestMapping(value = "/listPage/byCode", method = RequestMethod.GET)
    public PageResult<StatisticByCodeQueryRspDto> listStatisticByCode(
            PageParam pageParam,  @Validated StatisticByCodeQueryReqDto queryDto) {
        PageResult<StatisticByCodeQueryRspDto> pageResult = schedulingTransferRecordService.listPageStatisticByCode(pageParam, queryDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }

    @Operation(summary = "统计查询-按代理商code：不分页")
    @RequestMapping(value = "/list/byCode", method = RequestMethod.GET)
    public PageResult<StatisticByCodeQueryRspDto> listStatisticByCode(
            @Validated StatisticByCodeQueryReqDto queryDto) {
        List<StatisticByCodeQueryRspDto> dtos = schedulingTransferRecordService.listStatisticByCode(queryDto);
        UuidPlus.setUuidList(dtos);
        // 前端已经做了分页，这里直接返回一个分页的格式给前端
        return new PageResult<>(dtos, (long) dtos.size());
    }

    @Operation(summary = "统计查询(导出)-按代理商code")
    @RequestMapping(value = "/export/byCode", method = RequestMethod.GET)
    public void exportStatisticByCode(
            WebRequest webRequest,
            HttpServletResponse response,
            StatisticByCodeQueryReqDto queryDto) {
        List<StatisticByCodeQueryRspDto> resultList = schedulingTransferRecordService.listStatisticByCode(queryDto);
        RuoyiExcelUtil<StatisticByCodeQueryRspDto> util
                = new RuoyiExcelUtil<>(StatisticByCodeQueryRspDto.class, webRequest.getLocale());
        util.exportExcel(response, resultList, "Sheet1");
    }

}
