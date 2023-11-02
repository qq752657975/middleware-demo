package com.ygb.httpdemo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.base.Splitter;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class HttpDemoApplicationTests {

	private final OkHttpClient client = new OkHttpClient();

	@Test
	void contextLoads() throws IOException {

		System.out.println(getHttpRedMoney("1000", "5", "代码测试红包", "88481", "9", "88481"));
	}


	private List<Long> getHttpRedMoney(String total,String count,String remark,String member,String rid,String group ){
		String result = "";
		List<Long> list = new ArrayList<>();
		HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse("http://121.40.229" +
				".226:8088/contror/console/admin/getRandomRedEnvelope")).newBuilder();
		httpBuilder.addQueryParameter("total",total);
		httpBuilder.addQueryParameter("count",count);
		httpBuilder.addQueryParameter("remark",remark);
		httpBuilder.addQueryParameter("member",member);
		httpBuilder.addQueryParameter("rid",rid);
		httpBuilder.addQueryParameter("group",group);
		try {
			Request request = new Request.Builder()
					.url(httpBuilder.build())
					.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),""))
					.build();
			final Call call = client.newCall(request);
			Response response = call.execute();
			if(ObjectUtil.isNotNull(response) && ObjectUtil.isNotNull(response.body())){
				String resultStr = response.body().string();
				result = resultStr.substring(1,resultStr.length() -1);
			}
			if(!"".equals(result)){
				Iterable<String> split = Splitter.on(',').trimResults().omitEmptyStrings().split(result);
				if(CollUtil.isNotEmpty(split)){
					for (String s : split) {
						list.add(Long.parseLong(s));
					}
				}
			}
			return list;
		}catch (Exception e){
			return Collections.emptyList();
		}
	}

}
