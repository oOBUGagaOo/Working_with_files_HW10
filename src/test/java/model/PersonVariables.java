package model;

import java.util.List;

public class PersonVariables {
    public String name;
    public Integer age;
    public Boolean isClever;
    public List<String> hobbies;
    public Passport passport;

    public static class Passport {
        public Long number;
        public String issueDate;
    }
}