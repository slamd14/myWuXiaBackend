package com.yyx.service;

import com.yyx.entity.PageResult;
import com.yyx.entity.QueryPageBean;

public interface BookService {

    public PageResult findPage(QueryPageBean queryPageBean);

}
