package com.credenceid.vcstatusverifier.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Slf4j
public class Utils {

    private Utils() {
    }

    public static int decodeStatusList(String encodedListStr, int index, int statusSize) throws IOException {
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

        Base64 decoder = new Base64(true);
        byte[] decodedBytes = decoder.decode(encodedList);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);

        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);

        return extractIntFromBinaryString(getBitValue(gzipInputStream, index, statusSize));
    }

    public static int extractIntFromBinaryString(List<Character> decompressedList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character bit : decompressedList) {
            stringBuilder.append(bit);
        }

        return Integer.parseInt(stringBuilder.toString(), 2);
    }

    public static List<Character> getBitValue(InputStream inputStream, int index, int bitSize) {
        try (InputStream stream = inputStream) {
            // Calculate bit and byte positions
            int bitStartPosition = index * bitSize;
            log.debug("bitStartPosition: {}", bitStartPosition);

            long byteStart = bitStartPosition / 8;
            log.debug("skipping: {} bytes", byteStart);
            stream.skip(byteStart);

            log.debug("available: {} bytes", stream.available());

            int bytesToRead = (bitSize - 1) / 8 + 1;
            log.debug("readingNext: {} bytes", bytesToRead);

            byte[] readBytes = stream.readNBytes(bytesToRead);
            return extractBitValue(readBytes, index, bitSize);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting bit value", e);
        }
    }

    private static List<Character> extractBitValue(byte[] bytes, long index, int bitSize) {
        BitSet bitSet = BitSet.valueOf(bytes);
        log.debug("bits set: {}", bitSet.length());

        long bitStart = (index * bitSize) % 8;
        log.debug("startingFromBit: {}", bitStart);

        List<Character> result = new ArrayList<>();
        for (long i = bitStart; i < bitStart + bitSize; i++) {
            int bitValue = bitSet.get((int) i) ? 1 : 0;
            result.add(Character.forDigit(bitValue, 10));
        }
        return result;
    }

    private static boolean isValidBase64Url(String str) {
        // Base64URL strings should not have padding '=' and use '-' and '_' instead of '+' and '/'
        String base64UrlPattern = "^[A-Za-z0-9_-]+$";
        return str.matches(base64UrlPattern);
    }
}
