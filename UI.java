/*
 * Copyright (c) 1994, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package prototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Methods provided for orientation of programming
 *
 * @author Wahab Meskinyar
 * @version 1.0
 * @since 12.27.2020
 */

public interface UI {

    public abstract void drawString(Graphics g);

    public abstract void motionBlur(Graphics2D g2d);

    public abstract void drawRadar(Graphics2D g2d);

    public abstract void drawScanner(Graphics2D g2d);

    public abstract void detection(Graphics2D g2d);

    public abstract Color setColor(int r, int g, int b);

}
