package com.csuft.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.learn.entity.Teacher;
import com.csuft.learn.mapper.TeacherMapper;
import com.csuft.learn.service.ITeacherService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-04-22
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    public Teacher teacherLogon(Map<String, String> map) {
        return null;
    }
}
