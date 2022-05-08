package com.csuft.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuft.uc.entity.CourseVideo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
public interface CourseVideoMapper extends BaseMapper<CourseVideo> {

    CourseVideo getCourseVideoPath(String courseName, String teacherName);
}
