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
	
	private static JFrame frame;
	

	private BlockRelation firstBlockRelation;
	
	enum Orientation {
		NORTH,SOUTH,EAST,WEST
	}
	
	private class BlockRelation {
		private int id;
		private BlockRelation south,east;
		
		public BlockRelation(int id) {
			this.id = id;
		}
		
		public BlockRelation posBlock(int id, Orientation orientation, int otherId) {
			if(this.id==otherId) {
				if(orientation==Orientation.EAST) {
					this.east=new BlockRelation(id);
				}

				if(orientation==Orientation.SOUTH) {
					this.south=new BlockRelation(id);
				}
				
				return null;
			} else {
				if(this.south!=null) {
					this.south = this.south.posBlock(id, orientation, otherId);
				}
				if(this.east!=null) {
					this.east = this.east.posBlock(id, orientation, otherId);
				}
				return this;
			}
		}
		
		

	}
	
	private  void placeFirstBlock(int id) {
		firstBlockRelation = new BlockRelation();
		firstBlockRelation.id = id;
	}
	
	private void setBlock(int id,Orientation orientation, int otherId) {
		//principe van een (enkele) linked list...
		//top is noord-west
		//navigeren tot dat je overeenkomst vindt...
		//als het ten noorden is van de block anders recursief werken
		
		if(firstBlockRelation.id == otherId 
				&&  (orientation == Orientation.WEST
				  || orientation == Orientation.EAST)) {
			//swap
		} else {
			firstBlockRelation.posBlock(id,orientation,otherId);
		}
	}
	
    private static void drawBlock(int id,int x,int y,int width,int height) {
    	SwingBlock block = new SwingBlock(id);
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
