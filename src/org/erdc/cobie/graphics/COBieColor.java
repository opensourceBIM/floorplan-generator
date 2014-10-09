package org.erdc.cobie.graphics;

import java.awt.Color;

import javax.vecmath.Color3f;

import org.erdc.cobie.graphics.string.Default;

/**
 * Enumerates colors used to render COBie objects.
 */
public enum COBieColor
{
	ALICE_BLUE(new Color3f(0.94f, 0.97f, 1.0f)), 
	BLACK(new Color3f(Color.BLACK)), 
	BLUE(new Color3f(0.40f, 0.60f, 0.80f)), 
	BROWN_NOSE(new Color3f(0.40f, 0.27f, 0.14f)), 
	CYAN(new Color3f(Color.CYAN)), 
	DARK_ORANGE(new Color3f(1.0f, 0.55f, 0)), 
	GRAY(new Color3f(0.76f, 0.76f, 0.76f)), 
	GREEN(new Color3f(0.0f, 0.5f, 0.0f)), 
	MAGENTA(new Color3f(Color.MAGENTA)), 
	PIGMENT_BLUE(new Color3f(0.20f, 0.20f, 0.60f)), 
	TAN(new Color3f(0.76f, 0.70f, 0.50f)), 
	VERMILION(new Color3f(0.89f, 0.26f, 0.20f)), 
	WHITE(new Color3f(Color.WHITE)), 
	WOOD_BROWN(new Color3f(0.76f, 0.60f, 0.42f)), 
	YELLOW(new Color3f(Color.YELLOW));

	/**
	 * Casts the supplied {@code Color3f} to a COBieColor.
	 * 
	 * @param color	The {@code Color3f} to cast.
	 * @return		The cast COBieColor.
	 * @throws 		An {@link IllegalArgumentException} if the supplied {@code Color3f} does not have a matching COBieColor.
	 */
	public static COBieColor cast(Color3f color)
	{
		COBieColor matchingColor = null;

		if (colors == null)
		{
			colors = COBieColor.values();
		}

		for (COBieColor cobieColor : colors)
		{
			if (cobieColor.toColor3f().equals(color))
			{
				matchingColor = cobieColor;
				break;
			}
		}

		if (matchingColor == null)
		{
			throw new IllegalArgumentException(Default.COLOR_CAST_FAILED.toString());
		}

		return matchingColor;
	}

	private final static String toHexValue(int value)
	{
		String hex = Integer.toHexString(value & 0xff);

		while (hex.length() < 2)
		{
			hex += Default.HEX_COLOR_EMPTY_DIGIT.toString();
		}

		return hex;
	}

	private final Color3f color;
	private static COBieColor[] colors = null;

	private COBieColor(Color3f color)
	{
		this.color = color;
	}

	/**
	 * Gets this COBieColor as a {@code Color3f}.
	 * 
	 * @return The matching {@code Color3f}.
	 */
	public final Color3f toColor3f()
	{
		return color;
	}

	/**
	 * Gets this COBieColor as a hex value.
	 * 
	 * @return A {@code String} representing a hex color value matching this COBieColor.
	 */
	public final String toHexString()
	{
		Color3f color3f = color;
		Color color = new Color(color3f.x, color3f.y, color3f.z);

		String r = toHexValue(color.getRed());
		String g = toHexValue(color.getGreen());
		String b = toHexValue(color.getBlue());

		return Default.HEX_COLOR_PREFIX.toString() + r + g + b;
	}
}
