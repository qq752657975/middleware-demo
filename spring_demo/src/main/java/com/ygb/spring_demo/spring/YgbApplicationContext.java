package com.ygb.spring_demo.spring;

import com.ygb.spring_demo.spring.annotation.Autowired;
import com.ygb.spring_demo.spring.annotation.Component;
import com.ygb.spring_demo.spring.annotation.ComponentScan;
import com.ygb.spring_demo.spring.annotation.Scope;
import com.ygb.spring_demo.spring.interfaces.BeanNameAware;
import com.ygb.spring_demo.spring.interfaces.BeanPostProcessor;
import com.ygb.spring_demo.spring.interfaces.InitializingBean;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 21:48
 */
public class YgbApplicationContext {

    private Class configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> singletonObjects = new HashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public YgbApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫码Bean的方法
        scan(configClass);
        //循环扫码到的所有Bean定义
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            //获取BeanName
            String beanName = entry.getKey();
            //获取对应的Bean定义
            BeanDefinition beanDefinition = entry.getValue();
            //判断是否单例
            if (beanDefinition.getScope().equals("singleton")) {
                //如果是单例，则创建Bean对象
                Object bean = createBean(beanName, beanDefinition);
                //将创建好的Bean对象放入单例池中
                singletonObjects.put(beanName, bean);

            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        //获取Bean定义中存入的class对象
        Class clazz = beanDefinition.getType();

        Object instance = null;
        try {
            //通过无参的构造方法，生成出对应的实例对象
            instance = clazz.getConstructor().newInstance();
            //获取实例对象的所有属性
            for (Field field : clazz.getDeclaredFields()) {
                //判断属性上是否有Autowired注解
                if (field.isAnnotationPresent(Autowired.class)) {
                    //如果有，则将属性设置为可访问
                    field.setAccessible(true);
                    //给属性值设置对应的Bean对象
                    field.set(instance, getBean(field.getName()));
                }
            }
            //如果实例对象实现了BeanNameAware接口，则调用setBeanName方法，将beanName传入
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware)instance).setBeanName(beanName);
            }

            //如果实例对象实现了beanPostProcessor接口，则调用postProcessBeforeInitialization方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }
            //如果实例对象实现了InitializingBean接口，则调用afterPropertiesSet方法
            if (instance instanceof InitializingBean) {
                ((InitializingBean)instance).afterPropertiesSet();
            }
            //如果实例对象实现了beanPostProcessor接口，则调用postProcessAfterInitialization方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //返回Bean实例
        return instance;
    }

    public Object getBean(String beanName) {
        //判断传入的bean的名字在单例池中是否存在，如果不存则抛出异常
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException();
        }
        //通过传入的beanName从beanDefinitionMap缓存中获取对应BeanDefinition对象
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        //判断BeanDefinition对象中的scope属性是否为单例
        if (beanDefinition.getScope().equals("singleton")) {
            //从单例池中回去对应的Bean
            Object singletonBean = singletonObjects.get(beanName);
            //如果Bean不存在，则创建这个Bean
            if (singletonBean == null) {
                //创建Bean
                singletonBean = createBean(beanName, beanDefinition);
                //将创建出来的Bean，放入到单例池中
                singletonObjects.put(beanName, singletonBean);
            }
            //返回Bean
            return singletonBean;
        } else {
            //如果作用域不是单例的，则直接去创建这个Bean，然后进行返回
            Object prototypeBean = createBean(beanName, beanDefinition);
            return prototypeBean;
        }

    }




    private void scan(Class configClass) {
        //判断传入class对象中是否有ComponentScan注解
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            //获取ComponentScan注解
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //获取ComponentScan注解中的value值，就是扫码的路径
            String path = componentScanAnnotation.value();
            //将路径中的.转换为/
            path = path.replace(".", "/");
            //拿到当前类的ClassLoader
            ClassLoader classLoader = YgbApplicationContext.class.getClassLoader();
            //生成路径对象
            URL resource = classLoader.getResource(path);
            //获取路径下的文件
            File file = new File(resource.getFile());
            //如果是目录
            if (file.isDirectory()) {
                //获取目录下的文件
                for (File f : file.listFiles()) {
                    //获取文件的绝对路径
                    String absolutePath = f.getAbsolutePath();
                    //截取路径，从com开始截取到.class结尾
                    absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    //将路径的分割符替换成.号
                    absolutePath = absolutePath.replace("\\", ".");

                    try {
                        //加载文件为class对象
                        Class<?> clazz = classLoader.loadClass(absolutePath);
                        //判断class对象是否有Component注解
                        if (clazz.isAnnotationPresent(Component.class)) {
                            //判断class对象是否实现了BeanPostProcessor接口
                            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                //如果实现了BeanPostProcessor接口，就将class对象实例化，添加到beanPostProcessorList中
                                BeanPostProcessor instance = (BeanPostProcessor) clazz.getConstructor().newInstance();
                                beanPostProcessorList.add(instance);
                            }
                            //判断class对象是否有Component注解
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            //获取Component注解中的value值，就是bean的名字
                            String beanName = componentAnnotation.value();
                            //如果value值为空，就将类名首字母小写作为bean的名字
                            if ("".equals(beanName)) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }
                            //创建BeanDefinition对象
                            BeanDefinition beanDefinition = new BeanDefinition();
                            //把class对象封装到BeanDefinition对象中
                            beanDefinition.setType(clazz);
                            //判断class对象是否有Scope注解
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                //如果有Scope注解，就获取Scope注解中的value值，就是bean的作用域
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                String value = scopeAnnotation.value();
                                beanDefinition.setScope(value);
                            } else {
                                //如果没有Scope注解，就默认为单例
                                beanDefinition.setScope("singleton");
                            }
                            //将beanName和beanDefinition对象添加到beanDefinitionMap中
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }
}
