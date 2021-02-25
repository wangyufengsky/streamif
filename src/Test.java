import factory.NewIf;
import interfaces.Do;
import interfaces.Predicate;

public class Test {


    public static void main(String[] args) {

        Person person=new Person("cao","23");
        System.out.println(person.toString());

        NewIf.create(person,"name")
                .ifEmpty()
                .ifSetString("Wang")
                .ifNotEmpty("age")
                .ifSetString("24")
                .end();
        System.out.println(person.toString());

        NewIf.create(person,"name")
                .IF(bean->"cao".equals(bean.getName()))
                .DO(bean->bean.setName("Liu"))
                .end();
        System.out.println(person.toString());

        NewIf.create(person,"name")
                .IF(new Predicate<Person>() {
                    @Override
                    public boolean satisfy(Person person) {
                        return "Liu".equals(person.getName());
                    }
                })
                .DO(new Do<Person>() {
                    @Override
                    public void DO(Person person) {
                        person.setName("Zhang");
                    }
                }).end();
        System.out.println(person.toString());

        Person person1=new Person("Li","1");
        NewIf.create(person).ifInstanceof(Person.class).DO(new Do<Person>() {
            @Override
            public void DO(Person person) {
                person1.setName(person.getName());
            }
        });
        System.out.println(person.toString());
        System.out.println(person1.toString());
    }
}
