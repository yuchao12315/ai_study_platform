package com.csuft.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.common.response.R;
import com.csuft.common.utils.RedisUtil;
import com.csuft.common.utils.TextUtils;
import com.csuft.uc.entity.Course;
import com.csuft.uc.entity.CourseVideo;
import com.csuft.uc.entity.StudentCourse;
import com.csuft.uc.mapper.CourseMapper;
import com.csuft.uc.service.ICourseService;
import com.csuft.uc.service.ICourseVideoService;
import com.csuft.uc.service.IStudentCourseService;
import com.csuft.uc.vo.StudentCourseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
@Slf4j
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ICourseService courseService;
    @Autowired
    private ICourseVideoService courseVideoService;
    @Autowired
    private IStudentCourseService studentCourseService;
    @Autowired
    private StudentCourseServiceImpl studentCourseServiceImpl;
    @Autowired
    private CourseVideoServiceImpl videoServiceImpl;

    @Override
    public R creatCourse(String path,Course course) {
        String name = course.getName();//课程名称
        String lecturer = course.getLecturer();//主讲师
        String intro = course.getIntro();//简介
        Integer collegeId = course.getCollegeId();//学院名称
        String num = course.getNum();//编号
        if (TextUtils.isEmpty(name)) {
            return R.FAILED("课程名称不可为空");
        }
        //根据学院名生成编号
        String courseNum = generateCourseNum(collegeId);
        // 判断课程名称否有被注册
        QueryWrapper<Course> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("name", name);
        infoQueryWrapper.select("id");
        Course courseInfo = courseService.getBaseMapper().selectOne(infoQueryWrapper);
        if (courseInfo != null) {
            return R.FAILED("课程名称已存在");
        }
        Course targetCourse = new Course();
        targetCourse.setName(name);
        targetCourse.setCollegeId(collegeId);
        targetCourse.setNum(courseNum);
        targetCourse.setLecturer(lecturer);
        targetCourse.setIntro(intro);
        save(targetCourse);
        //判断是否注册过 存储视频路径 存到courseVideo
        QueryWrapper<CourseVideo> courseVideoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("path", path);
        infoQueryWrapper.select("course_id","teacherName");
        CourseVideo videoInfo = courseVideoService.getBaseMapper().selectOne(courseVideoQueryWrapper);
        if (videoInfo != null) {
            return R.FAILED("课程路径已存在");
        }
        CourseVideo courseVideo = new CourseVideo();
        courseVideo.setCourseId(name);
        courseVideo.setTeacherId(lecturer);
        courseVideo.setPath(path);
        videoServiceImpl.save(courseVideo);
        log.info("creatCourse ===>{}，courseVideo ===>{} " + courseVideo);
        return R.SUCCESS("课程创建成功");
    }

    private String generateCourseNum(Integer collegeId) {
        //
        String head = "NULL";
        String body = "";
        switch (collegeId) {
            case 2: head = "C";
            case 3: head = "I";
            case 8: head = "F";
            case 9: head = "A";
            case 10: head = "B";
            case 11: head = "E";
            case 12: head = "L";
            case 13: head = "S";
        }
        for (int i = 0; i < 8; i++) {
            String m = String.valueOf((int) (Math.random() * 9));//转换成字符串
            body = body + m;//拼接字符串
        }
        String num = head + body;
        //TODO:检查编号是否存在 ,如果存在重新生成一个编号
        return num;
    }


    @Override
    public R searchCourse(Course course) {
        return null;
    }

    @Override
    public R deleteCourse(Integer id) {

        return null;
    }

    @Override
    public R updateCourse(String courseName, Course course) {
        QueryWrapper<Course> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("name", courseName);
        infoQueryWrapper.select("id");
        Course courseInfo = this.baseMapper.selectOne(infoQueryWrapper);
        if (courseInfo == null) {
            return R.FAILED("课程不存在");
        }
        courseInfo.setName(course.getName());
        courseInfo.setCollegeId(course.getCollegeId());
        courseInfo.setLecturer(course.getLecturer());
        courseInfo.setIntro(course.getIntro());
        this.baseMapper.updateById(courseInfo);
        log.info("课程信息重置成功");
        return R.SUCCESS("课程信息重置成功");
    }


    @Override
    public R getCourseList() {
        List<Course> courseList = this.baseMapper.listCourse();
        log.info("===>" + courseList);
        return R.SUCCESS("查询课程列表成功").setData(courseList);
    }

    @Override
    public R getStudentCourseList(String userName) {
        List<StudentCourseVo> courseList = this.baseMapper.listStudentCourse(userName);
        log.info("===>" + courseList);
        return R.SUCCESS("查询学生课程列表成功").setData(courseList);
    }

    @Override
    public R uploadingVideo(String userName, CourseVideo courseVideo) {
        String path = courseVideo.getPath();
        return R.SUCCESS("上传视频成功");
    }

    /**
     * 学生添加课程
     * @param studentCourseVo
     * @return
     */
    @Override
    public R addStudentCourse(StudentCourseVo studentCourseVo) {
        String userName = studentCourseVo.getUserName();
        String courseName = studentCourseVo.getCourseName();
        String lecturer = studentCourseVo.getLecturer();
        String intro = studentCourseVo.getIntro();
        Integer collegeId = studentCourseVo.getCollegeId();
        if (TextUtils.isEmpty(userName)) {
            return R.FAILED("用户名称不可为空");
        }
        if (TextUtils.isEmpty(courseName)) {
            return R.FAILED("课程名称不可为空");
        }
        // 判断课程名称否有被注册
        QueryWrapper<StudentCourse> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("course_name", courseName);
        infoQueryWrapper.select("user_name");
        StudentCourse courseInfo = studentCourseService.getBaseMapper().selectOne(infoQueryWrapper);
        if (courseInfo != null) {
            return R.FAILED("课程名称已存在");
        }
        StudentCourse targetCourse = new StudentCourse();
        targetCourse.setUserName(userName);
        targetCourse.setCourseName(courseName);
        targetCourse.setCollegeId(collegeId);
        targetCourse.setLecturer(lecturer);
        targetCourse.setIntro(intro);
        studentCourseServiceImpl.save(targetCourse);
        log.info("creatCourse ===>{}，targetCourse ===>{} " + targetCourse);
        return R.SUCCESS("课程创建成功");
    }


}
