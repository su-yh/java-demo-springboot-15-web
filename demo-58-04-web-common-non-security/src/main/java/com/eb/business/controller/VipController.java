package com.eb.business.controller;

import com.eb.business.dto.UuidPlus;
import com.eb.business.dto.vip.req.VipAccountQueryReqDto;
import com.eb.business.dto.vip.req.VipAccountReqDto;
import com.eb.business.dto.vip.req.VipInfoQueryReqDto;
import com.eb.business.dto.vip.req.VipInfoUpdateReqDto;
import com.eb.business.dto.vip.rsp.VipAccountRspDto;
import com.eb.business.dto.vip.rsp.VipInfoRspDto;
import com.eb.business.service.VipInfoService;
import com.eb.business.service.VipService;
import com.eb.group.ValidationGroups;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mvc.authentication.CurrLoginUser;
import com.eb.mvc.authentication.LoginUser;
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
 * @since 2024-09-05
 */
@Tag(name = "代理商")
@RestController
@RequestMapping("/vip")
@RequiredArgsConstructor
@Validated
@Slf4j
public class VipController {
    private final VipService baseService;
    private final VipInfoService vipInfoService;

    @Operation(summary = "代理商更新上下线状态，以及tgUsername 的修改")
    @RequestMapping(value = "/info/update/byId", method = RequestMethod.POST)
    public void infoUpdateById(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated VipInfoUpdateReqDto updateReqDto) {
        vipInfoService.infoUpdateById(loginUser, updateReqDto);
    }

    @Operation(summary = "代理商信息查询(分页)")
    @RequestMapping(value = "/info/listPage", method = RequestMethod.GET)
    public PageResult<VipInfoRspDto> infoListPage(
            PageParam pageParam, VipInfoQueryReqDto reqDto) {
        return vipInfoService.infoListPage(pageParam, reqDto);
    }

    @Operation(summary = "代理商信息导出")
    @RequestMapping(value = "/info/export", method = RequestMethod.GET)
    public void infoExport(
            WebRequest webRequest,
            HttpServletResponse response,
            VipInfoQueryReqDto reqDto) {
        List<VipInfoRspDto> dtoList = vipInfoService.infoList(reqDto);

        RuoyiExcelUtil<VipInfoRspDto> util
                = new RuoyiExcelUtil<>(VipInfoRspDto.class, webRequest.getLocale());
        util.exportExcel(response, dtoList, "Sheet1");
    }

    @Operation(summary = "代理商账号新增")
    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public Long accountCreate(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) VipAccountReqDto createDto) {
        return baseService.accountCreate(loginUser, createDto);
    }

    @Operation(summary = "代理商账号修改")
    @RequestMapping(value = "/account/update/byId", method = RequestMethod.POST)
    public void accountUpdateById(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) VipAccountReqDto updateDto) {
        baseService.accountUpdateById(loginUser, updateDto);
    }

    @Operation(summary = "代理商账号查询(分页)")
    @RequestMapping(value = "/account/listPage", method = RequestMethod.GET)
    public PageResult<VipAccountRspDto> accountListPage(
            PageParam pageParam, VipAccountQueryReqDto queryDto) {
        PageResult<VipAccountRspDto> pageResult = baseService.accountListPage(pageParam, queryDto);
        UuidPlus.setUuidList(pageResult.getList());
        return pageResult;
    }

    @Operation(summary = "代理商账号导出")
    @RequestMapping(value = "/account/export", method = RequestMethod.GET)
    public void accountExport(
            WebRequest webRequest,
            HttpServletResponse response,
            VipAccountQueryReqDto queryDto) {
        List<VipAccountRspDto> dtoList = baseService.accountList(queryDto);

        RuoyiExcelUtil<VipAccountRspDto> util
                = new RuoyiExcelUtil<>(VipAccountRspDto.class, webRequest.getLocale());
        util.exportExcel(response, dtoList, "Sheet1");
    }
}
