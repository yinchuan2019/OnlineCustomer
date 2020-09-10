package com.app.interfaces.server.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.app.core.util.StringUtils;

@Configuration
public class RedisPoolFactory {

	//自动注入redis配置属性文件
    @Autowired
    private RedisProperties properties;

    /**
     * 	将redis连接池注入spring容器
     * @return
     */
    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        String password = properties.getPassword();
        if(StringUtils.isEmpty(password)){
        	JedisPool pool = new JedisPool(config, properties.getHost(), properties.getPort(), 30000);
        	return pool;
        }else{
        	JedisPool pool = new JedisPool(config, properties.getHost(), properties.getPort(), 30000, password);
        	return pool;
        }
    }
}
