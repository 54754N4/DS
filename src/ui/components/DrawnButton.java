package ui.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

public abstract class DrawnButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 6824926734152612241L;
	protected final JTabbedPane pane;
	
    public DrawnButton(JTabbedPane pane, String tooltip) {
    	this.pane = pane;
        int size = 17;
        setPreferredSize(new Dimension(size, size));
        setToolTipText(tooltip);
        setUI(new BasicButtonUI());		//Make the button looks the same for all Laf's
        setContentAreaFilled(false);	//Make it transparent
        setFocusable(false);			//No need to be focusable
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        addMouseListener(buttonMouseListener);	//rollover effect (same listener for all buttons)
        setRolloverEnabled(true);
        addActionListener(this);		//Close the proper tab by clicking the button
    }

    protected abstract void draw(Graphics2D g);
    @Override public void updateUI() {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        //shift the image for pressed buttons
        if (getModel().isPressed())
            g2.translate(1, 1);
        draw(g2);
        g2.dispose();
    }
	
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}