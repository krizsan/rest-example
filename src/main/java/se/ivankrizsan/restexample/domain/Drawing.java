package se.ivankrizsan.restexample.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A drawing that consists of an arbitrary number of shapes.
 *
 * @author Ivan Krizsan
 */
@Entity(name = "Drawing")
@Table(name = "Drawings")
public class Drawing extends LongIdEntity {
    /* Constant(s): */

    /* Instance variable(s): */
    @Column(name = "name", nullable = false)
    protected String name;
    @Column(name = "creationDate", nullable = false)
    protected Date creationDate;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "shapeId", referencedColumnName = "id")
    protected Set<Shape> shapes = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String inName) {
        name = inName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date inCreationDate) {
        creationDate = inCreationDate;
    }

    public Set<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(final Set<Shape> inShapes) {
        shapes = inShapes;
    }
}
