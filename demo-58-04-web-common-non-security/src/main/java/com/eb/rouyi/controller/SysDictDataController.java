package com.eb.rouyi.controller;

import com.eb.mvc.authentication.CurrLoginUser;
import com.eb.mvc.authentication.LoginUser;
import com.eb.rouyi.domain.AjaxResult;
import com.eb.rouyi.entity.SysDictData;
import com.eb.rouyi.page.TableDataInfo;
import com.eb.rouyi.service.ISysDictDataService;
import com.eb.rouyi.service.ISysDictTypeService;
import com.eb.rouyi.util.RuoyiStringUtils;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController
{
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

//    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData)
    {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

//    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('system:dict:export')")
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SysDictData dictData)
//    {
//        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
//        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
//        util.exportExcel(response, list, "字典数据");
//    }

    /**
     * 查询字典数据详细
     */
//    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode)
    {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (RuoyiStringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return success(data);
    }

    /**
     * 新增字典类型
     */
//    @PreAuthorize("@ss.hasPermi('system:dict:add')")
//    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @Validated @RequestBody SysDictData dict)
    {
        dict.setCreateBy(loginUser.getNickname());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
//    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
//    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateBy(loginUser.getNickname());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
//    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
//    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }
}
