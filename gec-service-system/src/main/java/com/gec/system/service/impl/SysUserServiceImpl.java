package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.entity.SysUser;
import com.gec.system.mapper.SysUserMapper;
import com.gec.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author maXen
 * @since 2024-01-08
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(iPage,sysUserQueryVo);
    }
}
