package com.ygb.shardingdemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygb.shardingdemo.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    @Select("select t.* from course c \n" +
            "left join t_dict t on c.cid = t.dict_id")
    List<Dict> selectListLeft();
}
