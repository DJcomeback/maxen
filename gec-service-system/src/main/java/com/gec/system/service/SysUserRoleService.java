package com.gec.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysUserRole;
import com.gec.model.vo.AssginRoleVo;

import java.util.Map;

public interface SysUserRoleService extends IService<SysUserRole>{
    Map<String,Object> getRolesByUserId(Long userId);

    void doAssign(AssginRoleVo assginRoleVo);
}

