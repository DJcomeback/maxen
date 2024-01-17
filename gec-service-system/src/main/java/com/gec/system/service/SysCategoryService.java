package com.gec.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
public interface SysCategoryService extends IService<SysCategory> {
    IPage<SysCategory> selectPage(IPage<SysCategory> page, SysCategoryQueryVo sysCategoryQueryVo);
}
