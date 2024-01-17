package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author maXen
 * @since 2024-01-15
 */
public interface SysLoginLogService extends IService<SysLoginLog> {
    void recordLoginLog(String username, Integer status, String ipaddr, String message);

    //条件分页查询登录日志
    IPage<SysLoginLog> selectPage(Long page, Long limit, SysLoginLogQueryVo sysLoginLogQueryVo);

}
