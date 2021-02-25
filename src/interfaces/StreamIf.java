package interfaces;

import impl.MyStreamIf;

import java.lang.reflect.Field;


public interface StreamIf<T> {

    MyStreamIf<T> DO(Do<T> ifDo);

    MyStreamIf<T> elseDo(Do<T> ifDo);

    MyStreamIf<T> IF(Predicate<T> predicate);

    MyStreamIf<T> ELSEIF(Predicate<T> predicate);

    MyStreamIf<T> ELSE();

    MyStreamIf<T> ifNotEmpty();

    MyStreamIf<T> ifEmpty();

    MyStreamIf<T> ifNotEmpty(String field);

    MyStreamIf<T> ifEmpty(String field);

    MyStreamIf<T> ifNotEmpty(Field field);

    MyStreamIf<T> ifEmpty(Field field);

    MyStreamIf<T> ifSetString(String str);

    <R> MyStreamIf<T> ifInstanceof(Class<R> r);

    T toBean();

    void end();
}
