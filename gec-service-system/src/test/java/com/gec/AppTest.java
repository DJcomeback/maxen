package com.gec;

import com.gec.model.system.SysRole;
import com.gec.system.SystemApplication;
import com.gec.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = SystemApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void contextLoads() {
        // 测试环境加载
        List<SysRole> list = sysRoleService.list();
        for (SysRole sysRole : list) {
            System.out.println(sysRole);
        }
    }
}
