package org.home.hone.interfaces;

import org.home.hone.reflection.ObjectAnalyzer;

import java.util.Date;

import static java.lang.System.out;
import static java.util.Collections.reverseOrder;
import static java.util.Arrays.sort;

public class Employee implements Comparable<Employee>, Cloneable {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String n, double s) {
        name = n;
        salary = s;
        hireDay = new Date();
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public int compareTo(Employee other) {
        return Double.compare(salary, other.salary);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee clone = (Employee) super.clone();
        clone.hireDay = (Date) this.hireDay.clone();
        return clone;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Employee e1 = new Employee("Jack", 11000.00);
        Employee e2 = new Employee("John", 10000.00);
        Employee e3 = new Employee("Jane", 9000.00);
        Employee e4 = new Employee("Jim",  12000.00);
        Employee[] a = new Employee[] {e1, e2, e3, e4, (Employee) e4.clone()};
        ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer();
        out.printf("before sort: %s\n", objectAnalyzer.toString(a));
        objectAnalyzer = new ObjectAnalyzer();
        sort(a);
        sort(a, reverseOrder());
        out.printf("after sort: %s\n", objectAnalyzer.toString(a));
    }
}
