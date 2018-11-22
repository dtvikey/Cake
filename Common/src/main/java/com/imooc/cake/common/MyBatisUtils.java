package com.imooc.cake.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author: dtvikey
 * @Date: 22/11/18 上午 11:18
 * @Version 1.0
 * MyBatis 工具类
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {

        try{

            String resource = "config.xml";
            reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static SqlSession openSession(){

        return sqlSessionFactory.openSession();

    }

}
