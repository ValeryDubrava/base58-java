package ru.oak.jutils;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Base58 encoder/decoder with supporting leading zeros.
 * Simple but not faster implementation based on BigInteger.
 */
public class Base58 {
    private final static char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private final static int[] INDEXES = new int[128];
    private final static BigInteger BASE = BigInteger.valueOf(58);
    private final static Encoder ENCODER = new Encoder();
    private final static Decoder DECODER = new Decoder();

    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    public static Decoder getDecoder() {
        return DECODER;
    }

    public static Encoder getEncoder() {
        return ENCODER;
    }

    public static class Encoder {
        public String encode(byte[] data) {
            char[] buffer = new char[data.length * 2];
            int i = buffer.length - 1;
            BigInteger v = new BigInteger(data);
            while (!v.equals(BigInteger.ZERO)) {
                BigInteger[] divAndRem = v.divideAndRemainder(BASE);
                buffer[i] = ALPHABET[divAndRem[1].intValue()];
                v = divAndRem[0];
                i--;
            }
            for (int zeroI = 0; data[zeroI] == 0; zeroI ++) {
                buffer[i] = ALPHABET[0];
                i--;
            }
            return new String(buffer, i + 1, buffer.length - i - 1);
        }
    }

    public static class Decoder {
        public byte[] decode(String base58value) {
            BigInteger k = BigInteger.ONE;
            BigInteger result = BigInteger.ZERO;

            for (int i = base58value.length() - 1; i >= 0; i --) {
                char c = base58value.charAt(i);
                if (c >= 128 || INDEXES[c] == -1) {
                    throw new IllegalArgumentException("wrong character " + c + " at position " + i);
                }
                int v = INDEXES[c];
                result = result.add(k.multiply(BigInteger.valueOf(v)));
                k = k.multiply(BASE);
            }
            return result.toByteArray();
        }
    }
}
