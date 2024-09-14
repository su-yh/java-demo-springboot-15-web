package com.eb.business.controller;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.business.dto.order.req.SchedulingTransferRecordQueryReqDto;
import com.eb.business.service.SchedulingTransferRecordService;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
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
@Tag(name = "订单")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrderController {
    private final SchedulingTransferRecordService schedulingTransferRecordService;

    @Operation(summary = "订单查询(分页)")
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public PageResult<SchedulingTransferRecordEntity> listPage(
            PageParam pageParam, SchedulingTransferRecordQueryReqDto queryDto) {
        DatesRangeQueryReqDto.defaultDatesRange(queryDto, -7, 0);
        return schedulingTransferRecordService.listOrderPage(pageParam, queryDto);
    }

    @Operation(summary = "导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(
            WebRequest webRequest,
            HttpServletResponse response,
            SchedulingTransferRecordQueryReqDto queryDto) {
        DatesRangeQueryReqDto.defaultDatesRange(queryDto, -7, 0);
        List<SchedulingTransferRecordEntity> entityList = schedulingTransferRecordService.listOrder(queryDto);
        RuoyiExcelUtil<SchedulingTransferRecordEntity> util
                = new RuoyiExcelUtil<>(SchedulingTransferRecordEntity.class, webRequest.getLocale());
        util.exportExcel(response, entityList, "Sheet1");
    }
}
