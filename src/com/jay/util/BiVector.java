// package
package com.jay.util;

// imports
import java.io.Serializable;
import java.util.Vector;

/**
 * Implements a pair of synchronized vectors for storring a list of
 * pairs of objects.
 *
 * @author Richard Pito
 * @version 0.1
 */
public class BiVector implements Serializable, Cloneable {
    // History 1 : Below 1 line is added by dragon to sustain the compatibility with previous version.
    static final long serialVersionUID = 3027127750808558089L;

    private Vector mAvector = null;
    private Vector mBvector = null;

    public BiVector() {
        mAvector = new Vector();
        mBvector = new Vector();
    }

    public BiVector(int i) {
        mAvector = new Vector(i);
        mBvector = new Vector(i);
    }

    public Vector getVector_1() {
        return mAvector;
    }

    public Vector getVector_2() {
        return mBvector;
    }

    public synchronized void addElements(Object a, Object b) {
        mAvector.addElement(a);
        mBvector.addElement(b);
    }

    public synchronized Object clone() {
        try {
            BiVector v = (BiVector)super.clone();
            v.mAvector = (Vector)mAvector.clone();
            v.mBvector = (Vector)mBvector.clone();
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    public Object elementAt_1(int i) {
        return mAvector.elementAt(i);
    }

    public Object elementAt_2(int i) {
        return mBvector.elementAt(i);
    }

    public int indexOf_1(Object o) {
        return mAvector.indexOf(o);
    }

    public int indexOf_2(Object o) {
        return mBvector.indexOf(o);
    }

    /**
     * Gets two objects
     *
     * @author Richard Pito
     * @return a vector containing two objects
     */
    public Vector elementAt(int i) {
        Vector ret = new Vector();
        ret.addElement(mAvector.elementAt(i));
        ret.addElement(mBvector.elementAt(i));
        return ret;
    }

    /**
     * Gets two enumerations
     *
     * @author Richard Pito
     * @return a vector containing two enumerations; the first one is an
     *         enumeration of vector 1, the second of vector 2.
     */
    public Vector elements() {
        Vector ret = new Vector();
        ret.addElement(mAvector.elements());
        ret.addElement(mBvector.elements());
        return ret;
    }

    public boolean isEmpty() {
        return mAvector.isEmpty();
    }

    public boolean contains_1(Object o) {
        return mAvector.contains(o);
    }

    public boolean contains_2(Object o) {
        return mBvector.contains(o);
    }

    public void removeElementAt(int i) {
        mAvector.removeElementAt(i);
        mBvector.removeElementAt(i);
    }

    public void removeAllElements() {
        mAvector.removeAllElements();
        mBvector.removeAllElements();
    }

    public void setElementsAt(Object a, Object b, int i) {
        mAvector.setElementAt(a, i);
        mBvector.setElementAt(b, i);
    }

    public int size() {
        return mAvector.size();
    }

    public String toString() {
        return "BIVECTOR:part 1: " + mAvector.toString() + "\n" +
                    "BIVECTOR:part 2: " + mBvector.toString() + "\n";
    }
}
