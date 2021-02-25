import factory.NewIf;

public class TestELSE {
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
}
