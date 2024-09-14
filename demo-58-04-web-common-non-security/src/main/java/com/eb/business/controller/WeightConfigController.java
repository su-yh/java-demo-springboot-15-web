package com.eb.business.controller;

import com.eb.business.dto.base.IdBody;
import com.eb.business.service.WeightConfigService;
import com.eb.group.ValidationGroups;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.WeightConfigEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Tag(name = "权重分配置")
@RestController
@RequestMapping("/weight/config")
@RequiredArgsConstructor
@Validated
@Slf4j
public class WeightConfigController {
    private final WeightConfigService baseService;

    @Operation(summary = "查询(分页)")
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public PageResult<WeightConfigEntity> listPage(
            PageParam pageParam, WeightConfigEntity queryEntity) {
        return baseService.listPage(pageParam, queryEntity);
    }

    @Operation(summary = "查询 by id")
    @RequestMapping(value = "/query/byId", method = RequestMethod.GET)
    public WeightConfigEntity queryById(
            @RequestParam("id") Long id) {
        return baseService.queryById(id);
    }

    @Operation(summary = "创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) WeightConfigEntity entity) {
        baseService.create(entity);
    }

    @Operation(summary = "更新")
    @RequestMapping(value = "/update/byId", method = RequestMethod.POST)
    public void update(
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) WeightConfigEntity entity) {
        baseService.updateById(entity);
    }

    @Operation(summary = "删除")
    @RequestMapping(value = "/delete/byId", method = RequestMethod.POST)
    public void deleteById(
            @RequestBody @Validated IdBody idBody) {
        baseService.deleteById(idBody.getId());
    }
}
