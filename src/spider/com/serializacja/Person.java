package spider.com.serializacja;

import java.io.Serializable;

public class Person extends Address implements Serializable {

    public String name;
    public String lastName;
    public int age;

    public Person( String n, String l, int a, String c, String s, int sn, String p){
        this.name = n;
        this.lastName = l;
        this.age = a;
        this.city = c;
        this.street = s;
        this.streetNr = sn;
        this.postCode = p;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNr=" + streetNr +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
