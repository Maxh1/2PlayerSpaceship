package AlienGame;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;


public class CollisionEx extends JFrame {
	Board board;
	public CollisionEx() {

		initUI();
	}

	private void initUI() {
		board=new Board();
		add(board);

		setResizable(false);
		pack();

		setTitle("Collision");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws IOException {
		
		CollisionEx ex = new CollisionEx();
		ex.setVisible(true);

		ServerSocket listener = new ServerSocket(9090);
		try {
			while (true) {

				Socket socket = listener.accept();

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					ex.board.sendCommand(inputLine);
				}
				socket.close();
			}
		} finally {
			listener.close();
		}
	}
}
