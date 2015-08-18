package org.bimserver.cobie.graphics;

/**
 * This interface defines a common way to handle 2D/3D functionality in engine-related classes.
 * 
 * @author RDITLNBM
 *
 * @param <T2D> Specifies the implementation of {@code For2D} that the implementer of this interface supports.
 * @param <T3D> Specifies the implementation of {@code For3D} that the implementer of this interface supports.
 */
public interface Selector<T2D extends For2D, T3D extends For3D>
{
    T2D for2D();
    T3D for3D();
}
