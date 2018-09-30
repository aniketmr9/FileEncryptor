package app.fileencryption.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import org.apache.commons.lang3.ArrayUtils;

public class EncryptionUtility
{
	//declaring lowercase and uppercase alphabets
	static String lcAlphabets[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z" };
	static String ucAlphabets[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	static Scanner scanner = new Scanner(System.in);
	static BufferedReader bufferedReader = null;
	static BufferedWriter bufferedWriter = null;
	static File encryptedFile = null;
	static String[] key = null;

	/*
	 * generateEncryptionKey() takes a String and converts it into corresponding integer array based on the position of each character in the string in English alphabet.
	 * Ignores space(" "), numbers(0-9) and special characters(?,(,),[,],...) Assigns 0 index to such characters. 
	 * Example : hello --> [7,4,11,11,14]
	 */
	public int[] generateEncryptionKey(String encryptionKeyVal)
	{
		String keyCharacter = "";
		//declaring integer array of size of encryption key
		int[] encryptionKeyValArr = new int[encryptionKeyVal.length()];
		for (int keyIndex = 0; keyIndex < encryptionKeyVal.length(); keyIndex++)
		{
			keyCharacter = Character.toString(encryptionKeyVal.toLowerCase().charAt(keyIndex));
			if (keyCharacter.equals(" "))
			{
				encryptionKeyValArr[keyIndex] = 0;
			}
			else if (!keyCharacter.matches("[a-zA-Z]+"))
			{
				encryptionKeyValArr[keyIndex] = 0;
			}
			else
			{
				int indexOfKeyCharacter = ArrayUtils.indexOf(lcAlphabets, keyCharacter);
				encryptionKeyValArr[keyIndex] = indexOfKeyCharacter;
			}
		}
		/*for (int i = 0; i < encryptionKeyValArr.length; i++)
		{
			System.out.println(encryptionKeyValArr[i]);
		}*/
		return encryptionKeyValArr;
	}
	/*
	 * encryptFile() takes string of data line by line from source file, encrypts its and returns the encrypted line
	 * It takes each character from input string adds corresponding index from encryptionKeyValArr to it
	 * once encryptionKeyValArr reaches the last array element it starts again from 0 index
	 */
	public String encryptFile(String message, String encryptionToken, int[] encryptionKeyValArr)
	{
		int alteredIndex = 0;
		String encryptedMessage = "";
		String messageAlphabet = "";
		int keyIndex = 0;
		int addIndex = 0;
		for (int index = 0; index < message.length(); index++)
		{
			addIndex = encryptionKeyValArr[keyIndex];
			messageAlphabet = Character.toString(message.charAt(index));
			if (messageAlphabet.equals(" "))
			{
				encryptedMessage = encryptedMessage + " ";
				continue;
			}
			if (!messageAlphabet.matches("[a-zA-Z]+"))
			{
				encryptedMessage = encryptedMessage + messageAlphabet;
				continue;
			}
			String tempMessageAlphabet = Character.toString(message.charAt(index)).toUpperCase();
			// Checking Case
			if (tempMessageAlphabet.equals(messageAlphabet))
			{
				int indexOfAlphabet = ArrayUtils.indexOf(ucAlphabets, messageAlphabet);
				// going to start of lcAlphabets
				int indexOfAlphabet1 = 25 - indexOfAlphabet;
				if (indexOfAlphabet1 < addIndex)
				{
					alteredIndex = addIndex - indexOfAlphabet1 - 1;
					encryptedMessage = encryptedMessage + ucAlphabets[alteredIndex];

				} else
				{
					encryptedMessage = encryptedMessage + ucAlphabets[indexOfAlphabet + addIndex];
				}
			} else
			{
				int indexOfAlphabet = ArrayUtils.indexOf(lcAlphabets, messageAlphabet);
				// going to start of lcAlphabets
				int indexOfAlphabet1 = 25 - indexOfAlphabet;
				if (indexOfAlphabet1 < addIndex)
				{
					alteredIndex = addIndex - indexOfAlphabet1 - 1;
					encryptedMessage = encryptedMessage + lcAlphabets[alteredIndex];

				} else
				{
					encryptedMessage = encryptedMessage + lcAlphabets[indexOfAlphabet + addIndex];
				}
			}
			keyIndex++;
			if (keyIndex >= encryptionKeyValArr.length)
			{
				keyIndex = 0;
			}
		}
		//System.out.println("Encrypted Message :" + encryptedMessage);
		return encryptedMessage;
	}

	/*
	 * decryptFile() takes string of data line by line from source file, decrypts its and returns the decrypted line
	 * It takes each character from input string subtracts corresponding index from encryptionKeyValArr to it
	 * once encryptionKeyValArr reaches the last array element it starts again from 0 index
	 */
	public String decryptFile(String encryptedMessage, String decryptionToken, int[] decryptionKeyValArr)
	{
		int alteredIndex = 0;
		String decryptedMessage = "";
		String messageAlphabet = "";
		int keyIndex = 0;
		int addIndex = 0;
		for (int index = 0; index < encryptedMessage.length(); index++)
		{
			addIndex = decryptionKeyValArr[keyIndex];
			messageAlphabet = Character.toString(encryptedMessage.charAt(index));
			if (messageAlphabet.equals(" "))
			{
				decryptedMessage = decryptedMessage + " ";
				continue;
			}
			if (!messageAlphabet.matches("[a-zA-Z]+"))
			{
				decryptedMessage = decryptedMessage + messageAlphabet;
				continue;
			}
			String tempMessageAlphabet = Character.toString(encryptedMessage.charAt(index)).toUpperCase();
			// Checking Case
			if (tempMessageAlphabet.equals(messageAlphabet))
			{
				int indexOfAlphabet = ArrayUtils.indexOf(ucAlphabets, messageAlphabet);
				// going to start of lcAlphabets
				if (indexOfAlphabet < addIndex)
				{
					alteredIndex = 25 - (addIndex - indexOfAlphabet) + 1;
					decryptedMessage = decryptedMessage + ucAlphabets[alteredIndex];
				} else
				{
					decryptedMessage = decryptedMessage + ucAlphabets[indexOfAlphabet - addIndex];
				}
			} else
			{
				int indexOfAlphabet = ArrayUtils.indexOf(lcAlphabets, messageAlphabet);
				// going to start of lcAlphabets
				if (indexOfAlphabet < addIndex)
				{
					alteredIndex = 25 - (addIndex - indexOfAlphabet) + 1;
					decryptedMessage = decryptedMessage + lcAlphabets[alteredIndex];

				} else
				{
					decryptedMessage = decryptedMessage + lcAlphabets[indexOfAlphabet - addIndex];
				}
			}
			keyIndex++;
			if (keyIndex >= decryptionKeyValArr.length)
			{
				keyIndex = 0;
			}
		}
		//System.out.println("Decrypted Message :" + decryptedMessage);
		return decryptedMessage;
	}

}