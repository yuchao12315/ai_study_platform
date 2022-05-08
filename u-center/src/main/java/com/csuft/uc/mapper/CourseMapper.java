package com.csuft.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuft.uc.entity.Course;
import com.csuft.uc.vo.StudentCourseVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> listCourse();
    List<StudentCourseVo> listStudentCourse(String userName);
}
