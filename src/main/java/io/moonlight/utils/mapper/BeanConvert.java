package io.moonlight.utils.mapper;

import com.google.common.collect.Lists;
import jodd.bean.BeanCopy;
import jodd.bean.BeanUtil;
import jodd.introspector.ClassDescriptor;
import jodd.introspector.ClassIntrospector;
import jodd.introspector.FieldDescriptor;
import jodd.introspector.PropertyDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by qt on 2017/7/27.
 */
public abstract class BeanConvert {


    private BeanConvert() {}

    public static <T> T transform(Object source, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        BeanCopy.beans(source, t).copy();
        return t;
    }

    public static <T> T transform(Object source, Class<T> clazz, String... fields) {

        try {
            T t = clazz.newInstance();

            ClassDescriptor      sourceClassDesc = ClassIntrospector.lookup(source.getClass());
            ClassDescriptor      destClassDesc   = ClassIntrospector.lookup(clazz);
            PropertyDescriptor[] destProp        = destClassDesc.getAllPropertyDescriptors();

            Field[] sourceFields = source.getClass().getDeclaredFields();

            //        Set<String> destFieldSet = Arrays.asList(clazz.getDeclaredFields()).stream().map(Field::getName).collect(Collectors.toSet());
            //        destFieldSet.addAll(Stream.of(clazz.getSuperclass().getDeclaredFields()).map(Field::getName).collect(Collectors.toSet()));

            Set<String> destFieldSet = Stream.of(destProp).map(PropertyDescriptor::getName).collect(Collectors.toSet());
            for (Field sourceField : sourceFields) {
                if (List.class.isAssignableFrom(sourceField.getType())) continue;
                if (destFieldSet.contains(sourceField.getName())) {
                    BeanUtil.pojo.setProperty(t, sourceField.getName(), BeanUtil.pojo.getProperty(source, sourceField.getName()));
                }
            }

            Stream.of(fields).map(fieldNames -> {
                String[] filedNameArr = fieldNames.split("->");
                String[] filedName    = new String[2];
                filedName[0] = filedNameArr[0];
                filedName[1] = (filedNameArr.length == 1) ? filedNameArr[0] : filedNameArr[1];
                return filedName;
            }).forEach(fieldNames -> {
                try {
                    FieldDescriptor sourceField = sourceClassDesc.getFieldDescriptor(fieldNames[0], true);//ReflectionUtils.findField(source.getClass(), fieldNames[0]);
                    FieldDescriptor field       = destClassDesc.getFieldDescriptor(fieldNames[1], true);//ReflectionUtils.findField(t.getClass(), fieldNames[1]);
                    if (sourceField != null && field != null) {
                        if (Objects.equals(List.class, field.getRawType())) {//List.class.isAssignableFrom(field.getType())
                            Class fc = field.getRawComponentType();

                            List sourceValueList = BeanUtil.pojo.getProperty(source, fieldNames[0]);

                            List destValueList = Lists.newArrayList();
                            for (Object sourceValue : sourceValueList) {
                                destValueList.add(transform(sourceValue, fc, fields));
                            }

                            BeanUtil.pojo.setProperty(t, fieldNames[1], destValueList);
                        } else {
                            BeanUtil.pojo.setProperty(t, fieldNames[1], BeanUtil.pojo.getProperty(source, fieldNames[0]));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static <T> T transform(Object source, String[] fields, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();

        ClassDescriptor      destClassDesc = ClassIntrospector.lookup(clazz);
        PropertyDescriptor[] destProp      = destClassDesc.getAllPropertyDescriptors();

        Field[] sourceFields = source.getClass().getDeclaredFields();

        //        Set<String> destFieldSet = Arrays.asList(clazz.getDeclaredFields()).stream().map(Field::getName).collect(Collectors.toSet());
        //        destFieldSet.addAll(Stream.of(clazz.getSuperclass().getDeclaredFields()).map(Field::getName).collect(Collectors.toSet()));

        Set<String> destFieldSet = Stream.of(destProp).map(PropertyDescriptor::getName).collect(Collectors.toSet());
        for (Field sourceField : sourceFields) {
            if (List.class.isAssignableFrom(sourceField.getType())) continue;
            if (destFieldSet.contains(sourceField.getName())) {
                BeanUtil.pojo.setProperty(t, sourceField.getName(), BeanUtil.pojo.getProperty(source, sourceField.getName()));
            }
        }

        Stream.of(fields).map(fieldNames -> {
            String[] filedNameArr = fieldNames.split("->");
            String[] filedName    = new String[2];
            filedName[0] = filedNameArr[0];
            filedName[1] = (filedNameArr.length == 1) ? filedNameArr[0] : filedNameArr[1];
            return filedName;
        }).forEach(fieldNames -> {
            try {
                Field sourceField = source.getClass().getField(fieldNames[0]);
                Field field       = t.getClass().getField(fieldNames[1]);
                if (sourceField != null && field != null) {
                    if (List.class.isAssignableFrom(field.getType())) {
                        Type fc = field.getGenericType();

                        List sourceValueList = BeanUtil.pojo.getProperty(source, fieldNames[0]);

                        List destValueList = Lists.newArrayList();
                        for (Object sourceValue : sourceValueList) {
                            destValueList.add(transform(sourceValue, fields, getClass(fc, 0)));
                        }

                        BeanUtil.pojo.setProperty(t, fieldNames[1], destValueList);
                    } else {
                        BeanUtil.pojo.setProperty(t, fieldNames[1], BeanUtil.pojo.getProperty(source, fieldNames[0]));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return t;
    }

    private static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }
}

//        Field[] fs = source.getClass().getDeclaredFields();
//        for (Field f : fs) {
//            Class fieldClazz = f.getType(); // 得到field的class及类型全路径
//
//            if (fieldClazz.isPrimitive()) continue;  //【1】 //判断是否为基本类型
//
//            if (fieldClazz.getName().startsWith("java.lang")) continue; //getName()返回field的类型全路径；
//
//            if (fieldClazz.isAssignableFrom(List.class)) //【2】
//            {
//                Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
//
//                if (fc == null) continue;
//
//                if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
//                {
//                    ParameterizedType pt = (ParameterizedType) fc;
//
//                    Class genericClazz = (Class) pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。
//
//                    System.out.println(genericClazz);
//                }
//            }
//        }
