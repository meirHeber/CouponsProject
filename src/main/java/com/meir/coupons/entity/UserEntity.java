package com.meir.coupons.entity;

import javax.persistence.*;


@Entity
@Table(name="Users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "AGE", nullable = false)
    private int age;

    @ManyToOne
    private CompanyEntity company;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
