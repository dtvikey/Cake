package com.imooc.cake.service;

import com.imooc.cake.common.MyBatisUtils;
import com.imooc.cake.entity.Cake;
import com.imooc.cake.mapper.CakeMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

/**
 * @Author: dtvikey
 * @Date: 23/11/18 上午 11:20
 * @Version 1.0
 * 蛋糕
 */
public class CakeService {

    /**
     * 根据分类分页查询蛋糕
     * @param categoryId 蛋糕分类ID
     * @param page  要查询的页数
     * @param size  要查询的记录数
     * @return  蛋糕集合
     */
    public List<Cake> getCakesByCategoryId(Long categoryId,Integer page,Integer size){
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            CakeMapper mapper = sqlSession.getMapper(CakeMapper.class);
            return mapper.getCakesByCategoryId(categoryId,(page-1) * size,size);
        }finally {
            sqlSession.close();
        }

    }

    /**
     * 新增蛋糕
     * @param cake 蛋糕信息
     */
    public void addCake(Cake cake){

        Date now = new Date();
        cake.setCreateTime(now);
        cake.setUpdateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {

            CakeMapper mapper = sqlSession.getMapper(CakeMapper.class);
            mapper.addCake(cake);
            sqlSession.commit();


        }finally {

            sqlSession.close();

        }

    }


}
