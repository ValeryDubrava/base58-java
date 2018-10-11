package ru.oak.jutils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;


public class Base58Test {
    private final static String Base58String = "1MkXN76E4S3JaaCpfDeVg95KkigXJZEk9Y";
    private final static String HexString = "00E39DBF9F2C0419D2342CDEEC6FC8F6AFAE5E4F6A322960A3";

    @Test
    public void testEncode() {
        byte[] source = DatatypeConverter.parseHexBinary(HexString);
        String result = new Base58().getEncoder().encode(source);
        Assert.assertEquals(result, Base58String);
    }

    @Test
    public void testDecode() {
        byte[] result = new Base58().getDecoder().decode(Base58String);
        String hexResult = DatatypeConverter.printHexBinary(result);
        Assert.assertEquals(HexString, hexResult);
    }
}