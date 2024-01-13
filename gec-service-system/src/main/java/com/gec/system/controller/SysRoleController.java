package com.gec.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysRole;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.service.SysRoleService;
import com.gec.system.service.SysUserRoleService;
import com.gec.system.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理控制器")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin("*")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

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
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据ID逻辑删除")
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        boolean isSuccess = sysRoleService.removeById(id);
        return isSuccess?Result.ok():Result.fail();
    }

    // 分页 + 查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("角色分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findRoleByPageQuery(@PathVariable Long page, @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo)
    {
        return Result.ok(sysRoleService.selectPage(new Page<>(page, limit), sysRoleQueryVo));
    }

    // 添加
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.save(sysRole);
        return isSuccess?Result.ok(): Result.fail();
    }

    // 根据id 去获取一个role
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查询")
    @GetMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        return Result.ok(sysRoleService.getById(id));
    }

    //修改
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.updateById(sysRole);
        return isSuccess ? Result.ok() : Result.fail();
    }

    // 批量删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        boolean isSuccess = sysRoleService.removeByIds(ids);
        return isSuccess ? Result.ok() : Result.fail();
    }


    // 根据用户ID获取角色数据
    @ApiOperation("根据用户ID获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        return Result.ok(sysUserRoleService.getRolesByUserId(userId));
    }

    // 根据用户分配角色
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysUserRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }

}
