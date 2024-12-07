package com.arun.companyms.company;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Getter
//@Setter
@Data
@NoArgsConstructor
@Entity
public class Company {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double rating;

//    @OneToMany(mappedBy = "company")
//    @JsonManagedReference
//    private List<Job> jobs;

//    @OneToMany(mappedBy = "company")
//    private List<Review> reviews;

}
