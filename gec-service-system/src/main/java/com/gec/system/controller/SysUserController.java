package com.gec.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.entity.SysUser;
import com.gec.system.service.SysUserService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //根据ID查找用户
    @ApiOperation("根据ID查找用户")
    @GetMapping("/findUserById/{id}")
    public Result findUserById(@PathVariable Long id) {
        return Result.ok(sysUserService.getById(id));
    }

    //添加用户
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody SysUser sysUser) {
        boolean isSuccess = sysUserService.save(sysUser);
        return isSuccess ? Result.ok() : Result.fail();
    }

    //修改用户
    @ApiOperation("修改用户")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody SysUser sysUser) {
        boolean isSuccess = sysUserService.updateById(sysUser);
        return isSuccess ? Result.ok() : Result.fail();
    }

    //删除用户
    @ApiOperation("根据ID逻辑删除用户")
    @PostMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        boolean isSuccess = sysUserService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        boolean isSuccess = sysUserService.removeByIds(ids);
        return isSuccess ? Result.ok() : Result.fail();
    }
}
