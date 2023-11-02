package com.ygb.app_pay_demo;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppPayDemoApplicationTests {

    @SneakyThrows
    @Test
    void contextLoads() {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2021000117608007",
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCMCj/wbARakgJR0/2PoiGBXfx6O5aaz5tQxh9ICPgEOb2AaiP9t4CBXs+/1872Zs1eB9q6CLYv+qF7Kr4FzbB8Hn3FzKPYOsWXkHRGMoo6WGTM8PBPF/7XYypVut77h5Addodg2V1Rmt81kV66mMXi6wOGOqRLk180vQ3cq1cqAhMbi6l5A9Jx1FZbOquMEA2qH27SJTw2g5JT/o8WZgH/ZxpC1o9daGWkzp41Ba7PRMHGMxBt/D1kTaXjIOAiMJ84q4UDYwaVQi0vOMZIS/PJyyJj9ep7VkXjYUCSx9deU5jeNoY5kWyy+ylkUlT8E5iBKHr5EeiSgUCaXmdekDJJAgMBAAECggEAIwu5nqBZopThsbq8vQJVIjv0IF7jPNDxzJAYaf2cm+obS5TEdw1CQp4InbkTWUS25m5OxbnCZlVxrhIvReKI5Tb4QNUKt+VVZ63F+39QanOOnl85tIGLzDlL8fJ3NTlizozIKmubV/3LGGvN6iKrezNXSXNJhIRYEimx22Shxqgn2cUviMj0V78p3xnfiZAuNTKD2Rv4SApCjz2uJr88lPRAHRmDE9E8R96yFQ14BBiyu3PI8agN8aPz5YWWYW+8bej9OARqlPpcxyxm5upXNPOB5+/3426e4x2hai7DazDZ7k3EEeiDPyCjir/TbUp25PgHeSjGk3vYyXBmEnkeVQKBgQDj+ITi0NJQPVVzS+6pLo5FdRjeGI1gvbSmJwL5viTcjteRndRCWE/f+8YXrEJS5s1HlaAYP+b4acES146aZKrYX9eqZbOaiahF+G/4/mochHRqMLLlY4C4CW83JDdr5/Xs8GZdEpqWA/325gK8z4CMgZURmN7IwmGhza9LzqzkxwKBgQCdQhJiWvQSA33g2tL39oZD7qZeGItdOk1cMTl/VGSes5ZmjKpDskFGcWdlhom+7+alWIJLBk/XBPo3xerWmvJ+gXVjMWGmToxXyrzILfhI0gJ/JKNoL+kfYFhEJ7aG70B7MiM5l+aCZtDTmfbHD6cp6eV5zxSs9Zf5v2HmBp8AbwKBgQCL+ZweaHm6Ku68zHLODnfszelm9UxZiiHRo0KAde/MPSi/kFk0I2Vn8f+/qVVP8cc5nPWf7hyziruyBgjZY9ZC5RZW2ftdeAiKbfDSweQFfo68ZQpzLpVqauCOyv38k4C86x7vJu0kD29wmeo0ZHRZ0XbmRycDvdaUsuZfdZdTIQKBgQCa4oyA2twxMcc1Q9Et8FzlY5PwcXQx6k5vUvwycMh5lp+3eSvpaRPpcaBI2m2o5Vfh2n7ZrenM+VYIwKI/pHTaLF+VxvlKd2q6vS+aacx0zIMw/sECyM1u3dGRrxuTYum7bPJMs1ORc/qRrr7j6ILYQUqG4nKWt31ruheOG09XeQKBgQCkl36QlohtobTPEM7tszuFjBIFqJtbC529+4Lnd0QWM8kb6ZpHejM/aG9I6YfGLWB06IerZo2MfMQmlGSEP+4KJUzq4ssxPaAShfLkYeLMtdLctle1W5JsvIKFeos6EgTp/uqpWxXrtrwjr/KHYlphRdoiyVfWuL/t350wUu/bWw==",
                "json",
                "UTF8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwIIL9EQ5ofeLMXyoyhQHehgCrLk90npREUU5P+ryBG2Jq0JGmJzG2h2qQZVwvfSzeN8pRZ0QysOvLA4lwzkERbrLXOp2an9M/K4QVVSieNko1WAKl3OSxCwB0toIAgjWi44tIuO8OlEaAxtwKoZPdC1GE8QNcE4cE+WQ9KiBxLSJWUqaqGXQfYoyhSUYHCNwzA6LCdCf+3OUXg2ozNScoYeU/XpVYvnL5i8mW0e/uzW7Vrka2bCEiNYqMvPrmu+y+fZaNEApZ1iwBr5jbPVRL2ozpCy+7xP1rn4CGyvvC7j8xEbYG8f1+SND+QSADL9MrQ7pX4tjspyoK6opg1277QIDAQAB",
                "RSA2");
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", "20210817010101004");
        bizContent.set("total_amount", 0.01);
        bizContent.set("subject", "测试商品");
        bizContent.set("product_code", "QUICK_MSECURITY_PAY");

        request.setBizContent(bizContent.toString());
        request.setNotifyUrl("");
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        System.out.println(response.getBody());
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}
