package com.csuft.uc.controller;


import com.csuft.common.response.R;
import com.csuft.uc.service.ICourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
@RestController
public class CourseVideoController {

    @Autowired
    public ICourseVideoService courseVideoService;

    @GetMapping("/uc/course-video/getCourseVideo")
    public R getCourseVideo(@RequestParam("course_id") String courseName,
                            @RequestParam("teacher_id") String teacherName) {
        return courseVideoService.getCourseVideo(courseName,teacherName);
    }

}

