package com.ygb.esdemo.controllers;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.ygb.esdemo.pojo.EsDataDemo;
import lombok.SneakyThrows;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * es操作
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/1/8 21:05
 */
@RestController
@RequestMapping("/es")
public class EsDemoController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @SneakyThrows
    @GetMapping("/insert_data/{esId}/{number}")
    public String insertData(@PathVariable("esId") Integer esId, @PathVariable("number") Integer number){

        EsDataDemo esDataDemo = new EsDataDemo();
        esDataDemo.setNumber(number);
        esDataDemo.setEsId(esId);

        if (!esDataDemo.insert()) {
            throw new RuntimeException("错误");
        }

        IndexRequest indexRequest = new IndexRequest("demo");
        indexRequest.id(esId.toString());
        indexRequest.source(JSONUtil.toJsonStr(esDataDemo), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return "新增成功";
    }


    @SneakyThrows
    @GetMapping("/update_data/{esId}")
    public String updateData(@PathVariable("esId") Integer esId){


        for (int i = 0; i < 500; i++) {
            EsDataDemo esDataDemo = new EsDataDemo();
            esDataDemo.setNumber(RandomUtil.randomInt(0, 1000000));
            esDataDemo.setEsId(esId);
            if (!esDataDemo.updateById()) {
                throw new RuntimeException("错误");
            }
            BulkRequest bulkRequest = new BulkRequest();
            UpdateRequest updateRequest = new UpdateRequest("demo",esId.toString());
            Map<String,Object> map = new HashMap<>();
            map.put("number",esDataDemo.getNumber());
            updateRequest.doc(map,XContentType.JSON);
            bulkRequest.add(updateRequest);
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        }
        return "修改成功";
    }
}
