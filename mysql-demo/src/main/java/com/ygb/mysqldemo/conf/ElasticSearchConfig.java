//package com.ygb.mysqldemo.conf;
//
//import lombok.*;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * elasticsearch配置
// * @version 1.0
// * @author: yang
// * @date: 2021/09/13 11:29
// */
//@Configuration
//@ConfigurationProperties(prefix = "elasticsearch")
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class ElasticSearchConfig {
//    /**节点*/
//    private String clusterHost;
//    private Integer nodePost;
//
//    private static final int TIME_OUT = 5 * 6 * 1000;
//
//    @Bean
//    public RestClientBuilder restClientBuilder(){
//         return RestClient.builder(
//                 new HttpHost(clusterHost,nodePost,"http")
//         );
//    }
//
//    @Bean(destroyMethod = "close")
//    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder){
//        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setSocketTimeout(TIME_OUT));
//        return new RestHighLevelClient(restClientBuilder);
//    }
//
//
//}
