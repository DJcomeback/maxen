package com.gec.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysRole;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.service.SysRoleService;
import com.gec.system.util.Result;
import com.gec.system.util.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理控制器")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

//    // 查询全部角色
//    @ApiOperation("查询全部接口")
//    @GetMapping("/findAll")
//    public List<SysRole> findAll() {
//        return sysRoleService.list();
//    }
//
//    // 根据id 去逻辑删除
//    @ApiOperation("逻辑删除接口")
//    @DeleteMapping("/remove/{id}")
//    public boolean remove(@PathVariable Long id) {
//        return sysRoleService.removeById(id);
//    }
//
// 查询全部角色
    @ApiOperation("查询全部接口")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll() {
//        try {
//            int i =  10/0;
//        } catch (Exception e) {
//            throw new MyCustomerException(2001,"自定义异常处理");
//        }
        return Result.ok(sysRoleService.list());
    }

        // 根据id 去逻辑删除
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        boolean isSuccess = sysRoleService.removeById(id);
        return isSuccess?Result.ok():Result.fail();
    }

    // 分页 + 查询
    @ApiOperation("角色分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findRoleByPageQuery(@PathVariable Long page, @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo)
    {
        return Result.ok(sysRoleService.selectPage(new Page<>(page, limit), sysRoleQueryVo));
    }

    // 添加
    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.save(sysRole);
        return isSuccess?Result.ok(): Result.fail();
    }

    // 根据id 去获取一个role
    @ApiOperation("根据id查询")
    @GetMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        return Result.ok(sysRoleService.getById(id));
    }

    //修改
    @ApiOperation("修改角色")
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.updateById(sysRole);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        boolean isSuccess = sysRoleService.removeByIds(ids);
        return isSuccess ? Result.ok() : Result.fail();
    }
}
