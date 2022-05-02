package com.csuft.learn.service;

import com.csuft.learn.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yc
 * @since 2022-04-22
 */
public interface ITeacherService extends IService<Teacher> {

    Teacher teacherLogon(Map<String, String> map);
}
