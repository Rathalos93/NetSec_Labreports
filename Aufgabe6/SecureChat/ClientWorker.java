import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.swing.JTextArea;

class ClientWorker implements Runnable
{
	private SSLSocket client;
	private JTextArea textArea;

	ClientWorker(SSLSocket client, JTextArea textArea)
	{
		this.client = client;
		this.textArea = textArea;
	}

	public boolean verifyUser()
	{
		PrintWriter out = null;
		{
			try
			{
				out = new PrintWriter(client.getOutputStream(), true);
			}
			catch (IOException e)
			{
				System.out.println("in or out failed");
				System.exit(-1);
			}
		}
		out.println("Successfully logged in!");
		return true;
	}


	public void run()
	{
		String line;
		BufferedReader in = null;
		PrintWriter out = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		}
		catch (IOException e)
		{
			System.out.println("in or out failed");
			System.exit(-1);
		}

		if (this.verifyUser())
		{
			MessageManager.registerClient(client);
			while (true)
			{
				try
				{
					line = in.readLine();
					//Send data back to client
					MessageManager.sendMessage(line);
					textArea.append(line + "\n");
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("Read failed");
					System.exit(-1);
				}
			}
		}
	}
}