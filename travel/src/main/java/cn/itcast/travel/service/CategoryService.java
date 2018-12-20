package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    //查询所有导航数据
    public List<Category> findAll() throws IOException;
}
