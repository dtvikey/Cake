package com.imooc.cake.service;

import com.imooc.cake.common.MyBatisUtils;
import com.imooc.cake.entity.Category;
import com.imooc.cake.mapper.CategoryMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

/**
 * @Author: dtvikey
 * @Date: 23/11/18 下午 02:30
 * @Version 1.0
 * 分类
 */
public class CategoryService {

    /**
     * 查询全部蛋糕分类
     * @return 全部蛋糕分类
     */
    public List<Category> getCategories(){
        SqlSession sqlSession = MyBatisUtils.openSession();
        try{
            CategoryMapper mapper = sqlSession.getMapper(CategoryMapper.class);
            return mapper.getCategories();
        }finally {
            sqlSession.close();
        }

    }

    public void addCategory(Category category){
        Date now = new Date();
        category.setCreateTime(now);
        category.setUpdateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();
        try{

            CategoryMapper mapper = sqlSession.getMapper(CategoryMapper.class);
            mapper.addCategory(category);
            sqlSession.commit();

        }finally {
            sqlSession.close();
        }
    }
}
