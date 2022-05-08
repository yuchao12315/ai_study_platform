package com.csuft.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csuft.common.response.R;
import com.csuft.uc.entity.CourseVideo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
public interface ICourseVideoService extends IService<CourseVideo> {

    R getCourseVideo(String courseName, String teacherName);
}
