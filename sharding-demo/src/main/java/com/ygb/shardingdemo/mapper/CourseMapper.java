package com.ygb.shardingdemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygb.shardingdemo.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select * from course")
    public List<Course> queryAllCourse();
}
