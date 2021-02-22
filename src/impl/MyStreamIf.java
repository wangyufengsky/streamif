package impl;

import interfaces.Do;
import interfaces.Predicate;
import interfaces.StreamIf;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyStreamIf<T> implements StreamIf<T> {

    private T t;

    private Field field;

    private boolean flag=false;

    private final List<Boolean> elseFlags=new ArrayList<>();

    private boolean isElse=false;

    public static class Builder<T>{

        private final MyStreamIf<T> target;

        public Builder() {
            this.target = new MyStreamIf<>();
        }

        public Builder<T> bean(T bean){
            target.t = bean;
            return this;
        }

        public Builder<T> field(Field field){
            target.field = field;
            return this;
        }

        public MyStreamIf<T> build(){
            return target;
        }
    }


    @Override
    public MyStreamIf<T> ifNotEmpty() {
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                flag= null!=this.field.get(t) && ""!=this.field.get(t);
            }else{
                flag=false;
            }
        } catch (IllegalAccessException e) {
            flag=false;
        }
        return this;
    }

    @Override
    public MyStreamIf<T> ifEmpty() {
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                flag= null==this.field.get(t) || ""==this.field.get(t);
            }else{
                flag=false;
            }
        } catch (IllegalAccessException e) {
            flag=false;
        }
        return this;
    }

    @Override
    public MyStreamIf<T> ifNotEmpty(String name) {
        Field[] fields = t.getClass().getDeclaredFields();
        List<Field> list= Arrays.stream(fields).filter(f->f.getName().equals(name)).collect(Collectors.toList());
        if(list.size()!=0){
            this.field=list.get(0);
            try {
                if(null!=this.field){
                    this.field.setAccessible(true);
                    flag= null!=this.field.get(t) && ""!=this.field.get(t);
                }else{
                    flag=false;
                }
            } catch (IllegalAccessException e) {
                flag=false;
            }
        }else flag=false;
        return this;
    }

    @Override
    public MyStreamIf<T> ifEmpty(String name) {
        Field[] fields = t.getClass().getDeclaredFields();
        List<Field> list= Arrays.stream(fields).filter(f->f.getName().equals(name)).collect(Collectors.toList());
        if(list.size()!=0){
            this.field=list.get(0);
            try {
                if(null!=this.field){
                    this.field.setAccessible(true);
                    flag= null==this.field.get(t) || ""==this.field.get(t);;
                }else{
                    flag=false;
                }
            } catch (IllegalAccessException e) {
                flag=false;
            }
        }else flag=false;
        return this;
    }

    @Override
    public MyStreamIf<T> ifNotEmpty(Field field) {
        this.field=field;
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                flag= null!=this.field.get(t) && ""!=this.field.get(t);
            }else{
                flag=false;
            }
        } catch (IllegalAccessException e) {
            flag=false;
        }
        return this;
    }

    @Override
    public MyStreamIf<T> ifEmpty(Field field) {
        this.field=field;
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                flag= null==this.field.get(t) || ""==this.field.get(t);;
            }else{
                flag=false;
            }
        } catch (IllegalAccessException e) {
            flag=false;
        }
        return this;
    }

    @Override
    public MyStreamIf<T> IF(Predicate<T> predicate) {
        flag=predicate.satisfy(t);
        return this;
    }

    @Override
    public MyStreamIf<T> ELSEIF(Predicate<T> predicate) {

        predicate.satisfy(t);
        return this;
    }

    @Override
    public MyStreamIf<T> ELSE() {
        isElse=true;
        elseFlags.add(this.flag);
        this.flag=!this.flag;
        return this;
    }


    @Override
    public MyStreamIf<T> ifSetString(String str){
        if(isElse){
            if(elseFlags.stream().parallel().noneMatch(flag-> flag)){
                if(flag&&null!=field){
                    field.setAccessible(true);
                    try {
                        field.set(t,str);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                isElse=false;
            }
        }else {
            if(flag&&null!=field){
                field.setAccessible(true);
                try {
                    field.set(t,str);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    @Override
    public <R> MyStreamIf<T> ifInstanceof(Class<R> r) {
        if(t != null){
            flag= t.getClass().equals(r);
        }else flag=false;
        return this;
    }

    @Override
    public T toBean() {
        return t;
    }

    @Override
    public void end() {
    }


    @Override
    public MyStreamIf<T> DO(Do<T> ifDo) {
        if(isElse){
            if(elseFlags.stream().parallel().noneMatch(flag-> flag)&&flag){
                ifDo.DO(t);
            }
            isElse=false;
        }else {
            if(flag){
                ifDo.DO(t);
            }
        }
        return this;
    }

    @Override
    public MyStreamIf<T> elseDo(Do<T> ifDo) {
        if(!flag){
            ifDo.DO(t);
        }
        return this;
    }

}
