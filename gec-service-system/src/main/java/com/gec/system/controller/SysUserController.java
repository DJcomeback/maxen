package com.gec.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.service.SysUserService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理控制器")
@RestController
@CrossOrigin("*")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    // 分页和条件查询
    @ApiOperation("分页和条件查询")
    @GetMapping("/{page}/{limit}")
    public Result selectUserPageByVo(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo) {
        return Result.ok(sysUserService.selectPage(new Page<>(page,limit),sysUserQueryVo));
    }
}
