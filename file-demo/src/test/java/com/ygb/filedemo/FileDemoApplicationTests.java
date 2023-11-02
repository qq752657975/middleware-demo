package com.ygb.filedemo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

@SpringBootTest
class FileDemoApplicationTests {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		File[] ls = FileUtil.ls(input.next());
		for (File l : ls) {


		}
	}
	@Test
	void contextLoads() {
		BigDecimal bigDecimal = new BigDecimal(1001);
		BigDecimal bigDecimal1 = new BigDecimal("0.15");
		BigDecimal multiply = bigDecimal.multiply(bigDecimal1).setScale(0, RoundingMode.UP);
		System.out.println(multiply.longValue());
	}

	@Test
	void conTime(){
		long time = DateUtil.parse("3300-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime();
		System.out.println(time);
	}

	@Test
	void LongMax(){
		long id = Long.parseLong(IdUtil.getSnowflake(1,1).nextId() + "12");
		System.out.println(id);

	}

}
