package org.izolentiy.studentslist.hv;

public class Student {

    private String firstName;
    private String lastName;
    private boolean isMale;
    private int photo;

    public Student(String firstName, String lastName, boolean isMale, int photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMale = isMale;
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
