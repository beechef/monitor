/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.GUI.Lib;

/**
 *
 * @author Admin
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;

public class RoundBorder implements Border {

    private int radius;
    private Color backgroundColor;

    public RoundBorder(int radius) {
        this.radius = radius;
    }

    public RoundBorder(int radius, Color backgroundColor) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;
    }

//public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//    Graphics2D g2 = (Graphics2D) g.create();
//    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//    Shape border = new RoundRectangle2D.Double(x, y, width-1, height-1, radius, radius);
//    if(this.backgroundColor!=null){
//        g2.setColor(backgroundColor);
//    }else{
//        g2.setColor(Color.WHITE);
//
//    }
//    g2.fill(border);
//    g2.setColor(Color.BLACK);
//    g2.draw(border);
//    g2.dispose();
//}
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Dimension arcs = new Dimension(this.radius, this.radius);

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
        } else {
            graphics.setColor(Color.CYAN);
        }
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); //paint background
        graphics.setColor(Color.BLACK);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); //paint border
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    public boolean isBorderOpaque() {
        return false;
    }
}
