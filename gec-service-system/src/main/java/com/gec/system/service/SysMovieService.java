package com.gec.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysMovie;
import com.gec.model.vo.SysMovieQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
public interface SysMovieService extends IService<SysMovie> {
    IPage<SysMovie> selectPage(IPage<SysMovie> p1, SysMovieQueryVo sysMovieQueryVo);
}
