//Example 25

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.UnknownHostException;

class SecureClient implements Runnable
{

	// The client socket
	private static SSLSocket clientSocket = null;
	// The output stream
	private static PrintStream os = null;
	// The input stream
	private static BufferedReader is = null;

	private static BufferedReader inputLine = null;
	private static boolean closed = false;

	public static void main(String[] args)
	{
		System.out.println("Main");

		// The default port.
		int portNumber = 4444;
		// The default host.
		String host = "localhost";


    /*
	 * Open a socket on a given host and port. Open input and output streams.
     */
		try
		{
			System.out.println("In try");
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			clientSocket = (SSLSocket) factory.createSocket(host, portNumber);

			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			//is = new DataInputStream(clientSocket.getInputStream());
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("All executed");
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host " + host);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Couldn't get I/O for the connection to the host "
					+ host);
		}

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
		System.out.println(os);
		System.out.println(is);
		System.out.println(clientSocket);
		System.out.println();
		if (clientSocket != null && os != null && is != null)
		{
			System.out.println("Check for socket");
			try
			{

				System.out.println("Starting Thread");
		/* Create a thread to read from the server. */
				new Thread(new SecureClient()).start();
				while (!closed)
				{
					os.println(inputLine.readLine().trim());
				}
        /*
         * Close the output stream, close the input stream, close the socket.
         */
				os.close();
				is.close();
				clientSocket.close();
			}
			catch (IOException e)
			{
				System.err.println("IOException:  " + e);
			}
		}
	}

	/*
	 * Create a thread to read from the server. (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
		System.out.println("Starting Run");
		String responseLine;
		try
		{
			System.out.println("Starting while");
			while ((responseLine = is.readLine()) != null)
			{
				System.out.println(responseLine);
				//if (responseLine.indexOf("*** Bye") != -1)
				//break;

			}
			System.out.println("Broke out of while");
			closed = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("IOException:  " + e);
		}
	}
}
