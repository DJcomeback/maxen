package com.gec.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.model.vo.LoginVo;
import com.gec.system.common.Result;
import com.gec.system.common.ResultCodeEnum;
import com.gec.system.custom.CustomUser;
import com.gec.system.service.SysLoginLogService;
import com.gec.system.util.IpUtil;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.JwtUtil;
import com.gec.system.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;

    private SysLoginLogService sysLoginLogService;


    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate,SysLoginLogService sysLoginLogService) {
        setAuthenticationManager(authenticationManager);
        setPostOnly(false);
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login", "POST"));
        //redis
        this.redisTemplate = redisTemplate;

        //登录日志
        this.sysLoginLogService = sysLoginLogService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            System.out.println("请求账号：" + loginVo.getUsername());
            System.out.println("请求密码：" + loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 登录成功
     *
     * @param request
     * @param response
     * @param chain
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication res) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) res.getPrincipal();
        String token = JwtHelper.createToken(customUser.getSysUser().getId()+"", customUser.getSysUser().getUsername());

        //保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        //记录登录成功的日志
        sysLoginLogService.recordLoginLog(customUser.getUsername(), 0, IpUtil.getIpAddress(request), "登录成功");

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 204, failed.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }
}
