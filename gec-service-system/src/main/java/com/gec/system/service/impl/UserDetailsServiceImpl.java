package com.gec.system.service.impl;

import com.gec.model.system.SysUser;
import com.gec.system.custom.CustomUser;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserInfoUserName(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("Invalid Username!");
        }

        if (sysUser.getStatus() == 0) {
            throw new RuntimeException("Frozen Account");
        }

        List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userPermsList.forEach(perm -> authorities.add(new SimpleGrantedAuthority(perm.trim())));
        return new CustomUser(sysUser, authorities);
    }
}
