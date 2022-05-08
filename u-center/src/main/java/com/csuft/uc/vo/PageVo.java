package com.csuft.uc.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页视图对象
 */
@Data
public class PageVo<T> {

    //数据
    private List<T> list;
    //每一页的数据长度
    private int listSize;
    //当前页码
    private int currentPage;
    //总页
    private int totalPage;
    //总的记录数
    private long totalCount;
    //是否有下一页和上一页
    private boolean hasPrePage;
    private boolean hasNextPage;
}
