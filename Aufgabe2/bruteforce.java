import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class bruteforce
{

	public static void main(String[] args)
	{
		final String hash = "2b2935865b8a6749b0fd31697b467bd7";
		final String salt = "8kofferradio";
		final String account = "testaccount";
		final int cardinality = 6;
		String result = "";

		final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
				'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


		for (long i = 0L; i < Math.pow(alphabet.length, cardinality + 1); i++)
		{
			//first arity
			result = alphabet[(int) (i % 36)] + ""; //36^0
			if (i >= Math.pow(36, 1))
			{
				//second arity
				result = alphabet[(int) ((i / 36) - 1) % 36] + result; //36^1
				if (i >= Math.pow(36, 2))
				{
					//third arity
					result = alphabet[(int) ((i / Math.pow(36, 2) - 1) % 36)] + result;
					if (i >= Math.pow(36, 3))
					{
						//fourth arity
						result = alphabet[(int) ((i / Math.pow(36, 3)) - 1) % 36] + result;
						if (i >= Math.pow(36, 4))
						{
							//fifth arity
							result = alphabet[(int) ((i / Math.pow(36, 4)) - 1) % 36] + result;
							if (i >= Math.pow(36, 5))
							{
								//sixth arity
								result = alphabet[(int) ((i / Math.pow(36, 5)) - 1) % 36] + result;
							}
						}
					}
				}
			}

			String saltAndPasswd = salt + result;
			String hashedPasswd = "";
			try
			{
				hashedPasswd = md5Hash(saltAndPasswd);
			}
			catch (NoSuchAlgorithmException ex)
			{
				System.out.println("Error when hashing: ");
				ex.printStackTrace();
			}


			if (hash.equals(hashedPasswd))
			{

				System.out.println("Password found! :");
				System.out.println("User: " + account + " Password: " + result);
				break;
			}
			else
			{
				if(i%100000 == 0)
				{
					System.out.println(i + ". at " + result + "-- Retry");
				}
			}
		}
		System.out.println("############################################### --- *hackervoice* I'M IN --- ##############################################################");
	}

	private static String md5Hash(String plain) throws NoSuchAlgorithmException
	{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(plain.getBytes());
		byte[] byteHash = md5.digest();
		return DatatypeConverter.printHexBinary(byteHash).toLowerCase();
	}
}
