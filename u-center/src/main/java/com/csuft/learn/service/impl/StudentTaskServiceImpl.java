package com.csuft.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.learn.entity.StudentTask;
import com.csuft.learn.mapper.StudentTaskMapper;
import com.csuft.learn.service.IStudentTaskService;
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
public class StudentTaskServiceImpl extends ServiceImpl<StudentTaskMapper, StudentTask> implements IStudentTaskService {

}
