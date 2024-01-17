package com.gec.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysOperLog;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysUserQueryVo;

import com.gec.system.service.SysOperLogService;
import com.gec.system.service.SysUserService;
import com.gec.system.common.Result;
import com.gec.system.util.IpUtil;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.MD5Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户管理控制器")
@RestController
@CrossOrigin("*")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysOperLogService sysOperLogService;

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
    public Result addUser(@RequestBody SysUser sysUser, SysOperLog sysOperLog, HttpServletRequest request) {
        //加密
        String passwordMD5 = MD5Helper.encrypt(sysUser.getPassword());
        //设置密码
        sysUser.setPassword(passwordMD5);
        //日志记录
        sysOperLog.setTitle("角色管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysUser/addUser/");
        sysOperLog.setBusinessType("INSERT");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysUserController.addUser()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysUserService.save(sysUser);
        return isSuccess ? Result.ok() : Result.fail();
    }

    //修改用户
    @ApiOperation("修改用户")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody SysUser sysUser,SysOperLog sysOperLog,HttpServletRequest request) {
        //日志记录
        sysOperLog.setTitle("角色管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysUser/updateUser/");
        sysOperLog.setBusinessType("UPDATE");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysUserController.updateUser()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysUserService.updateById(sysUser);
        return isSuccess ? Result.ok() : Result.fail();
    }

    //删除用户
    @ApiOperation("根据ID逻辑删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id,SysOperLog sysOperLog,HttpServletRequest request) {
        //日志记录
        sysOperLog.setTitle("角色管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysUser/remove/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysUserController.remove()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        boolean isSuccess = sysUserService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids,SysOperLog sysOperLog,HttpServletRequest request) {
        //日志记录
        sysOperLog.setTitle("角色管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/admin/system/sysUser/batchRemove/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysUserController.batchRemove()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        boolean isSuccess = sysUserService.removeByIds(ids);
        return isSuccess ? Result.ok() : Result.fail();
    }

}
