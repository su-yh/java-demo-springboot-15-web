package com.eb.rouyi.controller;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.authentication.CurrLoginUser;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.constants.UserConstants;
import com.eb.rouyi.domain.AjaxResult;
import com.eb.rouyi.entity.SysMenu;
import com.eb.rouyi.service.ISysMenuService;
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

import java.util.List;

/**
 * 菜单信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu, loginUser.getId());
        return success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId)
    {
        return success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu, loginUser.getId());
        return success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @PathVariable("roleId") Long roleId)
    {
        List<SysMenu> menus = menuService.selectMenuList(loginUser.getId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:add')")
//    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuKeyUnique(menu)) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_CREATE_FAILED_KEY_ERROR, menu.getMenuName());
        }
        if (!menuService.checkMenuNameUnique(menu)) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_CREATE_FAILED_NAME_EXISTS, menu.getMenuName());
        }
        if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !RuoyiStringUtils.ishttp(menu.getPath())) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_CREATE_FAILED_PATH_ERROR, menu.getMenuName());
        }
        menu.setCreateBy(loginUser.getNickname());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
//    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuKeyUnique(menu)) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_CREATE_FAILED_KEY_ERROR, menu.getMenuName());
        }
        if (!menuService.checkMenuNameUnique(menu)) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_EDIT_FAILED_NAME_EXISTS, menu.getMenuName());
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !RuoyiStringUtils.ishttp(menu.getPath())) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_EDIT_FAILED_PATH_ERROR, menu.getMenuName());
        }
        else if (menu.getMenuId().equals(menu.getParentId())) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_EDIT_FAILED_PARENT_ERROR, menu.getMenuName());
        }
        menu.setUpdateBy(loginUser.getNickname());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
//    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_DELETE_FAILED_CHILD_ERROR);
        }
        if (menuService.checkMenuExistRole(menuId)) {
            throw ExceptionUtil.business(ErrorCodeConstants.RUOYI_SYSTEM_MENU_DELETE_FAILED_ASSIGNED_ERROR);
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }
}