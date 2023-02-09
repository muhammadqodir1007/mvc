package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    @Nationalized
    private String name;


    public Tag(String name) {
        this.name = name;
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }


    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<GiftCertificate> giftCertificates = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return id == tag.id && Objects.equals(name, tag.name) && Objects.equals(giftCertificates, tag.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, giftCertificates);
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name='" + name + '\'' + ", giftCertificates=" + giftCertificates + '}';
    }
}
