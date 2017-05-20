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
    protected String mName;
    @Column(name = "creationDate", nullable = false)
    protected Date mCreationDate;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(
        name = "DrawingShapes",
        joinColumns = {@JoinColumn(name = "drawing_id")},
        inverseJoinColumns = {@JoinColumn(name = "shape_id")})
    protected Set<Shape> mShapes = new HashSet<>();

    /**
     * Adds the supplied shape to the drawing.
     *
     * @param inShape Shape to add to drawing.
     */
    public void addShape(final Shape inShape) {
        mShapes.add(inShape);
    }

    public String getName() {
        return mName;
    }

    public void setName(final String inName) {
        mName = inName;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(final Date inCreationDate) {
        mCreationDate = inCreationDate;
    }

    public Set<Shape> getShapes() {
        return mShapes;
    }

    public void setShapes(final Set<Shape> inShapes) {
        mShapes = inShapes;
    }
}
