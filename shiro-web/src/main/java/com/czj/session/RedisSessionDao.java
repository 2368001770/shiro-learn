package com.czj.session;

import com.czj.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author czj
 */
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource
    private JedisUtil jedisUtil;

    private final String SHIRO_SESSION_PREFIX = "czj";



    protected Serializable doCreate(Session session) {

        System.out.println("create session");
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {

        System.out.println("read session");
        if(sessionId == null){
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    public void update(Session session) throws UnknownSessionException {

        System.out.println("update session");
        if(session != null && session.getId() != null){
            saveSession(session);
        }
    }

    public void delete(Session session) {

        System.out.println("delete session");
        if(session != null && session.getId() != null){
            byte[] key = getKey(session.getId().toString());
            jedisUtil.delete(key);
        }
    }

    public Collection<Session> getActiveSessions() {

        System.out.println("get active session");
        Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<Session>();
        if(CollectionUtils.isEmpty(keys)){
            return sessions;
        }

        for(byte[] key : keys){
            Session session =  (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }

        return sessions;
    }

    private void saveSession(Session session){

        if(session != null && session.getId() != null){
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
            jedisUtil.expire(key,666);
        }
    }

    private byte[] getKey(String key){
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }
}
