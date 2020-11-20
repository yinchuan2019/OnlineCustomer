package com.im.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.im.product.mapper")
public class Application{
    public static void main( String[] args )    {
        try{
            SpringApplication.run(Application.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


