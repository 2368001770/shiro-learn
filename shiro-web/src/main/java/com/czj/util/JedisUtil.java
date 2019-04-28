package com.czj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author czj
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    public Jedis getResource(){
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key,byte[] value){
        Jedis jedis = getResource();
        try{
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();
        }
    }

    public void expire(byte[] key,int expireTime){
        Jedis jedis = getResource();
        try{
            jedis.expire(key,expireTime);
        }finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key){
        Jedis jedis = getResource();
        try{
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void delete(byte[] key){
        Jedis jedis = getResource();
        try{
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String prefix){
        Jedis jedis = getResource();
        try{
            return jedis.keys((prefix + "*").getBytes());
        }finally {
            jedis.close();
        }
    }


}
