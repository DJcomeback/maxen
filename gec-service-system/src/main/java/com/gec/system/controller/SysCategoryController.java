package com.gec.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysCategory;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysCategoryService;
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
 * 前端控制器
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */

@Api(tags = "影视分类")
@RestController
@RequestMapping("/system/sys-category")
@CrossOrigin("*")
public class SysCategoryController {

    @Autowired
    private SysCategoryService sysCategoryService;

    @Autowired
    private SysOperLogService sysOperLogService;

    @ApiOperation("获取全部分类")
    @GetMapping("/findAll")
    public Result findAll() {
        return Result.ok(sysCategoryService.list());
    }

    // 测试删除
    @ApiOperation("根据id去移除一个分类")
    @DeleteMapping("/removeCategory/{id}")
    public Result removeCategory(@PathVariable Long id, HttpServletRequest request, SysOperLog sysOperLog) {
        sysOperLog.setTitle("分类管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-category/removeCategory/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysCategoryController.removeCategory()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysCategoryService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 分页和条件查询
    @ApiOperation("分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findCategoryByPageQuery(@PathVariable Long page, @PathVariable Long limit, SysCategoryQueryVo sysCategoryQueryVo) {
        return Result.ok(sysCategoryService.selectPage(new Page<>(page,limit),sysCategoryQueryVo));
    }

    // 添加分类
    @ApiOperation("添加分类")
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody SysCategory sysCategory,SysOperLog sysOperLog,HttpServletRequest request) {
        sysOperLog.setTitle("分类管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-category/addCategory/");
        sysOperLog.setBusinessType("INSERT");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysCategoryController.addCategory()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysCategoryService.save(sysCategory);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 修改
    //1.根据id 去得到当前分类
    @ApiOperation("根据id去得到当前分类")
    @GetMapping("/findCategoryById/{id}")
    public Result findCategoryById(@PathVariable Long id) {
        return Result.ok(sysCategoryService.getById(id));
    }

    // 实现修改
    @ApiOperation("修改分类")
    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody SysCategory sysCategory,HttpServletRequest request,SysOperLog sysOperLog) {
        sysOperLog.setTitle("分类管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-category/updateCategory/");
        sysOperLog.setBusinessType("UPDATE");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysCategoryController.updateCategory()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysCategoryService.updateById(sysCategory);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("/removeCategoryByIds")
    public Result removeCategoryByIds(@RequestBody List<Long> ids,HttpServletRequest request,SysOperLog sysOperLog) {
        sysOperLog.setTitle("分类管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-category/removeCategoryByIds/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysCategoryController.removeCategoryByIds()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean isSuccess = sysCategoryService.removeByIds(ids);
        return isSuccess ? Result.ok() : Result.fail();
    }

}

