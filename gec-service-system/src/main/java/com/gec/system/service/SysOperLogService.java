package com.gec.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysLoginLog;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.model.vo.SysOperLogQueryVo;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author maXen
 * @since 2024-01-16
 */
public interface SysOperLogService extends IService<SysOperLog> {
    void recordLoginLog(SysOperLog sysOperLog);

    //条件分页查询登录日志
    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);
}
