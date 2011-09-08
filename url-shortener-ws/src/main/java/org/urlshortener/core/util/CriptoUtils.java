package org.urlshortener.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class CriptoUtils {

	public static String hash(Integer value, String algorithm) {
		String result = "";
		try {
			byte textBytes = value.byteValue();
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(textBytes);
			byte[] codigo = md.digest();
			result = convertToHex(codigo);

		} catch (NoSuchAlgorithmException ex) {
			System.out.println("hash ERROR:" + ex.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unused")
	public static String compress(String value) {

		byte[] input=null;
		try {
			input = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Compress the bytes
		byte[] output = new byte[100];
		Deflater compresser = new Deflater();
		compresser.setInput(input);
		compresser.finish();
		int compressedDataLength = compresser.deflate(output);
		
		return output.toString();

	}

	public static String decompress(String value, int compressedDataLength) {
		// Decompress the bytes
		Inflater decompresser = new Inflater();
		decompresser.setInput(value.getBytes(), 0, compressedDataLength);
		byte[] result = new byte[100];
		int resultLength=0;
		try {
			resultLength = decompresser.inflate(result);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		decompresser.end();

		// Decode the bytes into a String
		String outputString="";
		try {
			outputString = new String(result, 0, resultLength, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return outputString;
	}

	/**
	 * Convierta a Hexa
	 * 
	 * @param data
	 * @return
	 */
	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

}
