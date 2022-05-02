package com.csuft.learn.service.impl;

import com.csuft.learn.entity.Student;
import com.csuft.learn.mapper.StudentMapper;
import com.csuft.learn.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-04-22
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
