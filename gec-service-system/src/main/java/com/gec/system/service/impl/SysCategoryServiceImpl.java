package com.gec.system.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.system.mapper.SysCategoryMapper;
import com.gec.system.service.SysCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
@Service
@Transactional
public class SysCategoryServiceImpl extends ServiceImpl<SysCategoryMapper, SysCategory> implements SysCategoryService {

    @Override
    public IPage<SysCategory> selectPage(IPage<SysCategory> page, SysCategoryQueryVo sysCategoryQueryVo) {
        return baseMapper.selectPage(page,sysCategoryQueryVo);
    }
}
