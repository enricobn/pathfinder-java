/*******************************************************************************
 * Copyright (c) 2003-2012 Enrico Benedetti.
 * 
 * This file is part of Capa chess.
 * 
 * Capa chess is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Capa chess is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Capa chess.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jpathfinder.gl;

import java.awt.Color;

import javax.media.opengl.GL;

/**
 * @author Enrico Benedetti
 *
 */
public class GLColor {
    public static final GLColor WHITE = new GLColor(Color.WHITE, 0);
    public static final GLColor BLACK = new GLColor(Color.BLACK, 0);
    public static final GLColor BLUE = new GLColor(0f, 0f, 1f, 0);
    public static final GLColor CYAN = new GLColor(0.7f, 0.7f, 1.0f, 0);
    public static final GLColor BROWN = new GLColor(71, 40, 7, 0);
    public static final GLColor RED = new GLColor(1f, 0f, 0f, 0);
//    public static final GLColor IVORY = new GLColor(253, 247, 195, 0);
    public static final GLColor IVORY = new GLColor(233, 228, 182, 0);

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    
    public GLColor(final float red, final float green, final float blue, 
            final float alpha) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public GLColor(final Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
    }
    
    public GLColor(final Color color, final float alpha) {
        this(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public GLColor(final int red, final int green, final int blue, 
            final int alpha) {
        this.red = (float) red / 255f;
        this.green = (float) green / 255f;
        this.blue = (float) blue / 255f;
        this.alpha = (float) alpha / 255f;
    }
    
    public GLColor(final int red, final int green, final int blue) {
        this(red, green, blue, 255);
    }

    public void render(final GL gl) {
        gl.glColor4f(red, green, blue, alpha);
    }
    
    public GLColor withAlpha(final float alpha) {
        return new GLColor(red, green, blue, alpha);
    }
    
    float FACTOR = 0.8f;
    public GLColor darker() {
        return new GLColor(red * FACTOR, green * FACTOR, blue * FACTOR, alpha);
    }

    public GLColor brighter() {
        return new GLColor(Math.min(red / FACTOR, 1), Math.min(green / FACTOR, 1), Math.min(blue / FACTOR, 1), alpha);
    }
    
}
