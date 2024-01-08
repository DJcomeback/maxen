package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author maXen
 * @since 2024-01-08
 */

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    public IPage<SysUser> selectPage(IPage<SysUser> iPage, @Param("vo") SysUserQueryVo sysUserQueryVo);
}
