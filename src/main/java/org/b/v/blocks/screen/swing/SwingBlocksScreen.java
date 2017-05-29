package org.b.v.blocks.screen.swing;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.b.v.blocks.core.Blocks;
import org.b.v.blocks.core.Matrix;
import org.b.v.blocks.core.Orientation;
import org.b.v.blocks.core.Position;
import org.b.v.blocks.protocol.Protocol;
import org.b.v.blocks.protocol.net.remote.IpMessageTransformer;
import org.b.v.blocks.protocol.net.remote.RemoteBus;
import org.b.v.blocks.screen.BlockPainter;

public class SwingBlocksScreen extends JFrame implements BlockPainter {

	private static final long serialVersionUID = 1L;

	public SwingBlocksScreen(String string) {
		super(string);
	}
	
	public static void parseMessage(Blocks matrix, String line) {
		String[] tokens = line.split(";");
		for (String token : tokens) {
			System.out.println(token);
		}
		String id = tokens[1];
		String other = tokens[2];
		Orientation orientation = Orientation.valueOf(tokens[3]);
		matrix.addBlockRelationShip(id, orientation, other);
		
		frame.setContentPane(new JPanel());
		frame.getContentPane().setLayout(null);

		matrix.drawBlocks(frame);
	}
		
	

	private static class SwingBlock {
		private JButton button;

		SwingBlock(final String id) {
			this.button = new JButton("");
			this.button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog dialog = new JDialog();
					dialog.setSize(new Dimension(200, 200));
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
				Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						IpMessageTransformer transformer = new IpMessageTransformer();
						RemoteBus bus = new RemoteBus(8081,8082,"localhost").withTransformer(transformer);
						Protocol protocol = new Protocol(bus);
						Matrix<String> matrix = protocol.run();
						for(Map.Entry<Position,String> entry:matrix.getAllPositions().entrySet()) {
							frame.drawBlockAtPosition(threeLast(entry.getValue()),entry.getKey());
						}
						bus.close();
						return null;
					}
					
				});
			}
		});

		menu.add(addCategory);
		menuBar.add(menu);
	}

	private static SwingBlocksScreen frame;

	private static void drawBlock(String id, int x, int y, int width, int height) {
		SwingBlock block = new SwingBlock(id);
		frame.getContentPane().add(block.button());
		Insets insets = frame.getInsets();
		block.button().setBounds(x + insets.left, y + insets.top, width, height);
		System.out.println(id);
		block.button().setText(id);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new SwingBlocksScreen("Blocks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		frame.getContentPane().setLayout(null);

		// Size and display the window.
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		// frame.setSize(300 + insets.left + insets.right,
		// 125 + insets.top + insets.bottom);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		initializeTheMenu(menuBar);

		frame.setVisible(true);
	}
	
	private static String threeLast(String ip) {
		return ip.substring(ip.length() -1, ip.length());
	}

	public void drawBlockAtPosition(String id, Position position) {
		int sizeOfBlock = 50;
		drawBlock(id, 25 + (position.getX() * sizeOfBlock), 25 + position.getY() * sizeOfBlock, sizeOfBlock,
				sizeOfBlock);
	}

	public static void main(String[] args) throws IOException {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

//		ExecutorService service = Executors.newFixedThreadPool(3);
//		service.execute(new TcpBlocksServer(8080,matrix));
//		service.execute(new UdpBlocksServer(8081,matrix));

//		IpMessageTransformer transformer = new IpMessageTransformer();
//		RemoteBus bus = new RemoteBus(8081,8082,"localhost").withTransformer(transformer);
//		Protocol protocol = new Protocol(bus);
//		Matrix<String> matrix = protocol.run();
//		for(Map.Entry<Position,String> entry:matrix.getAllPositions().entrySet()) {
//			frame.drawBlockAtPosition(threeLast(entry.getValue()),entry.getKey());
//		}
//		System.out.println("finished");
	}


}
