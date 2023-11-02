package com.ygb.esdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygb.esdemo.pojo.EsDataDemo;
import org.apache.ibatis.annotations.Mapper;

/**
 * es测试mapper
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/1/8 21:18
 */
@Mapper
public interface EsDataDemoMapper extends BaseMapper<EsDataDemo> {
}
