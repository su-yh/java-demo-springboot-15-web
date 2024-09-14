package com.eb.business.controller;

import com.eb.business.service.SchedulingTransferRecordService;
import com.eb.business.service.VipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 下拉相关的接口
 *
 * @author suyh
 * @since 2024-09-05
 */
@Tag(name = "下拉相关的接口")
@RestController
@RequestMapping("/dropDown")
@RequiredArgsConstructor
@Validated
@Slf4j
public class DropDownController {
    private final VipService vipService;
    private final SchedulingTransferRecordService schedulingTransferRecordService;

    @Operation(summary = "代理商code 列表查询(所有)")
    @RequestMapping(value = "/code/list", method = RequestMethod.GET)
    public List<String> codeList() {
        return vipService.codeList();
    }

    @Operation(summary = "商户号列表查询(所有)")
    @RequestMapping(value = "/mchntNo/list", method = RequestMethod.GET)
    public List<String> mchntNoList() {
        return schedulingTransferRecordService.mchntNoList();
    }
}
