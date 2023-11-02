package com.ygb.mysqldemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.ygb.mysqldemo.pojo.UmsMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 会员mapper
 * @author yang
 * @version 1.0
 * @date 2021/7/22 10:01 上午
 */
@Mapper
public interface UmsMemberMapper extends BaseMapper<UmsMember> {


    UmsMember selectPhone(@Param("phoneNumber") String phoneNumber);
}
