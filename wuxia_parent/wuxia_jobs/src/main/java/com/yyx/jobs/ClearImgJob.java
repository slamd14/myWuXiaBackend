package com.yyx.jobs;

import com.yyx.constant.RedisConstant;
import com.yyx.utils.AliyunOSSUtil;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 自定义job，定时清理垃圾图片
 */
public class ClearImgJob {

    @Resource
    private JedisPool jedisPool;

    public void clearImg(){
        // 根据redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_OSS_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null){
            for (String trashPicName : set) {
                // 删除阿里云OSS上的图片
                AliyunOSSUtil.delete(trashPicName);
                // 从redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_OSS_RESOURCES, trashPicName);
                System.out.println("自定义任务执行，清理垃圾图片:" + trashPicName);
            }
        }
    }
}
