package com.hoppix;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

public class Useradmin implements Useradministration
{

	private static final String fileName = "passwords.txt";
	private static final int hashIterator = 1337;

	public static void main(String[] args)
	{
		String command = args[0];
		String user = args[1];

		Useradmin admin = new Useradmin();

		System.out.println("Password: ");
		Scanner scanner = new Scanner(System.in);
		String password = scanner.next();
		scanner.close();



		switch (command)
		{
			case "addUser":
				admin.addUser(user, password.toCharArray());
				break;

			case "checkUser":
				boolean found = admin.checkUser(user, password.toCharArray());
				if (found)
				{
					System.out.println("User correct");
				}
				break;

			default:
				System.out.println("Wrong input");

		}


	}

	public void addUser(String username, char[] password)
	{
		String passwordString = String.copyValueOf(password);
		String hash = "";

		Random rand = new Random();
		int salt = rand.nextInt(1234567) + 100000;

		try
		{
			hash = md5Hash(salt + passwordString);
			for (int i = 0; i < hashIterator; i++)
			{
				hash = md5Hash(hash);
			}

		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		finally
		{
			writeLine(fileName, username, salt, hash);
		}


	}

	public boolean checkUser(String username, char[] password)
	{
		String[] content = checkPasswordLine(fileName, username);
		if (content.equals(null))
		{
			System.out.println("User not found!");
			return false;
		}

		String passwordString = String.copyValueOf(password);
		String salt = content[1];
		String hashSave = content[2];

		try
		{
			String hash = md5Hash(salt + passwordString);
			for (int i = 0; i < hashIterator; i++)
			{
				hash = md5Hash(hash);
			}

			return hash.equals(hashSave);
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("Wrong password!");
			e.printStackTrace();
			return false;
		}

	}

	private static String md5Hash(String plain) throws NoSuchAlgorithmException
	{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(plain.getBytes());

		byte[] byteHash = md5.digest();
		return DatatypeConverter.printHexBinary(byteHash).toLowerCase();
	}

	public void writeLine(String fileName, String username, int salt, String saltHashedPassword)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			if (bufferedReader.readLine() == null)
			{
				bufferedWriter.write(username + " " + salt + " " + saltHashedPassword);
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
			bufferedReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Schreibfehler");
		}
	}

	public String[] checkPasswordLine(String fileName, String username)
	{
		String line = null;

		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
			{
				String[] content = line.split(" ");
				if (username.equals(content[0]))
				{
					return content;
				}
			}
			bufferedReader.close();
			System.out.println("Read " + fileName);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Datei nicht gefunden");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Lesefehler");
		}
		return null;
	}


}

interface Useradministration
{
	void addUser(String username, char[] password);

	boolean checkUser(String username, char[] password);
}
