package com.spring.aop.readwriteseparation.config;


import com.spring.aop.readwriteseparation.constant.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yn.qiao
 * @version 1.0
 * @create 2021-05-04 22:41
 **/
@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        log.info("切换到master");
    }

    public static void slave() {

        //  轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index  != 0) {
            set(DBTypeEnum.SLAVE1);
            log.info("切换到slave1");
        }else {
            set(DBTypeEnum.SLAVE2);
            log.info("切换到slave2");
        }
    }
}
