package com.gec.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
public interface SysCategoryMapper extends BaseMapper<SysCategory> {
    IPage<SysCategory> selectPage(IPage<SysCategory> page, @Param("vo") SysCategoryQueryVo sysCategoryQueryVo);
}
