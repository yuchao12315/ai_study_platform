package com.csuft.uc.vo;

import lombok.Data;

@Data
public class StudentCourseVo {

    protected String id;
    protected String userName;
    protected String courseName;
    protected String lecturer;
    protected String intro;
    protected String cover;
    protected Integer collegeId;
}
