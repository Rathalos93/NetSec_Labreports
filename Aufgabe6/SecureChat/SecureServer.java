//package javaLanguage.basic.socket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class SecureServer extends JFrame
{

	JLabel label = new JLabel("Text received over socket:");
	JPanel panel;
	JTextArea textArea = new JTextArea();
	ServerSocket server = null;

	SecureServer()
	{
		//Begin Constructor
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		getContentPane().add(panel);
		panel.add("North", label);
		panel.add("Center", textArea);
	} //End Constructor

	public void listenSocket()
	{

		try
		{
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			server = (SSLServerSocket) factory.createServerSocket(4444);
		}
		catch (IOException e)
		{
			System.out.println("Could not listen on port 4444");
			System.exit(-1);
		}
		while (true)
		{
			ClientWorker w;
			try
			{
				w = new ClientWorker((SSLSocket) server.accept(), textArea);
				Thread t = new Thread(w);
				t.start();
			}
			catch (IOException e)
			{
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
		}
	}

	protected void finalize()
	{
		//Objects created in run method are finalized when
		//program terminates and thread exits
		try
		{
			server.close();
		}
		catch (IOException e)
		{
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	public static void main(String[] args)
	{
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		SecureServer frame = new SecureServer();
		frame.setTitle("Server Program");
		WindowListener l = new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		};
		frame.addWindowListener(l);
		frame.pack();
		frame.setVisible(true);
		frame.listenSocket();
	}
}
