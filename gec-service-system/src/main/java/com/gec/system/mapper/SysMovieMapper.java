package com.gec.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysMovie;
import com.gec.model.vo.SysMovieQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
@Mapper
@Repository
public interface SysMovieMapper extends BaseMapper<SysMovie> {
//    @Select("select m.*,ca.name m_type from sys_movie inner join  sys_category ca on m.cid = ca.id where 1=1 and m.`name` like CONCAT('%',#{vo.name},'%') and m.cid = #{vo.cid} and m.is_deleted = 0 order by m.id desc")
    public IPage<SysMovie> selectPage(IPage<SysMovie> p1, @Param("vo") SysMovieQueryVo sysMovieQueryVo);
}
