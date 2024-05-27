package com.staxrt.tutorial.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "category_relations",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )

    @JsonManagedReference
    private Set<CategoryEntity> children = new HashSet<>();

    @ManyToMany(mappedBy = "children")
    @JsonBackReference
    private Set<CategoryEntity> parents = new HashSet<>();

    public String getName() {
        return name;
    }

    public Set<CategoryEntity> getParents() {
        return parents;
    }

    public Set<CategoryEntity> getChildren() {
        return children;
    }

    public void setParents(Set<CategoryEntity> parents) {
        this.parents = parents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildren(Set<CategoryEntity> children) {
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
