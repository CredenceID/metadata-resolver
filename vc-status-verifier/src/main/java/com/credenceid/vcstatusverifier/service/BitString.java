package com.credenceid.vcstatusverifier.service;

public class BitString {

    private final byte[] bits;
    private final boolean leftToRightIndexing;

    public BitString(byte[] bits, boolean leftToRightIndexing) {
        this.bits = bits;
        this.leftToRightIndexing = leftToRightIndexing;
    }

    public boolean getStatusBit(int position, int length) {
        if (position < 0 || position >= length) {
            throw new IllegalArgumentException("Position out of range");
        }

        int index = position / 8;         // index of the byte in which indexed bit is present
        int rem = position % 8;           // index of the indexed bit in the byte
        int shift = leftToRightIndexing ? (7 - rem) : rem;      // Left to right indexing
        int bit = 1 << shift;                                   // Bit mask

        return (bits[index] & bit) != 0;  // Check the indexed bit if it is set or unset
    }

}
