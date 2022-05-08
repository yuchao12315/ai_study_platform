package com.csuft.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.common.response.R;
import com.csuft.uc.entity.CourseVideo;
import com.csuft.uc.mapper.CourseVideoMapper;
import com.csuft.uc.service.ICourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-05-05
 */
@Service
public class CourseVideoServiceImpl extends ServiceImpl<CourseVideoMapper, CourseVideo> implements ICourseVideoService {

    @Autowired
    public ICourseVideoService courseVideoService;
    @Override
    public R getCourseVideo(String courseName, String teacherName) {
        CourseVideo courseVideoIndo = this.baseMapper.getCourseVideoPath(courseName, teacherName);
        if (courseVideoIndo == null) {
            R.FAILED("课程不存在");
        }
        return R.SUCCESS("视频获取成功").setData(courseVideoIndo);
    }
}
