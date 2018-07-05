import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Decrypting easyAES Aufgabe 4.
 * Created by hoppix on 05.07.18.
 */
public class Main
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("AES Decrypt");
		ArrayList<String> keys = generateKeys();
		System.out.println("Done generating Keys");
		Map<String, String> middleA = new HashMap<String, String>();
		Map<String, String> middleB = new HashMap<String, String>();

		String plaintext = "Verschluesselung";
		String ciphertext = "be393d39ca4e18f41fa9d88a9d47a574";
		byte[] bytes = Hex.decodeHex(ciphertext.toCharArray());
		ciphertext = new String(bytes, "UTF-8");

		for (int i = 0; i < keys.size(); i++)
		{
			AES.setKey(keys.get(i));
			middleA.put(AES.encrypt(plaintext), keys.get(i));
			middleB.put(AES.decrypt(ciphertext), keys.get(i));
		}

		System.out.println("Done encrypting");

		for (String sA : middleA.keySet())
		{
			for (String sB : middleB.keySet())
			{
				if(sA.equals(sB))
				{
					ArrayList<String> keyPair = new ArrayList<>();
					String k1 = middleA.get(sA);
					String k2 = middleB.get(sB);

					keyPair.add(k1);
					keyPair.add(k2);

					System.out.println(keyPair.toString());
					System.out.println("Correctness: ");
					System.out.println(checkCorrectness(plaintext, ciphertext, k1, k2));
				}
			}
		}
	}

	private static boolean checkCorrectness(String plaintext, String ciphertext, String key1, String key2)
	{
		AES.setKey(key1);
		String e1 = AES.encrypt(plaintext);

		AES.setKey(key2);
		String e2 = AES.encrypt(e1);

		return e2.equals(ciphertext);
	}


	private static ArrayList<String> generateKeys()
	{
		ArrayList<String> keys = new ArrayList<String>();

		for (int posByteA = 0; posByteA < 16; posByteA++)
		{
			System.out.println("posByteA: " + posByteA);
			for (int posByteB = 0; posByteB < 16; posByteB++)
			{
				for (int valueByteA = 0; valueByteA < 256; valueByteA++)
				{
					for (int valueByteB = 0; valueByteB < 256; valueByteB++)
					{
						byte[] key = new byte[16];
						key[posByteA] = (byte) valueByteA;
						key[posByteB] = (byte) valueByteB;

						String sKey = Hex.encodeHexString(key);
						if (posByteA >= 6)
						{
							System.out.println(sKey);
						}

						keys.add(sKey);
					}
				}
			}
		}
		return keys;
	}
}



