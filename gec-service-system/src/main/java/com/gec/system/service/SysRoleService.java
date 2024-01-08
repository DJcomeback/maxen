package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysRole;
import com.gec.model.vo.SysRoleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> selectPage(IPage<SysRole> page1, SysRoleQueryVo roleQueryVo);
}
