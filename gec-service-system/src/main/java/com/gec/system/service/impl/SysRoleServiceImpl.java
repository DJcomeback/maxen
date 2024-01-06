package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysRole;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.mapper.SysRoleMapper;
import com.gec.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public IPage<SysRole> selectPage(IPage<SysRole> page1, SysRoleQueryVo roleQueryVo) {
        IPage<SysRole> selectPage = this.baseMapper.selectPage(page1, roleQueryVo);
        return selectPage;
    }
}
