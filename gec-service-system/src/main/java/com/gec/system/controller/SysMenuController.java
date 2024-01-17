package com.gec.system.controller;


import com.gec.model.system.SysMenu;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.AssginMenuVo;
import com.gec.system.service.SysMenuService;
import com.gec.system.common.Result;
import com.gec.system.service.SysOperLogService;
import com.gec.system.util.IpUtil;
import com.gec.system.util.JwtHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author maXen
 * @since 2024-01-09
 */
@Api(tags = "菜单管理控制器")
@RestController
@RequestMapping("/admin/system/sysMenu")
@CrossOrigin("*")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysOperLogService sysOperLogService;

    // 加载树形菜单
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes() {
        return Result.ok(sysMenuService.findNodes());
    }

    //删除菜单
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeMenu/{id}")
    public Result removeMenu(@PathVariable Long id, SysOperLog sysOperLog, HttpServletRequest request) {
        sysOperLog.setTitle("菜单管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysMenu/removeMenu/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMenuController.removeMenu()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

    //添加菜单
    @ApiOperation(value = "添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu, SysOperLog sysOperLog, HttpServletRequest request) {
        sysOperLog.setTitle("菜单管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysMenu/addMenu/");
        sysOperLog.setBusinessType("INSERT");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMenuController.addMenu()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysMenuService.save(sysMenu);
        return isSuccess ? Result.ok() : Result.fail();
    }

    //修改菜单
    @ApiOperation(value = "修改菜单")
    @PostMapping("/updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu,SysOperLog sysOperLog,HttpServletRequest request) {
        sysOperLog.setTitle("菜单管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysMenu/updateMenu/");
        sysOperLog.setBusinessType("UPDATE");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMenuController.updateMenu()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysMenuService.updateById(sysMenu);
        return isSuccess? Result.ok() : Result.fail();
    }

    // 根据角色分配菜单
    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> list =   this.sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo,SysOperLog sysOperLog,HttpServletRequest request) {
        sysOperLog.setTitle("菜单管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysMenu/doAssign/");
        sysOperLog.setBusinessType("ASSIGN");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMenuController.doAssign()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }
}

