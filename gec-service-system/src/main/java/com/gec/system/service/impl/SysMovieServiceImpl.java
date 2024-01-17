package com.gec.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.MPJMappingWrapper;
import com.gec.model.system.SysCategory;
import com.gec.model.system.SysMovie;
import com.gec.model.vo.SysMovieQueryVo;
import com.gec.system.mapper.SysMovieMapper;
import com.gec.system.service.SysMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
@Service
public class SysMovieServiceImpl extends ServiceImpl<SysMovieMapper, SysMovie> implements SysMovieService {

    @Resource
    private SysMovieMapper sysMovieMapper;

    @Override
    public IPage<SysMovie> selectPage(IPage<SysMovie> p1, SysMovieQueryVo sysMovieQueryVo) {
//        MPJLambdaWrapper<SysMovie> mpjLambdaWrapper = new MPJLambdaWrapper<SysMovie>();
//        mpjLambdaWrapper.selectAll(SysMovie.class).selectAs(SysCategory::getName,SysMovie::getM_type).innerJoin(SysCategory.class,SysCategory::getId,SysMovie::getCid);
//        return sysMovieMapper.selectPage(p1,mpjLambdaWrapper);
        return this.baseMapper.selectPage(p1,sysMovieQueryVo);
    }
}
