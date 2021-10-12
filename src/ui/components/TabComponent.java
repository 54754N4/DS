package ui.components;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabComponent extends JPanel {
	private static final long serialVersionUID = 8476576673933552769L;
	public static final int BUTTON_OFFSET = 5,			// offset button from text
			TOP_OFFSET = 2;								// top offset
	
	private JTabbedPane pane;
	
	public TabComponent(final JTabbedPane pane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));	// remove default gaps
		this.pane = pane;
		setOpaque(false);
		add(createTiedLabel());
		add(new CloseButton(pane, this));
		setBorder(BorderFactory.createEmptyBorder(TOP_OFFSET, 0, 0, 0));
	}
	
	private JLabel createTiedLabel() {
		JLabel label = new JLabel() {
			private static final long serialVersionUID = -5089596734516413483L;

			@Override
			public String getText() {	// get tab title directly
				int i = pane.indexOfTabComponent(TabComponent.this);
				return i != -1 ? pane.getTitleAt(i) : null;
			}
		};
		label.setBorder(BorderFactory.createEmptyBorder(0,0,0,BUTTON_OFFSET));
		return label;
	}
}