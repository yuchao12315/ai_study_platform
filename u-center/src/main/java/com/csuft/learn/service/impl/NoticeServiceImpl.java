package com.csuft.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.learn.entity.Notice;
import com.csuft.learn.mapper.NoticeMapper;
import com.csuft.learn.service.INoticeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-04-22
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
