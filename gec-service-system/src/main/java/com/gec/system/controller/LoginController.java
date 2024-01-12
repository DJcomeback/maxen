package com.gec.system.controller;

import com.gec.model.system.SysUser;
import com.gec.model.vo.LoginVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysUserService;
import com.gec.system.common.Result;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.MD5Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "登录管理控制器")
@RequestMapping(value = "/admin/system/index")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录接口
     *
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginVo loginVo){
        //1.根据username 查询数据
        SysUser sysUser = sysUserService.getUserInfoUserName(loginVo.getUsername());
        //2.如果查询为空 给出提示
        if (sysUser == null) {
            throw new MyCustomerException(20001, "Invalid User");
        }
        //3.比较密码  （使用用户输入的密码和数据库密码比较）
        String password = loginVo.getPassword();
        String encryptP = MD5Helper.encrypt(password);

        if (!sysUser.getPassword().equals(encryptP)) {
            throw new MyCustomerException(20001,"Invalid Password");
        }
        //4.判断用户是否可用
        if (sysUser.getStatus() == 0) {
            throw new MyCustomerException(20001, "Frozen Account");
        }

        //5.根据userid和username 生成token字符串 再通过map返回
        String token = JwtHelper.createToken(sysUser.getId()+"", sysUser.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }
//    @PostMapping(value = "/login")
//    public Result login() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("token", "admin-token");
//        return Result.ok(map);
//    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation("info方法")
    @GetMapping("/info")
    public Result info(HttpServletRequest httpServletRequest) {
        //获取token
        String token = httpServletRequest.getHeader("token");
        System.out.println(token);
        //获取用户名
        String username = JwtHelper.getUsername(token);

        //获取用户信息
        return Result.ok(sysUserService.getUserInfo(username));
    }
//    @GetMapping(value = "/info")
//    public Result info() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("roles", "[admin]");
//        map.put("introduction", "I am a super admin");
//        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//        map.put("name", "admin maXen hello!");
//        return Result.ok(map);
//    }


    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }
}
