# java流式条件判断StreamIf

由于工作中存在大量if else判断，非常冗余，所以我用函数式编程方式写了一个if的api，可以使用流式写法进行逻辑编程。


用于条件判断的流式streamApi，封装了诸如对bean的字段，若为空则执行、若不为空则执行，也可以通过匿名函数或lambda表达式自定义条件判断和执行方法。
例子：


    public static void main(String[] args) {

        Person person=new Person("cao","23");
        System.out.println(person.toString());
        
        NewIf.create(person,"name").ifEmpty().ifSetString("Wang").ifNotEmpty("age").ifSetString("24").end();
        System.out.println(person.toString());
        
        NewIf.create(person,"name").IF(bean->"cao".equals(bean.getName())).DO(bean->bean.setName("Liu")).end();
        System.out.println(person.toString());

        NewIf.create(person,"name")
                .IF(new Predicate<Person>() {
                    @Override
                    public boolean satisfy(Person person) {
                        return "cao".equals(person.getName());
                    }
                })
                .DO(new Do<Person>() {
                    @Override
                    public void DO(Person person) {
                        person.setName("Liu");
                    }
                }).end();
        System.out.println(person.toString());
    }
        
以上例子中，第一种是使用封装好的流式api进行非空校验，若成功则进行set方法。
第二种和第三种分别是lambda和匿名函数的自定义流式判断。



    public static void main(String[] args) {
        Person person=new Person("cao","23");
        person.setNum(6);
        System.out.println(person.toString());

        NewIf.create(person)
                .IF(p->p.getNum()<5)
                .DO(p->p.setName("5"))
                .ELSE().IF(p->p.getNum()<10)
                .DO(p->p.setName("10"))
                .ELSE().IF(p->p.getNum()<20)
                .DO(p->p.setName("20"))
                .end();
        System.out.println(person.toString());
    }
    
以上例子中，则是用于正常的if-else逻辑判断的流式写法
