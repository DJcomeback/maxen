package com.gec.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gec.model.system.SysMenu;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.mapper.SysRoleMenuMapper;
import com.gec.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.system.util.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new MyCustomerException(201,"请先删除子菜单");
        }
        //删除
        baseMapper.deleteById(id);
    }
}
