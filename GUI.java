/*
 * Copyright (c) 2020, 2021, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package prototype;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Font;

/**
 * The class is used to demonstrate the SONAR functionality. To do so, the
 * built-in GUI is used to visualize the data fetched from the Serial-port.
 * <p>
 * To have a better understanding of this class & Serial.class, please have a
 * look at the JavaDoc of jSerialcomm at com.fazecast.jSerialComm.
 * </p>
 *
 * @author Wahab Meskinyar
 * @see UI.java;
 * @see jSerialComm;
 * @version 1.0
 * @since 12.27.2020
 */
public class GUI extends JComponent implements ActionListener, UI {

    private static final long serialVersionUID = 1L;

    private Timer timer = new Timer(2, this);

    private int x, y, distX = 10, distY = 30, angleX = 10, angleY = 70;
    private boolean swing;
    private double angle = 1d, frequency = 0.8d;
    private String dist = "Distance: ", angl = "Angle: ";
    private double CONSTANT_DIST_PX = 2d;
    private int cm100 = 200, cm200 = 400;
    private boolean objectFound = false;
    private int objectPositionX, objectPositionY;

    public GUI() {
        x = getWidth() / 2;
        y = getHeight();
        setOpaque(true);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawString(g);
        Graphics2D g2d = (Graphics2D) g;
        motionBlur(g2d);
        g2d.setColor(setColor(0, 200, 3));
        drawRadar(g2d);
        drawScanner(g2d);
        detection(g2d);

    }

    @Override
    public void drawRadar(Graphics2D g2d) {
        int offset = getWidth() / 2;
        g2d.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, 0);
        g2d.setStroke(new BasicStroke(3.1f));
        g2d.drawOval(getWidth() / 2 - (cm100), getWidth() / 2 - (cm100), cm100 * 2, cm100 * 2);
        g2d.drawOval(getWidth() / 2 - cm200, getWidth() / 2 - cm200, cm200 * 2, cm200 * 2);
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawOval(getWidth() / 2 - (offset), ((getWidth())) / 2 - (offset), getWidth(), getWidth());
    }

    @Override
    public void motionBlur(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 26, 25));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void drawString(Graphics g) {
        g.setFont(new Font("Arial", 10, 19));
        g.setColor(Color.white);
        g.drawString(dist + distance(), distX, distY);
        g.drawString(angl + Math.ceil(angle) + " °", angleX, angleY);
        g.setFont(new Font("Arial", 1, 15));
        g.drawString("100 cm", (getWidth() - 40) / 2, 410);
        g.drawString("200 cm", (getWidth() - 40) / 2, 210);
        g.drawString("300 cm", (getWidth() - 40) / 2, 15);
        g.setFont(new Font("Arial", 5, 9));
        g.drawString("Copyright (c) 1994, 2020. Facharbeit von Wahab Msk.", 950, 550);
    }

    private void setScannerLocation(int offsetX, int offsetY) {
        x = (getHeight()) - (int) ((getWidth() - offsetX) * Math.cos(Math.toRadians(angle)));
        y = (getHeight()) - (int) ((getHeight() - offsetY) * Math.sin(Math.toRadians(angle)));
    }

    @Override
    public void drawScanner(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(5f));
        if (objectFound) {
            g2d.drawLine(getWidth() / 2, getHeight(), objectPositionX + 15, objectPositionY + 15);
        } else {
            g2d.drawLine(getWidth() / 2, getHeight(), x, y);
        }

    }

    @Override
    public void detection(Graphics2D g2d) {
        if (!distance().equals("0") && calcPX() != 0) {
            objectFound = true;
            g2d.setStroke(new BasicStroke(5f));
            objectPositionX = getHeight() - (int) (calcPX() * Math.cos(Math.toRadians(angle)));
            objectPositionY = getHeight() - (int) (calcPX() * Math.sin(Math.toRadians(angle)));
            g2d.setColor(new Color(180, 0, 0, 250));
            g2d.fillOval(objectPositionX - 10 / 2, objectPositionY - 10 / 2, 10, 10);
            g2d.setStroke(new BasicStroke(1f));
            g2d.drawRect(objectPositionX - 10, objectPositionY - 10, 20, 20);

        } else {
            objectFound = false;
        }
    }

    @Override
    public Color setColor(int r, int g, int b) {
        return new Color(r, g, b);
    }

    private int calcPX() {
        double distancePX = 0;
        if (Serial.getDistance() <= 300 && Serial.getDistance() >= 1) {
            distancePX = (int) (Serial.getDistance() * CONSTANT_DIST_PX);
            return (int) distancePX;
        }
        return (int) distancePX;
    }

// <editor-fold defaultstate="collapsed" desc="Protected Code">   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (angle < 90) {
            setScannerLocation(630, 7);
        } else if (angle > 120) {
            setScannerLocation(561, 0);
        } else {
            setScannerLocation(550, 0);
        }
        angle = rotationCheck() ? angle += frequency : angle - frequency;

        repaint();

    }

// <editor-fold defaultstate="collapsed" desc="Protected Code">   
    private boolean rotationCheck() {
        if (angle <= 1) {
            swing = true;
        } else if (angle >= 180) {
            swing = false;
        }
        return swing;
    }

    private String distance() {
        return Serial.getDistance() <= 300 && Serial.getDistance() >= 1 ? Serial.getDistance() + "" : "0";
    }
}
