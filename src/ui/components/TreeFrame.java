package ui.components;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.swing.InputMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ads.contracts.Tree;

/**
 * Visualises any trees of type K in different tabs.
 * 
 * Refs.:
 * - Tabbed panel Tab traversal: https://www.davidc.net/programming/java/how-make-ctrl-tab-switch-tabs-jtabbedpane
 */
public class TreeFrame<K> extends JFrame implements ChangeListener {
	private static final long serialVersionUID = 2747234557180885310L;
	private final JTabbedPane pane;
	
	private TreeFrame(int width, int dy, Function<K, String> converter, Tree<K>[] trees) {
		super("Trees Visualiser");
		add(pane = createTabs(width, dy, converter, trees));
		setJMenuBar(new Menu(pane));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JTabbedPane createTabs(int width, int dy, Function<K, String> converter, Tree<K>[] trees) {
		JTabbedPane pane = new JTabbedPane();
		pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		for (int i=0; i<trees.length; i++) {
			pane.add("Tree "+(i+1), new ScrollTreePanel<>(width, dy, converter, trees[i]));
			pane.setTabComponentAt(i, new TabComponent(pane));
		}
		setupTabTraversalKeys(pane);
		pane.addChangeListener(this);
		return pane;
	}
	
	 private static void setupTabTraversalKeys(JTabbedPane tabbedPane) {
	    KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB"),
	    		ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");
	    // Remove ctrl-tab from normal focus traversal
	    Set<AWTKeyStroke> forwardKeys = new HashSet<>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
	    forwardKeys.remove(ctrlTab);
	    tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
	    // Remove ctrl-shift-tab from normal focus traversal
	    Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
	    backwardKeys.remove(ctrlShiftTab);
	    tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
	    // Add keys to the tab's input map
	    InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	    inputMap.put(ctrlTab, "navigateNext");
	    inputMap.put(ctrlShiftTab, "navigatePrevious");
	}
	 
	 @Override
	public void stateChanged(ChangeEvent e) {
//		 int tab = pane.getSelectedIndex();
		 
	}
	
	public static class Menu extends JMenuBar {
		private static final long serialVersionUID = -5131755896195964048L;
		private final JMenuItem scroll, exit;
		
		public Menu(final JTabbedPane pane) {
			exit = create("Exit", false, KeyEvent.VK_X, e -> System.exit(0));
			scroll = create("Wrap/Scroll Tabs", true, KeyEvent.VK_S, e -> 
				pane.setTabLayoutPolicy(
					pane.getTabLayoutPolicy() == JTabbedPane.WRAP_TAB_LAYOUT ?
						JTabbedPane.SCROLL_TAB_LAYOUT : 
						JTabbedPane.WRAP_TAB_LAYOUT));
			JMenu menu = new JMenu("Options");
			menu.setMnemonic(KeyEvent.VK_O);
			menu.add(scroll);
			menu.add(exit);
			add(menu);
		}
		
		private JMenuItem create(String name, boolean checkbox, int key, ActionListener listener) {
			Function<String, JMenuItem> ctor = checkbox ? JCheckBoxMenuItem::new : JMenuItem::new;
			JMenuItem item = ctor.apply(name);
			item.setMnemonic(key);
			item.addActionListener(listener);
			return item;
		}
	}
	
	/**
	 *  Implements panning and zooming 
	 **/
	public static class ScrollTreePanel<K> extends JPanel implements MouseWheelListener {
		private static final long serialVersionUID = 6418970698732925061L;
		private static final double X_SCALE = 1.1, Y_SCALE = 1.5;
		private final TreePanel<K> panel;
		private final JScrollPane scroll;
		
		public ScrollTreePanel(int width, int dy, Function<K, String> converter, Tree<K> tree) {
			super(new BorderLayout(), true);
			panel = new TreePanel<>(width, dy, tree, converter);
			scroll = new JScrollPane(panel) {
				private static final long serialVersionUID = 5594881058261083282L;

				@Override
				public Dimension getPreferredSize() {
					return panel.getPreferredSize();
				}
			};
			scroll.addMouseWheelListener(this);
			scroll.getViewport().addChangeListener(e -> scroll.repaint());
			add(scroll, BorderLayout.CENTER);
		}
		
		public TreePanel<K> getTree() {
			return panel;
		}
		
		public JScrollPane getScroll() {
			return scroll;
		}
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.isControlDown() && e.getWheelRotation() != 0) {
				Function<Double, Double> scaler = e.getWheelRotation() > 0 ? e1 -> 1/e1 : e1 -> e1;
				panel.scale(scaler.apply(X_SCALE), scaler.apply(Y_SCALE));
				JViewport vp = scroll.getViewport();
				Point p = vp.getViewPosition();
				p.x += scroll.getHorizontalScrollBar().getValue();
				p.y += scroll.getVerticalScrollBar().getValue();
		        p.x = (int) (X_SCALE*(p.x+e.getX())) - e.getX();
		        p.y = (int) (Y_SCALE*(p.y+e.getY())) - e.getY();
				vp.setViewPosition(p);
				e.consume();
			}
		}
	}
	
	public static class TreePanel<K> extends JPanel {
		private static final long serialVersionUID = 2315957851667986843L;
		private static final Font font = new Font("Verdana", Font.BOLD, 30);
		public final Tree<K> root;
		private Dimension size;
		private int width, dy;
		private Function<K, String> converter;
		
		private TreePanel(int totalWidth, int dy, Tree<K> tree, Function<K, String> converter) {
			super();
			this.width = totalWidth;
			this.dy = dy;
			this.converter = converter;
			root = tree;
			computeSize();
		}
		
		@Override
		public Dimension getPreferredSize() {
			return computeSize();
		}
		
		public Dimension computeSize() {
			return isValid() ? 
					size : 
					(size = new Dimension(width, (root.height()+1)*dy));
		}
		
		public void scale(double kx, double ky) {
			width *= kx;
			dy *= ky;
			invalidate();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			draw(g, 0, 0, root, size.width);
		}
		
		private void draw(Graphics g, int x, int y, Tree<K> node, int width) {
			Rectangle rect = new Rectangle(x, y, width, dy);
			drawCenteredString(g, converter.apply(node.getValue()), rect);
			if (node.isLeaf())
				return;
			List<Tree<K>> children = node.getChildren();
			for (int parentX = x + width/2, parentY = y+dy/2, 
					count = children.size(), 
					childWidth = width/count, 
					childX, i=0; i<count; i++) {
				childX = x+i*childWidth;
				draw(g, childX, y+dy, children.get(i), childWidth);
				g.drawLine(parentX, parentY, childX+childWidth/2, y+dy+dy/2);
			}
		}
		
		// https://stackoverflow.com/a/27740330/3225638
		public void drawCenteredString(Graphics g, String text, Rectangle rect) {
		    FontMetrics metrics = g.getFontMetrics(font);
		    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		    g.setFont(font);
		    g.drawString(text, x, y);
		}
	}
	
	@SafeVarargs
	public static <K> void display(Tree<K>...trees) {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width/2,
			dy = 50;
		display(width, dy, K::toString, trees);
	}
	
	@SafeVarargs
	public static <K> void display(int width, int dy, Function<K, String> converter, Tree<K>...trees) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new TreeFrame<>(width, dy, converter, trees)
					.setVisible(true);
			}
		});
	}
}