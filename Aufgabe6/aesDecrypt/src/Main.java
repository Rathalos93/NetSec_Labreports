import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
		HashSet<String> keys = generateKeys();
		System.out.println("Done generating Keys");
		Map<String, String> middleA = new HashMap<>();
		Map<String, String> middleB = new HashMap<>();

		String plaintext = "Verschluesselung";
		String ciphertext = "be393d39ca4e18f41fa9d88a9d47a574";
		byte[] bytes = Hex.decodeHex(ciphertext.toCharArray());
		ciphertext = new String(bytes, "UTF-8");

        for (String key : keys) {
            AES.setKey(key);

            AES.encrypt(plaintext);
            middleA.put(AES.getEncryptedString(), key);

            AES.decrypt(ciphertext);
            middleB.put(AES.getDecryptedString(), key );
        }

		System.out.println("Done encrypting");


        //Meet in the middle angriff
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
		AES.encrypt(plaintext);
		String e1 = AES.getEncryptedString();

		AES.setKey(key2);
		AES.encrypt(e1);
		String e2 = AES.getEncryptedString();

		return e2.equals(ciphertext);
	}


	private static HashSet<String> generateKeys()
	{
	    HashSet<String> keys = new HashSet<>();

		for (int posByteA = 0; posByteA < 16; posByteA++)
		{
            System.out.println(((double) posByteA / 16) * 100 + "% complete");
            for (int posByteB = posByteA; posByteB < 16; posByteB++)
			{

				for (int valueByteA = 0; valueByteA < 256; valueByteA++)
				{
					for (int valueByteB = 0; valueByteB < 256; valueByteB++)
					{
						byte[] key = new byte[16];
						key[posByteA] = (byte) valueByteA;
						key[posByteB] = (byte) valueByteB;

						//String sKey = Hex.encodeHexString(key);
                        String sKey = key + "";
						keys.add(sKey);
					}
				}
			}
		}
		return keys;
	}
}



