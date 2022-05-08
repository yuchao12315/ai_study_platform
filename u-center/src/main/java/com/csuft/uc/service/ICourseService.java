package com.csuft.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csuft.common.response.R;
import com.csuft.uc.entity.Course;
import com.csuft.uc.entity.CourseVideo;
import com.csuft.uc.vo.StudentCourseVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
public interface ICourseService extends IService<Course> {

    R creatCourse(String path,Course course);

    R searchCourse(Course course);

    R deleteCourse(Integer id);


    R updateCourse(String courseName,Course course);

    R getCourseList();

    R getStudentCourseList(String userName);

    R uploadingVideo(String userName, CourseVideo courseVideo);

    R addStudentCourse(StudentCourseVo studentCourseVo);
}
