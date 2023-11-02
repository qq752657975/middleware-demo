package com.ygb.spring_demo.spring;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 21:47
 */
public class BeanDefinition {

    private Class type;
    private String scope;
    private boolean isLazy;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
}
