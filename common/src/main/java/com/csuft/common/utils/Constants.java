package com.csuft.common.utils;

public interface Constants {

    interface User {
        String KEY_MAIL_CODE = "key_mail_code_";
        String KEY_MAIL_CODE_IP = "key_mail_code_ip_";
        String KEY_MAIL_CODE_ADDRESS = "key_mail_code_address_";
        String DEFAULT_AVATAR = "https://cdn.sunofbeaches.com/images/default_avatar.png";
        String DEFAULT_STATUS = "1";
        String DISABLE_STATUS = "0";
        String KEY_TOKEN = "key_token_";
        String KEY_ADMIN_INIT_STATE = "admin_init_state";
        String KEY_TEACHER_INIT_STATE = "teacher_init_state";
        String KEY_STUDY_TOKEN = "study_token";
        String KEY_SALT = "key_salt_";
        String SUPER_ROLE_LABEL = "超级管理员" ;
        String SUPER_ROLE_NAME ="SUPER_ADMIN";
        String TEACHER_ROLE_LABEL = "老师" ;
        String TEACHER_ROLE_NAME ="TEACHER";
    }

    interface Millions {
        long SECOND = 1000;
        long MIN = 60 * SECOND;
        long HOUR = MIN * 60;
        long TWO_HOUR = HOUR * 2;
        long DAY = HOUR * 24;
        long MONTH = DAY * 30;
    }

    interface TimeSecond {
        int ONE = 1;
        int MIN = 60 * ONE;
        int FIVE_MIN = 5 * MIN;
        int HOUR = MIN * 60;
        int TWO_HOUR = HOUR * 2;
        int DAY = HOUR * 24;
    }

    int DEFAULT_SIZE = 1;
}
