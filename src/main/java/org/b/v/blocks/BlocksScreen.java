package org.b.v.blocks;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;
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

public class BlocksScreen extends JFrame implements BlockPainter {

	private static final long serialVersionUID = 1L;

	public BlocksScreen(String string) {
		super(string);
	}
	
	private static Blocks matrix = new Blocks();

	private static final class TcpBlocksServer implements Runnable {
		
		private int port;

		public TcpBlocksServer(int port) {
			this.port = 8080;
		}
		
		@Override
		public void run() {
			try {
				ServerSocket server = new ServerSocket(this.port);
				while (true) {
					Socket socket = server.accept();
					socket.getInputStream();
					Scanner in = new Scanner(socket.getInputStream());
					
					while (in.hasNextLine()) {
						String line = in.nextLine();
						parseMessage(matrix, line);
					}
					in.close();
					server.close();
				}

			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class UdpBlocksServer implements Runnable {

		private int port;
		private DatagramSocket socket;

		UdpBlocksServer(int port) {
			this.port = port;
		}
		
		@Override
		public void run() {
			try {
				byte[] buf = new byte[256];
				socket = new DatagramSocket(port);
				while(true) {
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					System.out.println("lenght: " + packet.getLength());
					parseMessage(matrix,new String(Arrays.copyOfRange(packet.getData(), 0, packet.getLength())));
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	}

	private static class SwingBlock {
		private JButton button;

		SwingBlock(final int id) {
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
				// TODO ind
			}
		});

		menu.add(addCategory);
		menuBar.add(menu);
	}

	private static BlocksScreen frame;

	private static void drawBlock(int id, int x, int y, int width, int height) {
		SwingBlock block = new SwingBlock(id);
		frame.getContentPane().add(block.button());
		Insets insets = frame.getInsets();
		block.button().setBounds(x + insets.left, y + insets.top, width, height);
		block.button().setText("" + id);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new BlocksScreen("Blocks");
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

	public void drawBlockAtPosition(int id, Position position) {
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

		ExecutorService service = Executors.newFixedThreadPool(2);
		service.execute(new TcpBlocksServer(8080));
		service.execute(new UdpBlocksServer(8081));
	}

	private static void parseMessage(Blocks matrix, String line) {
		String[] tokens = line.split(";");
		for (String token : tokens) {
			System.out.println(token);
		}
		int id = Integer.parseInt(tokens[1]);
		int other = Integer.parseInt(tokens[2]);
		Orientation orientation = Orientation.valueOf(tokens[3]);
		matrix.addBlockRelationShip(id, orientation, other);

		frame.setContentPane(new JPanel());
		frame.getContentPane().setLayout(null);

		matrix.drawBlocks(frame);
	}
}
