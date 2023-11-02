package com.ygb.esdemo;

import lombok.SneakyThrows;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EsDemoApplicationTests {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@SneakyThrows
	@Test
	void contextLoads() {

		XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
				.startObject()
				.startObject("properties")

				.startObject("number")
				.field("type", "integer")
				.endObject()

				.endObject()
				.endObject();

		CreateIndexRequest request = new CreateIndexRequest("demo");
		request.mapping(mappingBuilder);
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
		boolean acknowledged = createIndexResponse.isAcknowledged();
		if (acknowledged) {
			System.out.println("创建成功");
		} else {
			System.out.println("创建失败");
		}
	}

}
