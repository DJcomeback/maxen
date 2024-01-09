package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysRole;
import com.gec.model.system.SysUserRole;
import com.gec.model.vo.AssginRoleVo;
import com.gec.system.mapper.SysRoleMapper;
import com.gec.system.mapper.SysUserRoleMapper;
import com.gec.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        //获取所有角色
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);

        //根据用户ID查询
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        //获取用户已分配的角色
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);

        //获取用户已分配的角色ID
        List<Long> roleIds = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            roleIds.add(sysUserRole.getUserId());
        }

        //创建返回的Map
        Map<String, Object> res = new HashMap<>();
        res.put("allRoles", sysRoles);
        res.put("userRoleIds", roleIds);

        return res;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户ID删除原有已分配角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", assginRoleVo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);

        //获取所有角色列表
        List<Long> roleIdList = assginRoleVo.getRoleIdList();

        for (Long roleId : roleIdList) {
            if (roleId != null) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(assginRoleVo.getUserId());

                //保存
                sysUserRoleMapper.insert(sysUserRole);
            }
        }

    }
}
