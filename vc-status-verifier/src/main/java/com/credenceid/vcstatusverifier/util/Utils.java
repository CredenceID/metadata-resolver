package com.credenceid.vcstatusverifier.util;

import com.credenceid.vcstatusverifier.exception.ServerException;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class Utils {

    private Utils() {
    }

    /**
     * Decodes a Base64URL-encoded string, validates it, and extracts the bit at a specified index.
     * It also handles decompression of the data.
     *
     * @param encodedListStr The Base64URL-encoded and compressed string prefixed with 'u'.
     * @param index          The index of the status to extract.
     * @param statusSize     The size of each status in bits.
     * @return boolean indicating whether the bit at the specified index is set (true) or not (false).
     */
    public static boolean decodeStatusList(String encodedListStr, int index, int statusSize) throws IOException {
        if (encodedListStr == null || encodedListStr.isEmpty()) {
            throw new IllegalArgumentException("Encoded string cannot be null or empty");
        }

        if (!encodedListStr.startsWith("u")) {
            throw new IllegalArgumentException("encoded list must start with 'u' ");
        }
        String encodedList = encodedListStr.substring(1);

        // Validate if the string is Base64URL
        if (!isValidBase64Url(encodedList)) {
            throw new IllegalArgumentException("The provided string is not a valid Base64URL-encoded string");
        }

        return getBitAtIndex(encodedList, index, statusSize);
    }


    /**
     * Extracts a specific bit from a Base64URL-encoded and GZIP-compressed string at a given index.
     * This method decodes the string, decompresses it, and returns whether the bit at the specified index is set (true) or not (false).
     *
     * @param encodedString   The Base64URL-encoded and compressed string.
     * @param credentialIndex The index of the credential to retrieve the bit for.
     * @param statusSize      The size of each status in bits.
     * @return boolean indicating whether the bit at the specified index is set (true) or not (false).
     */
    public static boolean getBitAtIndex(String encodedString, int credentialIndex, int statusSize) throws IOException {
        //Decode the base64url encoded string
        byte[] decodedBytes = decodeBase64Url(encodedString);

        //Decompress the decodedBytes[]
        byte[] decompressedBytes = decompressGzip(decodedBytes);

        int index = credentialIndex * statusSize;
        if (index >= decompressedBytes.length) {
            throw new ServerException(Constants.RANGE_ERROR);
        }

        // Step 3: Access the bit at the specified index
        int byteIndex = index / 8;          // Find the byte index
        int bitPosition = index % 8;        // Find the bit within the byte
        byte byteValue = decompressedBytes[byteIndex];

        // Calculate the mask for the bit we are interested in
        int bitMask = 1 << (7 - bitPosition);  // Left-to-right indexing (MSB is 0th bit)

        // Check if the bit is set (non-zero value)
        return (byteValue & bitMask) != 0;
    }

    /**
     * Decodes a Base64URL string into a byte array.
     *
     * @param base64Url The Base64URL-encoded string.
     * @return A byte array representing the decoded data.
     */
    public static byte[] decodeBase64Url(String base64Url) {
        String standardBase64 = base64Url.replace('-', '+').replace('_', '/');
        Base64 decoder = new Base64(true);
        return decoder.decode(standardBase64);
    }

    /**
     * Decompresses a GZIP-compressed byte array.
     *
     * @param compressedData The GZIP-compressed byte array.
     * @return A byte array containing the decompressed data.
     */
    public static byte[] decompressGzip(byte[] compressedData) throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            try (ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream()) {
                while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                    decompressedStream.write(buffer, 0, bytesRead);
                }
                return decompressedStream.toByteArray();
            }
        }
    }


    /**
     * Validates if the given string is a valid Base64URL-encoded string.
     * Base64URL encoding uses a URL-safe alphabet where '+' and '/' are replaced with '-' and '_', respectively,
     * and padding ('=') is not used.
     *
     * @param str The string to validate.
     * @return true if the string matches the Base64URL pattern, false otherwise.
     */
    private static boolean isValidBase64Url(String str) {
        // Base64URL strings should not have padding '=' and use '-' and '_' instead of '+' and '/'
        String base64UrlPattern = "^[A-Za-z0-9_-]+$";
        return str.matches(base64UrlPattern);
    }
}