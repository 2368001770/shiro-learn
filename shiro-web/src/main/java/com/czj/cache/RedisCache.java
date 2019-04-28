package com.czj.cache;

import com.czj.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache<E,V> implements Cache<E, V> {

    private final String CACHE_PREFIX = "czj-cache";

    @Resource
    private JedisUtil jedisUtil;

    public V get(E e) throws CacheException {

        System.out.println("从 redis 中获取权限数据");
        byte[] value = jedisUtil.get(getKey(e));
        if(value != null){
            return (V)SerializationUtils.deserialize(value);
        }
        return null;
    }

    public V put(E e, V v) throws CacheException {

        System.out.println("从 redis 中存储权限数据");
        byte[] key = getKey(e);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,666);

        return v;
    }

    public V remove(E e) throws CacheException {

        System.out.println("从 redis 中删除权限数据");
        byte[] key = getKey(e);
        byte[] value = jedisUtil.get(key);
        jedisUtil.delete(key);

        if(value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    public void clear() throws CacheException {

    }

    public int size() {
        return 0;
    }

    public Set<E> keys() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    //自定义方法获取 key 数组
    private byte[] getKey(E k){

        if(k instanceof String){
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }
}
