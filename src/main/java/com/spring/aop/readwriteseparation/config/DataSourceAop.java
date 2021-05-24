package com.spring.aop.readwriteseparation.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yn.qiao
 * @version 1.0
 * @create 2021-05-04 22:42
 **/
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.spring.aop.readwriteseparation.annoation.Master) " +
            "&& (execution(* com.spring.aop.readwriteseparation.service..*.select*(..)) " +
            "|| execution(* com.spring.aop.readwriteseparation.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.spring.aop.readwriteseparation.annoation.Master) " +
            "|| execution(* com.spring.aop.readwriteseparation.service..*.insert*(..)) " +
            "|| execution(* com.spring.aop.readwriteseparation.service..*.add*(..)) " +
            "|| execution(* com.spring.aop.readwriteseparation.service..*.update*(..)) " +
            "|| execution(* com.spring.aop.readwriteseparation..*.edit*(..)) " +
            "|| execution(* com.spring.aop.readwriteseparation.service..*.delete*(..)) " +
            "|| execution(* com.spring.aop.readwriteseparation.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }


}
