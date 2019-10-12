/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsApplication, v0.1 2019年03月02日 10:06闫迎军(YanYingJun) Exp $
 */
@SpringBootApplication
@MapperScan(basePackages = { "io.renren.sms.dao"})
public class SmsApplication {

    public static void main(String[] args){
        SpringApplication.run(SmsApplication.class, args);
    }
}
