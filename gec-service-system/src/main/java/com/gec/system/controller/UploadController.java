package com.gec.system.controller;

import com.gec.system.common.Result;
import com.gec.system.service.SysMovieService;
import com.gec.system.util.OssTemplate;
import com.gec.system.util.VodTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.View;

@RestController
@Api(tags = "视频上传控制器")
@RequestMapping("/admin/system/upload")
@CrossOrigin("*")  // 解决跨域问题
public class UploadController {

    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private SysMovieService sysMovieService;

    @Autowired
    private VodTemplate vodTemplate;

    // 上传图片
    @ApiOperation("上传图片")
    @RequestMapping(value = "/uploadImage")
    public Result uploadImage(MultipartFile movieImage) throws Exception {
        String URL = ossTemplate.upload(movieImage.getOriginalFilename(), movieImage.getInputStream());
        System.out.println("URL: " + URL);
        return Result.ok(URL);
    }

    @ApiOperation("上传视频")
    @RequestMapping(value = "/uploadVideo")
    public Result uploadVideo(MultipartFile movieVideo) throws Exception {
        String video = vodTemplate.uploadVideo(movieVideo.getOriginalFilename(), movieVideo.getInputStream());
        System.out.println(video);
        return Result.ok(video);
    }
}
