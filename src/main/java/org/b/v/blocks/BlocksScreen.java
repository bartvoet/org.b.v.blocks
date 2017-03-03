package org.b.v.blocks;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BlocksScreen extends JFrame implements BlockPainter {
	
	
	private Blocks matrix = new Blocks();
	
	public BlocksScreen(String string) {
		super(string);
	}
	
	private static class SwingBlock {
		private JButton button;
		
		SwingBlock(final int id) {
			this.button = new JButton("");
			this.button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog dialog = new JDialog();
					dialog.setSize(new Dimension(200,200));
					dialog.add(new JLabel("TODO: A menu is to be used here to configue the block"));
					dialog.setVisible(true);
				}
			});
		}
		
		public JButton button() {
			return button;
		}
		
	}

	private static void initializeTheMenu(JMenuBar menuBar) {
		JMenu menu = new JMenu("Acties");
		menu.setMnemonic(KeyEvent.VK_A);
		
		JMenuItem addCategory = new JMenuItem("Connecteer setup");
		addCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO ind
			}
		});
		
		menu.add(addCategory);
		menuBar.add(menu);
	}
	
	private static BlocksScreen frame;
	

	
    private static void drawBlock(int id,int x,int y,int width,int height) {
    	SwingBlock block = new SwingBlock(id);
    	block.button().setText("" + id);
    	frame.getContentPane().add(block.button());
    	Insets insets = frame.getInsets();
    	block.button().setBounds(x + insets.left, y + insets.top,width, height);
    }
    

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new BlocksScreen("Blocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.getContentPane().setLayout(null);

        //Size and display the window.
        Insets insets = frame.getInsets();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        frame.setSize(300 + insets.left + insets.right,
//                      125 + insets.top + insets.bottom);
		JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        initializeTheMenu(menuBar);
        
        frame.setVisible(true);
    }
    


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
        		Blocks matrix = new Blocks();
        		matrix.addBlock(1, Orientation.EAST,2);
        		matrix.addBlock(2, Orientation.SOUTH,3);
        		matrix.addBlock(4, Orientation.NORTH,3);
        		matrix.addBlock(5, Orientation.WEST,4);
        		matrix.addBlock(1, Orientation.WEST,6);
        		matrix.drawBlocks(frame);
            }
        });
    }

    private Map<Integer,Position> blocks=new TreeMap<Integer,Position>();
    
    public void drawBlockAtPosition(int id,Position position) {
    	int sizeOfBlock = 50;
    	drawBlock(id,25 + (position.getX() * sizeOfBlock), 25 + position.getY() * sizeOfBlock, sizeOfBlock, sizeOfBlock);
    }
    
    

}
