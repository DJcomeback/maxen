package com.gec.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author maXen
 * @since 2024-01-09
 */
public interface SysMenuService extends IService<SysMenu> {
    //加载菜单列表
    List<SysMenu> findNodes();

    //改造删除
    void removeMenuById (Long id);
}
