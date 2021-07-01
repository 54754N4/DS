package ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

public class CloseButton extends DrawnButton {
	private static final long serialVersionUID = -2239459860056387167L;
	private final TabComponent parent;
	
	public CloseButton(final JTabbedPane pane, final TabComponent parent) {
		super(pane, "Close");
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {
        int i = pane.indexOfTabComponent(parent);
        if (i != -1)
            pane.remove(i);
    }

	@Override
	protected void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        if (getModel().isRollover())
            g.setColor(Color.RED);
        int delta = 6;
        g.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
        g.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
	}
}