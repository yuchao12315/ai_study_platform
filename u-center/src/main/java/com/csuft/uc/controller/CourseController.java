package com.csuft.uc.controller;


import com.csuft.common.response.R;
import com.csuft.uc.entity.Course;
import com.csuft.uc.entity.CourseVideo;
import com.csuft.uc.service.ICourseService;
import com.csuft.uc.vo.StudentCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
@RestController
public class CourseController {

    @Autowired
    private ICourseService courseService;
    

    @PostMapping("/uc/course/createCourse")
    public R createCourse(@RequestParam String path,@RequestBody Course course) {
        return  courseService.creatCourse(path,course);
    }

    @GetMapping("/uc/course/searchCourse")
    public R searchCourse(@RequestBody Course course) {
        return courseService.searchCourse(course);
    }

    @PutMapping("/uc/course/deleteCourse/{id}")
    public R deleteCourse(@PathVariable("id") Integer id) {
        return courseService.deleteCourse(id);
    }


    @PutMapping("/uc/course/editCourse/{name}")
    public R editCourse(@PathVariable("name") String name,@RequestBody  Course course) {
        return  courseService.updateCourse(name,course);
    }

//    @RequestMapping("/uc/course/courseListByPage")
//    public R courseListByPage(int page, int pageSize) {
//        return courseService.searchCourseByPage(page,pageSize);
//    }

    @GetMapping("/uc/course/getCourseList")
    public R getCourseList() {
        return courseService.getCourseList();
    }
    @GetMapping("/uc/course/getStudentCourseList")
    public R getStudentCourseList(@RequestParam("userName") String userName) {
        return courseService.getStudentCourseList(userName);
    }
    @PostMapping("/uploadingVideo")
    public R uploadingVideo(@RequestParam("userName")String userName ,@RequestBody CourseVideo courseVideo) {
        //视频资源存储信息
        return courseService.uploadingVideo(userName,courseVideo);
    }

    @PutMapping("/uc/course/addStudentCourse")
    public R addStudentCourse(@RequestBody StudentCourseVo studentCourseVo) {
        return courseService.addStudentCourse(studentCourseVo);
    }
}

