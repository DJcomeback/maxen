package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysMovie;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysMovieQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysMovieService;
import com.gec.system.service.SysOperLogService;
import com.gec.system.util.IpUtil;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.VodTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author maXen
 * @since 2024-01-13
 */
@Api(tags = "影视管理控制器")
@RestController
@RequestMapping("/system/sys-movie")
@CrossOrigin("*")
public class SysMovieController {
    @Autowired
    private SysMovieService sysMovieService;

    @Autowired
    private VodTemplate vodTemplate;

    @Autowired
    private SysOperLogService sysOperLogService;

    @ApiOperation("获取全部影视列表")
    @GetMapping("/findAll")
    public Result findAll() {
        List<SysMovie> list = this.sysMovieService.list();
        return Result.ok(list);
    }

    @ApiOperation("根据id去移除一个影视")
    // 测试删除
    @DeleteMapping("/removeMovie/{id}")
    public Result removeMovie(@PathVariable Long id, SysOperLog sysOperLog, HttpServletRequest request) {
        sysOperLog.setTitle("影视管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-movie/removeMovie/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMovieController.removeMovie()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean b = this.sysMovieService.removeById(id);
        return b ? Result.ok() : Result.fail();
    }

    // 分页和条件查询
    @ApiOperation("分页和条件查询")
    @GetMapping("/{page}/{limit}")
    public Result findMovieByPageQuery(@PathVariable Long page,
                                       @PathVariable Long limit,
                                       SysMovieQueryVo sysMovieQueryVo) {
        //1.创建分页对象
        IPage<SysMovie> p1 = new Page<SysMovie>(page, limit);
        //2.调用方法
        p1 = this.sysMovieService.selectPage(p1, sysMovieQueryVo);
        //3.返回
        return Result.ok(p1);
    }

    // 添加影视
    @ApiOperation("添加影视")
    @PostMapping("/addMovie")
    public Result addMovie(@RequestBody SysMovie sysMovie,SysOperLog sysOperLog,HttpServletRequest request) {
        sysOperLog.setTitle("影视管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-movie/addMovie/");
        sysOperLog.setBusinessType("INSERT");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMovieController.addMovie()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean res = this.sysMovieService.save(sysMovie);
        return res ? Result.ok() : Result.fail();
    }

    // 修改
    //1.根据id 去得到当前影视
    @ApiOperation("根据id 去得到当前影视")
    @GetMapping("/findMovieById/{id}")
    public Result findMovieById(@PathVariable Long id) {
        SysMovie sysMovie = this.sysMovieService.getById(id);
        return Result.ok(sysMovie);
    }

    // 实现修改
    @ApiOperation("实现修改")
    @PostMapping("/updateMovie")
    public Result updateMovie(@RequestBody SysMovie sysMovie,SysOperLog sysOperLog,HttpServletRequest request) {
        sysOperLog.setTitle("影视管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-movie/updateMovie/");
        sysOperLog.setBusinessType("UPDATE");
        sysOperLog.setRequestMethod("POST");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMovieController.updateMovie()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean b = this.sysMovieService.updateById(sysMovie);
        return b ? Result.ok() : Result.fail();
    }

    // 批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("/removeMovieByIds")
    public Result removeMovieByIds(@RequestBody List<Long> ids,SysOperLog sysOperLog,HttpServletRequest request) {
        sysOperLog.setTitle("影视管理");
        sysOperLog.setOperIp(IpUtil.getIpAddress(request));
        sysOperLog.setOperUrl("/system/sys-movie/removeMovieByIds/");
        sysOperLog.setBusinessType("DELETE");
        sysOperLog.setRequestMethod("DELETE");
        sysOperLog.setOperName(JwtHelper.getUsername(request.getHeader("token")));
        sysOperLog.setMethod("com.gec.system.controller.SysMovieController.removeMovieByIds()");
        sysOperLog.setStatus(0);
        sysOperLog.setOperatorType("MANAGE");
        sysOperLogService.recordLoginLog(sysOperLog);
        boolean b = this.sysMovieService.removeByIds(ids);
        return b ? Result.ok() : Result.fail();
    }


    //根据id获取播放凭证
    @ApiOperation("根据id获取播放凭证")
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable Long id) throws Exception {
        System.out.println(id);
        // 1.根据id获取movie
        SysMovie movie = sysMovieService.getById(id);
        //2.根据movie获取播放id
        String playId = movie.getPlayId();
        //3.获取 封面
        String image = movie.getImage();
        //4.根据播放id获取auth
        String playAuth = vodTemplate.getVideoPlayAuth(playId).getPlayAuth();
        //5.返回结果(用map封装)
        Map<String,Object> map = new HashMap<>();

        map.put("playAuth",playAuth);
        map.put("image",image);
        map.put("playId",playId);
        return Result.ok(map);
    }
}

