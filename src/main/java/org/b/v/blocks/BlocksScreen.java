package org.b.v.blocks;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BlocksScreen {
	
	private static class Block {
		private JButton button;
		
		Block(final int id) {
			this.button = new JButton("");
			this.button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog dialog = new JDialog();
					dialog.setSize(new Dimension(200,200));
					dialog.add(new JLabel("TODO: a menu is to be used here to configue the block"));
					dialog.setVisible(true);
					System.out.println("hello" + id);
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
	
	private static List<Block> buttons = new ArrayList<Block>();
	private static Map<Integer,Block> blocks = new TreeMap<Integer,Block>();
	
	private static JFrame frame;
	
    private static void drawBlock(int id,int x,int y,int width,int height) {
    	Block block = new Block(id);
    	frame.getContentPane().add(block.button());
    	positionBlock(block, x, y, height, width);
    }
    
	private static void positionBlock(Block b2, int x,int y,int width,int heigth) {
		Insets insets = frame.getInsets();
        b2.button().setBounds(x + insets.left, y + insets.top,width, heigth);
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Blocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.getContentPane().setLayout(null);

        int sizeOfBlock = 50;
        
        for(int i=0;i<8;i++) {
        	drawBlock(i,25 + (i * sizeOfBlock), 5, sizeOfBlock, sizeOfBlock);
        }

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
            }
        });
    }
}
