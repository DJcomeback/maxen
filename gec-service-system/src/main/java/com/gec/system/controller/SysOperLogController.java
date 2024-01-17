package com.gec.system.controller;


import com.gec.model.vo.SysOperLogQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author maXen
 * @since 2024-01-16
 */
@Api("操作日志记录")
@RestController
@RequestMapping("/system/sys-operation-log")
@CrossOrigin("*")
public class SysOperLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    @ApiOperation("获取操作日志记录列表")
    @GetMapping("/{page}/{limit}")
    public Result getList(@PathVariable Long page, @PathVariable Long limit, SysOperLogQueryVo sysOperLogQueryVo) {
        return Result.ok(sysOperLogService.selectPage(page, limit, sysOperLogQueryVo));
    }

}

