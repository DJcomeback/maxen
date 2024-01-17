package com.gec.system.controller;


import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author maXen
 * @since 2024-01-15
 */
@RestController
@RequestMapping("/system/sys-login-log")
@Api(tags = "登录日志")
@CrossOrigin("*")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService sysLoginLogService;
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result QueryLoginLog(@PathVariable Long page, @PathVariable Long limit, SysLoginLogQueryVo sysLoginLogQueryVo) {
        return Result.ok(sysLoginLogService.selectPage(page, limit, sysLoginLogQueryVo));
    }

    //根据ID获取
    @ApiOperation(value = "根据ID获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(sysLoginLogService.getById(id));
    }
}

