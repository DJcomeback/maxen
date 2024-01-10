package com.gec.system.controller;


import com.gec.model.system.SysMenu;
import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 加载树形菜单
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes() {
        return Result.ok(sysMenuService.findNodes());
    }

    //删除菜单
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeMenu/{id}")
    public Result removeMenu(@PathVariable Long id) {
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

    //添加菜单
    @ApiOperation(value = "添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu) {
        boolean isSuccess = sysMenuService.save(sysMenu);
        return isSuccess? Result.ok() : Result.fail();
    }

    //修改菜单
    @ApiOperation(value = "修改菜单")
    @PostMapping("/updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu) {
        boolean isSuccess = sysMenuService.updateById(sysMenu);
        return isSuccess? Result.ok() : Result.fail();
    }
}

