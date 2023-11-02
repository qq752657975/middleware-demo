package com.ygb.app_pay_demo.controller;

import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/2/27 9:45
 */
@RestController
@RequestMapping("pay")
public class CallBackDemoController {

    @PostMapping("alipay")
    public String aliPay(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject);
        return "success";
    }
}
