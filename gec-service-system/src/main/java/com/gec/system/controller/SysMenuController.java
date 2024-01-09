package com.gec.system.controller;


import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    // 加载树形菜单
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes() {
        return Result.ok(sysMenuService.findNodes());
    }
}

