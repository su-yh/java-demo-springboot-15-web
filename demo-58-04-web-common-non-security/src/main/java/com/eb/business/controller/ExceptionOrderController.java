package com.eb.business.controller;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.business.dto.UuidPlus;
import com.eb.business.dto.base.CpOrderBody;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderAuditByCpOrderReviseUpdateReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderAuditByIdUpdateReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderStatisticByMchntNoQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderStatisticByUserQueryReqDto;
import com.eb.business.dto.exceptionorder.req.SchedulingOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByDatesRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByMchntNoRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByUserRspDto;
import com.eb.business.dto.exceptionorder.rsp.SchedulingTransferRecordAuditRspDto;
import com.eb.business.service.ExceptionOrderService;
import com.eb.constant.enums.ExceptionOrderSceneEnums;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.group.ValidationGroups;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import com.eb.mp.mysql.entity.custom.ExceptionOrderEntity;
import com.eb.mvc.authentication.CurrLoginUser;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.vo.ResponseResult;
import com.eb.rouyi.excel.poi.RuoyiExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Tag(name = "异常订单管理页面")
@RestController
@RequestMapping("/exception/order")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ExceptionOrderController {
    private final ExceptionOrderService baseService;

    @Operation(summary = "订单录入")
    @RequestMapping(value = "/utr/record/create", method = RequestMethod.POST)
    public Long utrRecordCreate(
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) UtrUploadRecordEntity entity) {
        return baseService.utrRecordCreate(entity);
    }

    @Operation(summary = "二级菜单项：订单处理(分页查询)")
    @RequestMapping(value = "/utr/record/listPage", method = RequestMethod.GET)
    public PageResult<ExceptionOrderEntity> listPageUtrRecord(
            PageParam pageParam, ExceptionOrderQueryReqDto queryReqDto) {
        return baseService.listPageUtrRecord(pageParam, queryReqDto);
    }

    @Operation(summary = "二级菜单项：订单处理(导出)")
    @RequestMapping(value = "/utr/record/export", method = RequestMethod.GET)
    public void listPageUtrRecord(
            WebRequest webRequest,
            HttpServletResponse response,
            ExceptionOrderQueryReqDto queryReqDto) {
        List<ExceptionOrderEntity> entityList = baseService.listUtrRecord(queryReqDto);
        RuoyiExcelUtil<ExceptionOrderEntity> util
                = new RuoyiExcelUtil<>(ExceptionOrderEntity.class, webRequest.getLocale());
        util.exportExcel(response, entityList, "Sheet1");
    }

    @Operation(summary = "【处理】通过cp订单号修改为待审核，并返回处理情形")
    @RequestMapping(value = "/utr/record/waiting/byCpOrder", method = RequestMethod.POST)
    public ResponseResult<ExceptionOrderSceneEnums> utrRecordWaitingByCpOrder(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated CpOrderBody cpOrderBody) {
        ExceptionOrderSceneEnums scene = baseService.updateAuditStatueByCpOrder(
                loginUser, cpOrderBody.getCpOrder(), OrderAuditStatusEnums.WAITING_PROCESS);
        return ResponseResult.ofSuccess(scene);
    }

    @Operation(summary = "【处理-情形：0+0_未匹配】数据匹配Or查询(分页)")
    @RequestMapping(value = "/scheduling/record/listPage", method = RequestMethod.GET)
    public PageResult<SchedulingTransferRecordEntity> listPageSchedulingRecordOr(
            PageParam pageParam, SchedulingOrderQueryReqDto queryReqDto) {
        return baseService.listPageSchedulingRecordOr(pageParam, queryReqDto);
    }

    @Operation(summary = "【处理-情形：0+0_未匹配】通过修正订单号，变更审核状态为待审核")
    @RequestMapping(value = "/utr/record/waiting/byCpOrderRevise", method = RequestMethod.POST)
    public void utrRecordWaitingByCpOrderRevise(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated ExceptionOrderAuditByCpOrderReviseUpdateReqDto updateReqDto) {
        baseService.updateAuditStatueByCpOrderRevise(loginUser, updateReqDto, OrderAuditStatusEnums.WAITING_PROCESS);
    }

    @Operation(summary = "【处理-情形：0+0_未匹配】通过修正订单号，变更审核状态为拒绝")
    @RequestMapping(value = "/utr/record/reject/byCpOrderRevise", method = RequestMethod.POST)
    public void utrRecordRejectByCpOrderRevise(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated ExceptionOrderAuditByCpOrderReviseUpdateReqDto updateReqDto) {
        updateReqDto.setAmount(null).setPnum(null); // 拒绝时忽略这两个值
        baseService.updateAuditStatueByCpOrderRevise(loginUser, updateReqDto, OrderAuditStatusEnums.REJECTED);
    }

    @Operation(summary = "【二级菜单项】订单审核，查询(分页)")
    @RequestMapping(value = "/scheduling/record/audit/listPage", method = RequestMethod.GET)
    public PageResult<SchedulingTransferRecordAuditRspDto> listPageSchedulingRecordAudit(
            PageParam pageParam, ExceptionOrderQueryReqDto queryReqDto) {
        return baseService.listPageSchedulingRecordAudit(pageParam, queryReqDto);
    }

    @Operation(summary = "【二级菜单项】订单审核，审核操作")
    @RequestMapping(value = "/scheduling/record/audit/update/byId", method = RequestMethod.POST)
    public void schedulingRecordAuditById(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) ExceptionOrderAuditByIdUpdateReqDto updateReqDto) {
        baseService.schedulingRecordAuditById(loginUser, updateReqDto);
    }

    @Operation(summary = "【三级菜单项-异常订单统计】按天(分页)")
    @RequestMapping(value = "/statistic/listPage/byDay", method = RequestMethod.GET)
    public PageResult<ExceptionOrderStatisticByDatesRspDto> listPageStatisticByDates(
            PageParam pageParam, DatesRangeQueryReqDto queryReqDto) {
        // 默认最近8 天
        DatesRangeQueryReqDto.defaultDatesRange(queryReqDto, -7, 0);
        PageResult<ExceptionOrderStatisticByDatesRspDto> pageResult
                = baseService.listPageStatisticExceptionOrderByDates(pageParam, queryReqDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }

    @Operation(summary = "【三级菜单项-异常订单统计】按运营人员(分页)")
    @RequestMapping(value = "/statistic/listPage/byUser", method = RequestMethod.GET)
    public PageResult<ExceptionOrderStatisticByUserRspDto> listPageStatisticByUser(
            PageParam pageParam, ExceptionOrderStatisticByUserQueryReqDto queryReqDto) {
        // 默认最近3 天
        DatesRangeQueryReqDto.defaultDatesRange(queryReqDto, -2, 0);
        PageResult<ExceptionOrderStatisticByUserRspDto> pageResult
                = baseService.listPageStatisticExceptionByUser(pageParam, queryReqDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }

    @Operation(summary = "【三级菜单项-异常订单统计】按商户号(分页)")
    @RequestMapping(value = "/statistic/listPage/byMchntNo", method = RequestMethod.GET)
    public PageResult<ExceptionOrderStatisticByMchntNoRspDto> listPageStatisticByMchntNo(
            PageParam pageParam, ExceptionOrderStatisticByMchntNoQueryReqDto queryReqDto) {
        // 默认最近7 天
        DatesRangeQueryReqDto.defaultDatesRange(queryReqDto, -6, 0);
        PageResult<ExceptionOrderStatisticByMchntNoRspDto> pageResult
                = baseService.listPageStatisticExceptionByMchntNo(pageParam, queryReqDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }
}
