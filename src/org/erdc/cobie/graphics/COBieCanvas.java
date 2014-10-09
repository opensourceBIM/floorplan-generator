package org.erdc.cobie.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * COBieCanvas represents a renderable screen area upon which COBie objects are rendered.
 */
public interface COBieCanvas extends SettingsUser
{
	/**
	 * Gets this COBieCanvas' center location.
	 * 
	 * @return A {@code Dimension} describing this COBieCanvas' center location.
	 */
    Dimension getCenter();
    
    /**
     * Gets this COBieCanvas' dimensions.
     * 
     * @return A {@code Dimension} describing this COBieCanvas' dimensions.
     */
    Dimension getDimensions();
    
    /**
     * Writes this COBieCanvas' data to a {@code BufferedImage}.
     * 
     * @return A {@code BufferedImage} containing this COBieCanvas' data.
     */
    BufferedImage write();
}
