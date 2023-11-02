package com.ygb.mongodemo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.result.UpdateResult;
import com.ygb.mongodemo.pojo.Employee;
import com.ygb.mongodemo.pojo.HistoryGroup;
import com.ygb.mongodemo.pojo.HistoryGroupDTO;
import com.ygb.mongodemo.pojo.Zips;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class MongoDemoApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    /**
     * 创建集合
     */
    @Test
    public void testCollection(){
        //判断集合是否存在
        boolean exists = mongoTemplate.collectionExists("emp");
        if (exists) {
            //删除集合
            mongoTemplate.dropCollection("emp");
        }
        //创建集合
        mongoTemplate.createCollection("emp");
    }

    /**
     * 添加文档
     */
    @Test
    public void testInsert(){
        Employee employee = new Employee(1, "小明", 30,10000.00, new Date());

        //添加文档
        // sava: _id存在时更新数据
        //mongoTemplate.save(employee);
        // insert： _id存在抛出异常 支持批量操作
        mongoTemplate.insert(employee);

        //批量添加
        List<Employee> list = Arrays.asList(
                new Employee(2, "张三", 21,5000.00, new Date()),
                new Employee(3, "李四", 26,8000.00, new Date()),
                new Employee(4, "王五",22, 8000.00, new Date()),
                new Employee(5, "张龙",28, 6000.00, new Date()),
                new Employee(6, "赵虎",24, 7000.00, new Date()),
                new Employee(7, "赵六",28, 12000.00, new Date()));
        mongoTemplate.insert(list,Employee.class);
    }

    /**
     * 查询文档
     */
    @Test
    public void testFind(){
        System.out.println("==========查询所有文档===========");
        List<Employee> list = mongoTemplate.findAll(Employee.class);
        list.forEach(System.out::println);

        System.out.println("==========根据_id查询===========");
        //根据_id查询
        Employee e = mongoTemplate.findById(1, Employee.class);
        System.out.println(e);

        System.out.println("==========findOne返回第一个文档===========");
        //如果查询结果是多个，返回其中第一个文档对象
        Employee one = mongoTemplate.findOne(new Query(), Employee.class);
        System.out.println(one);

        System.out.println("==========条件查询===========");
        //new Query() 表示没有条件
        //查询薪资大于等于8000的员工
        //Query query = new Query(Criteria.where("salary").gte(8000));
        //查询薪资大于4000小于10000的员工
        //Query query = new Query(Criteria.where("salary").gt(4000).lt(10000));
        //正则查询（模糊查询） java中正则不需要有//
        //Query query = new Query(Criteria.where("name").regex("张"));

        //and or 多条件查询
        Criteria criteria = new Criteria();
        //and 查询年龄大于25&薪资大于8000的员
        //criteria.andOperator(Criteria.where("age").gt(25),Criteria.where("salary").gt (8000));
        //or 查询姓名是张三或者薪资大于8000的员工
        criteria.orOperator(Criteria.where("name").is("张 三"),Criteria.where("salary").gt(5000));
        Query query = new Query(criteria);

        //sort排序
        //query.with(Sort.by(Sort.Order.desc("salary")));
        //skip limit 分页 skip用于指定跳过记录数，limit则用于限定返回结果数量。
        query.with(Sort.by(Sort.Order.desc("salary")))
                .skip(0)//指定跳过记录数
                .limit(4); //每页显示记录数

        //查询结果
        List<Employee> employees = mongoTemplate.find( query, Employee.class);
        employees.forEach(System.out::println);
    }

    /**
     * json形式查询
     */
    @Test
    public void testFindByJson() {
        //使用json字符串方式查询
        //等值查询
        //String json = "{name:'张三'}";
        //多条件查询
        String json = "{$or:[{age:{$gt:25}},{salary:{$gte:8000}}]}";
        Query query = new BasicQuery(json);

        //查询结果
        List<Employee> employees = mongoTemplate.find( query, Employee.class);
        employees.forEach(System.out::println);
    }


    /**
     * 更新文档
     */
    @Test
    public void testUpdate(){
        //query设置查询条件
        Query query = new Query(Criteria.where("salary").gte(15000));
        System.out.println("==========更新前===========");
        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        employees.forEach(System.out::println);

        Update update = new Update();
        //设置更新属性
        update.set("salary",13000);

        //updateFirst() 只更新满足条件的第一条记录
        //UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Employee.class);

        //updateMulti() 更新所有满足条件的记录
        //UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Employee.class);

        //upsert() 没有符合条件的记录则插入数据
        //update.setOnInsert("id",11); //指定_id
        UpdateResult updateResult = mongoTemplate.upsert(query, update, Employee.class);

        //返回修改的记录数
        System.out.println(updateResult.getModifiedCount());
        System.out.println("==========更新后===========");
        employees = mongoTemplate.find(query, Employee.class);
        employees.forEach(System.out::println);
    }

    /**
     * 删除文档
     */
    @Test
    public void testDelete(){
        //删除所有文档
        //mongoTemplate.remove(new Query(),Employee.class);

        //条件删除
        Query query = new Query(Criteria.where("salary").gte(10000));
        mongoTemplate.remove(query,Employee.class);
    }

    /**
     * 聚合操作1
     */
    @Test
    public void testAggregation(){

        //$group操作
        GroupOperation groupOperation = Aggregation.group("city").sum("pop").as("totalPop");

        //match
        MatchOperation matchOperation = Aggregation.match(Criteria.where("totalPop").gte(10 * 1000 * 1000));

        //按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(Zips.class, groupOperation, matchOperation);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(typedAggregation, Map.class);

        //获取结构集
        List<Map> mappedResults = aggregationResults.getMappedResults();

        for(Map map:mappedResults){
            System.out.println(map);
        }
    }

    /**
     * 聚合操作2
     */
    @Test
    public void testAggregation2(){

        //$group操作
        GroupOperation groupOperation = Aggregation.group("state","city").sum("pop").as("cityPop");

        //$group操作
        GroupOperation groupOperation2 = Aggregation.group("_id.state").avg("cityPop").as("avgCityPop");

        //$sort
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"avgCityPop");

        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(Zips.class, groupOperation, groupOperation2,sortOperation);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(typedAggregation, Map.class);

        //获取结构集
        List<Map> mappedResults = aggregationResults.getMappedResults();

        for(Map map:mappedResults){
            System.out.println(map);
        }
    }

    /**
     * 聚合操作3
     */
    @Test
    public void testAggregation3(){

        //$group操作
        GroupOperation groupOperation = Aggregation .group("state","city").sum("pop").as("pop");

        //$sort
        SortOperation sortOperation = Aggregation .sort(Sort.Direction.ASC,"pop");

        ///$group
        GroupOperation groupOperation2 = Aggregation
                .group("_id.state")
                .last("_id.city")
                .as("biggestCity")
                .last("pop")
                .as("biggestPop")
                .first("_id.city")
                .as("smallestCity")
                .first("pop").
                as("smallestPop");

        //$project
        ProjectionOperation projectionOperation = Aggregation
                .project("state","biggestCity","smallestCity")
                .and("_id")
                .as("state")
                .andExpression( "{ name: \"$biggestCity\", pop: \"$biggestPop\" }")
                .as("biggestCity")
                .andExpression( "{ name: \"$smallestCity\", pop: \"$smallestPop\" }" )
                .as("smallestCity")
                .andExclude("_id");

        //$sort
        SortOperation sortOperation2 = Aggregation .sort(Sort.Direction.ASC,"state");
        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation( Zips.class, groupOperation, sortOperation, groupOperation2, projectionOperation,sortOperation2);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate .aggregate(typedAggregation, Map.class);

        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }
    }

    @Test
    void  getGroupPage(){
        System.out.println(JSONUtil.toJsonStr(getImGroupPage()));
    }

    public Map<String, Object> getImGroupPage(){
        int pageIndex = 0;
        int pageSize = 15;
        String createTime = "1657985650873";
        String groupNumber = "35310750";
        long sysLoginUserId = 1541321116856356864L;
        int maxPageSize= 13;
        Query query = new Query(Criteria.where("groupNumber").is(groupNumber).and("numberTime").gte(Long.valueOf(createTime)).and("receiverIds").in(Long.toString(sysLoginUserId)).and("deleteMemberId").nin(Long.toString(sysLoginUserId)));
        query.with(Sort.by(Sort.Order.desc("numberTime")))
                .skip(pageIndex)
                .limit(pageSize);
        List<HistoryGroup> historyGroups = mongoTemplate.find(query, HistoryGroup.class);
        List<HistoryGroup> collect = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(16);
        if(CollUtil.isEmpty(historyGroups)){
            map.put("collect",historyGroups);
            map.put("pageIndex",pageIndex);
            return map;
        }
        if(CollUtil.isNotEmpty(historyGroups)){
            for (HistoryGroup historyGroup : historyGroups) {
                if(historyGroup.getStatus().equals(0)){
                    if(historyGroup.getLoginMemberId().equals(Long.toString(sysLoginUserId))){
                        HistoryGroup historyGroup1 = HistoryGroup.builder()
                                .id(historyGroup.getId())
                                .msg(historyGroup.getMsg())
                                .msgId(historyGroup.getMsgId())
                                .groupId(historyGroup.getGroupId())
                                .groupNumber(historyGroup.getGroupNumber())
                                .memberId(historyGroup.getMemberId())
                                .memberHeadImg(historyGroup.getMemberHeadImg())
                                .memberNick(historyGroup.getMemberNick())
                                .memberAccount(historyGroup.getMemberAccount())
                                .memberPhone(historyGroup.getMemberPhone())
                                .memberRemark(historyGroup.getMemberRemark())
                                .msg(historyGroup.getMsg())
                                .createdTime(historyGroup.getCreatedTime())
                                .type(historyGroup.getType())
                                .cellType(historyGroup.getCellType())
                                .isMe(historyGroup.getIsMe())
                                .isRead(historyGroup.getIsRead())
                                .status(historyGroup.getStatus())
                                .redEnvelopeStatus(historyGroup.getRedEnvelopeStatus())
                                .redEnvelopeId(historyGroup.getRedEnvelopeId())
                                .withdraw(historyGroup.getWithdraw())
                                .loginMemberId(historyGroup.getLoginMemberId())
                                .receiverIds(historyGroup.getReceiverIds())
                                .deleteMemberId(historyGroup.getDeleteMemberId())
                                .build();
                        collect.add(historyGroup1);
                    }
                }else {
                    HistoryGroup historyGroup1 = HistoryGroup.builder()
                            .id(historyGroup.getId())
                            .msg(historyGroup.getMsg())
                            .msgId(historyGroup.getMsgId())
                            .groupId(historyGroup.getGroupId())
                            .groupNumber(historyGroup.getGroupNumber())
                            .memberId(historyGroup.getMemberId())
                            .memberHeadImg(historyGroup.getMemberHeadImg())
                            .memberNick(historyGroup.getMemberNick())
                            .memberAccount(historyGroup.getMemberAccount())
                            .memberPhone(historyGroup.getMemberPhone())
                            .memberRemark(historyGroup.getMemberRemark())
                            .msg(historyGroup.getMsg())
                            .createdTime(historyGroup.getCreatedTime())
                            .type(historyGroup.getType())
                            .cellType(historyGroup.getCellType())
                            .isMe(historyGroup.getIsMe())
                            .isRead(historyGroup.getIsRead())
                            .status(historyGroup.getStatus())
                            .redEnvelopeStatus(historyGroup.getRedEnvelopeStatus())
                            .redEnvelopeId(historyGroup.getRedEnvelopeId())
                            .withdraw(historyGroup.getWithdraw())
                            .loginMemberId(historyGroup.getLoginMemberId())
                            .receiverIds(historyGroup.getReceiverIds())
                            .deleteMemberId(historyGroup.getDeleteMemberId())
                            .build();
                    collect.add(historyGroup1);
                }
            }
        }
        Integer pageNumber = pageIndex + pageSize - 1;

        if(CollUtil.isNotEmpty(collect) && collect.size() >= maxPageSize){
            map.put("collect",getHistoryGroup(collect));
            map.put("pageIndex",pageNumber);
            return map;
        }else{
            Query query1 =  new Query(Criteria.where("groupNumber").is(groupNumber).and("numberTime").gte(Long.valueOf(createTime)).and("receiverIds").in(Long.toString(sysLoginUserId)).and("deleteMemberId").nin(Long.toString(sysLoginUserId)));
            query1.with(Sort.by(Sort.Order.desc("numberTime")))
                    .skip(pageNumber)
                    .limit(pageSize / 2);
            List<HistoryGroup> newHistoryGroups = mongoTemplate.find(query1, HistoryGroup.class);
            if(CollUtil.isEmpty(newHistoryGroups)){
                map.put("collect",getHistoryGroup(collect));
                map.put("pageIndex",pageNumber + (pageSize / 2) - 1);
                return map;
            }
            for (HistoryGroup historyGroup : newHistoryGroups) {
                if(historyGroup.getStatus().equals(0)){
                    if(historyGroup.getLoginMemberId().equals(Long.toString(sysLoginUserId))){
                        HistoryGroup historyGroup1 = HistoryGroup.builder()
                                .id(historyGroup.getId())
                                .msgId(historyGroup.getMsgId())
                                .msg(historyGroup.getMsg())
                                .groupId(historyGroup.getGroupId())
                                .groupNumber(historyGroup.getGroupNumber())
                                .memberId(historyGroup.getMemberId())
                                .memberHeadImg(historyGroup.getMemberHeadImg())
                                .memberNick(historyGroup.getMemberNick())
                                .memberAccount(historyGroup.getMemberAccount())
                                .memberPhone(historyGroup.getMemberPhone())
                                .memberRemark(historyGroup.getMemberRemark())
                                .msg(historyGroup.getMsg())
                                .createdTime(historyGroup.getCreatedTime())
                                .type(historyGroup.getType())
                                .cellType(historyGroup.getCellType())
                                .isMe(historyGroup.getIsMe())
                                .isRead(historyGroup.getIsRead())
                                .status(historyGroup.getStatus())
                                .redEnvelopeStatus(historyGroup.getRedEnvelopeStatus())
                                .redEnvelopeId(historyGroup.getRedEnvelopeId())
                                .withdraw(historyGroup.getWithdraw())
                                .loginMemberId(historyGroup.getLoginMemberId())
                                .receiverIds(historyGroup.getReceiverIds())
                                .deleteMemberId(historyGroup.getDeleteMemberId())
                                .build();
                        collect.add(historyGroup1);
                    }
                }else {
                    HistoryGroup historyGroup1 = HistoryGroup.builder()
                            .id(historyGroup.getId())
                            .msgId(historyGroup.getMsgId())
                            .msg(historyGroup.getMsg())
                            .groupId(historyGroup.getGroupId())
                            .groupNumber(historyGroup.getGroupNumber())
                            .memberId(historyGroup.getMemberId())
                            .memberHeadImg(historyGroup.getMemberHeadImg())
                            .memberNick(historyGroup.getMemberNick())
                            .memberAccount(historyGroup.getMemberAccount())
                            .memberPhone(historyGroup.getMemberPhone())
                            .memberRemark(historyGroup.getMemberRemark())
                            .msg(historyGroup.getMsg())
                            .createdTime(historyGroup.getCreatedTime())
                            .type(historyGroup.getType())
                            .cellType(historyGroup.getCellType())
                            .isMe(historyGroup.getIsMe())
                            .isRead(historyGroup.getIsRead())
                            .status(historyGroup.getStatus())
                            .redEnvelopeStatus(historyGroup.getRedEnvelopeStatus())
                            .redEnvelopeId(historyGroup.getRedEnvelopeId())
                            .withdraw(historyGroup.getWithdraw())
                            .loginMemberId(historyGroup.getLoginMemberId())
                            .receiverIds(historyGroup.getReceiverIds())
                            .deleteMemberId(historyGroup.getDeleteMemberId())
                            .build();
                    collect.add(historyGroup1);
                }
            }
        }
        List<HistoryGroup> newCollect =
                collect.stream().sorted(Comparator.comparing(HistoryGroup::getCreatedTime).reversed()).collect(Collectors.toList());
        map.put("collect",getHistoryGroup(newCollect));
        map.put("pageIndex",pageNumber + (pageSize / 2) - 1);
        return map;
    }

    /**
     * 保证查询消息
     * @param historyGroupList 原始对象
     * @return 包装结果
     */
    private List<HistoryGroupDTO> getHistoryGroup(List<HistoryGroup> historyGroupList){
        Long sysLoginUserId = 1541321116856356864L;
        List<HistoryGroupDTO> list = new ArrayList<>();
        historyGroupList.forEach(historyGroup -> {
            HistoryGroupDTO historyGroupDTO = HistoryGroupDTO
                    .builder()
                    .id(historyGroup.getId())
                    .msgId(historyGroup.getMsgId())
                    .groupId(historyGroup.getGroupId())
                    .groupNumber(historyGroup.getGroupNumber())
                    .memberId(historyGroup.getMemberId())
                    .memberHeadImg(historyGroup.getMemberHeadImg())
                    .memberNick(historyGroup.getMemberNick())
                    .memberAccount(historyGroup.getMemberAccount())
                    .memberPhone(historyGroup.getMemberPhone())
                    .memberRemark(historyGroup.getMemberRemark())
                    .msg(historyGroup.getMsg())
                    .createdTime(historyGroup.getCreatedTime())
                    .type(historyGroup.getType())
                    .cellType(historyGroup.getCellType())
                    .isMe(historyGroup.getIsMe())
                    .isRead(1)
                    .status(historyGroup.getStatus())
                    .redEnvelopeStatus(1)
                    .redEnvelopeId(historyGroup.getRedEnvelopeId())
                    .withdraw(historyGroup.getWithdraw())
                    .loginMemberId(historyGroup.getLoginMemberId())
                    .build();
            if(CollUtil.isNotEmpty(historyGroup.getIsRead())){
                historyGroup.getIsRead().forEach(universalKeyValue -> {
                    if(universalKeyValue.getUniversalKey().equals(sysLoginUserId.toString())){
                        historyGroupDTO.setIsRead(Integer.valueOf(universalKeyValue.getUniversalValue()));
                    }
                });
            }

            if(CollUtil.isNotEmpty(historyGroup.getRedEnvelopeStatus())){
                historyGroup.getRedEnvelopeStatus().forEach(universalKeyValue -> {
                    if(universalKeyValue.getUniversalKey().equals(sysLoginUserId.toString())){
                        historyGroupDTO.setRedEnvelopeStatus(Integer.valueOf(universalKeyValue.getUniversalValue()));
                    }
                });
            }

            list.add(historyGroupDTO);
        });
        return list;
    }

}
