package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysUserQueryVo;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author maXen
 * @since 2024-01-08
 */


public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo);

    SysUser getUserInfoUserName(String username);

    Map<String, Object> getUserInfo(String username);
}
