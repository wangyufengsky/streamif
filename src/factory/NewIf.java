package factory;

import impl.MyStreamIf;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewIf {

    public static <T> MyStreamIf<T> create(T t){
        return new MyStreamIf.Builder<T>().bean(t).build();
    }

    public static <T> MyStreamIf<T> create(T t, Field field){
        return new MyStreamIf.Builder<T>().bean(t).field(field).build();
    }

    public static <T> MyStreamIf<T> create(T t,String name){
        Field[] fields = t.getClass().getDeclaredFields();
        List<Field> list=Arrays.stream(fields).filter(f->f.getName().equals(name)).collect(Collectors.toList());
        if(list.size()>0){
            return new MyStreamIf.Builder<T>().bean(t).field(list.get(0)).build();
        } return  new MyStreamIf.Builder<T>().bean(t).build();
    }

}
