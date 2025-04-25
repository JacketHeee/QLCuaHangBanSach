package GUI.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.geom.RoundRectangle2D;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.RenderingHints;

import javax.swing.border.EmptyBorder;

public class LabelTongQuan extends JPanel{

    private String text; 
    private String icon; 
    private String count;
    private String background;
    private int cornerRadius = 15; // độ bo góc
    private JLabel labelTongQuan;
    
    public LabelTongQuan(String text,String icon, String count,String background) {
        this.text = text;
        this.icon = icon; 
        this.count = count;
        this.background = background;
        init();
        setOpaque(false);
    }

    private void init() {
        setLayout(new MigLayout("al center center,gap 10"));
        setBackground(Color.decode(background));
        setBorder(new EmptyBorder(10,10,10,10));
        add(new JLabel(new FlatSVGIcon(LabelInfor.class.getResource("../../resources/img/icon/"+icon)).derive(50, 50)));
        JPanel panel = new JPanel(new MigLayout("al center center"));
        panel.setOpaque(false);
        labelTongQuan = new JLabel(String.format("<html><b><font color='white' size='+2'>%s</font></b></html>", count));
        panel.add(labelTongQuan,"al center,pushx,wrap");
        panel.add(new JLabel(String.format("<html><b><font color='white' size='+0'>%s</font></b></html>", text)),"al center,pushx,gaptop 10");
        
        add(panel,"push,grow");
    }
    @Override   
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // tô nền bo góc
        Shape round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.setColor(getBackground());
        g2.fill(round);
        g2.dispose();

        super.paintComponent(g);
    }

    private void refresh() {
        this.repaint();
        this.revalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        refresh();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        refresh();
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.labelTongQuan.setText(String.format("<html><b><font color='white' size='+2'>%s</font></b></html>", count));
    }
 
    
}
