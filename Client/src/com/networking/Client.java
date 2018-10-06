package com.networking;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Trivial client for the date server.
 */
public class Client extends JFrame {

    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
	
	private String server_address;
	private Socket s;
	private PrintWriter out;
	
    public static void main(String[] args) {    	
    	EventQueue.invokeLater(() -> {
            JFrame ex = new Client();
            ex.setVisible(true);
        });
    }
    
    public Client() {
    	server_address = JOptionPane.showInputDialog("What is the message you want to send?\n");
    	
    	try {
			s = new Socket(server_address, 9090);
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	addKeyListener(new TAdapter());
    	
    	addWindowListener(new WindowAdapter() {
    		@Override
    		public void windowClosing(WindowEvent e) {
    			try {
    				System.out.println("Closed socket");
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
    }
    
    private void sendMessage(String command) {
	    out.println(command);
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                sendMessage("left");
            }

            if (key == KeyEvent.VK_RIGHT) {
                sendMessage("right");
            }

            if (key == KeyEvent.VK_UP) {
                sendMessage("up");
            }

            if (key == KeyEvent.VK_DOWN) {
                sendMessage("down");
            }
            
            if (key == KeyEvent.VK_SPACE) {
                sendMessage("space");
            }
        }
    }
}