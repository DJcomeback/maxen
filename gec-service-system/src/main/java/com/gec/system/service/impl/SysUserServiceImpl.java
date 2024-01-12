package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.gec.model.vo.RouterVo;
import com.gec.model.vo.SysUserQueryVo;

import com.gec.system.mapper.SysUserMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(iPage,sysUserQueryVo);
    }

    @Override
    public SysUser getUserInfoUserName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String,Object> map = new HashMap<>();
        SysUser sysUser = getUserInfoUserName(username);

        //根据用户id获取菜单权限
        List<RouterVo> userMenuList = sysMenuService.findUserMenuList(sysUser.getId());

        //根据用户id获取用户按钮权限
        List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());

        //当前权限控制使用不到，我们暂时忽略
        map.put("name", sysUser.getName());
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("roles",  "[admin]");


        map.put("buttons", userPermsList);
        map.put("routers", userMenuList);

        return map;
    }
}
