package com.gec.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gec.model.system.SysMenu;
import com.gec.model.system.SysRoleMenu;
import com.gec.model.vo.AssginMenuVo;
import com.gec.model.vo.RouterVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.mapper.SysRoleMenuMapper;
import com.gec.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.system.util.MenuHelper;
import com.gec.system.util.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author maXen
 * @since 2024-01-09
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        return MenuHelper.bulidTree(baseMapper.selectList(null));
    }

    @Override
    public void removeMenuById(Long id) {
        //查询当前删除菜单下面是否子菜单
        //根据id = parentid
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Long count = Long.valueOf(baseMapper.selectCount(queryWrapper));
        if (count > 0) {
            throw new MyCustomerException(201, "请先删除子菜单");
        }
        //删除
        baseMapper.deleteById(id);
    }

    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        List<SysMenu> sysMenus = null;

        //判断是否为管理员
        sysMenus = userId == 1 ? baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value")) : baseMapper.findListByUserId(userId);

        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.bulidTree(sysMenus);

        //构建路由

        return RouterHelper.buildRouters(sysMenuTreeList);
    }

    @Override
    public List<String> findUserPermsList(Long userId) {
        List<SysMenu> sysMenus = null;

        //判断是否为管理员
        sysMenus = userId == 1 ? baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1)) : baseMapper.findListByUserId(userId);

        //创建返回的集合
        List<String> permissions = new ArrayList<>();

        sysMenus.forEach(menu -> {
            if (menu.getType() == 2) {
                permissions.add(menu.getPerms());
            }
        });

        return permissions;
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<SysMenu> menuList = baseMapper.selectList(queryWrapper);

        //根据角色id获取角色权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);

        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(wrapper);

        //获取该角色已分配的所有权限id
        List<Long> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }
        //遍历所有权限列表
        for (SysMenu sysMenu : menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                //设置该权限已被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenus = MenuHelper.bulidTree(menuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                //添加新权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }


}
