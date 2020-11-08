package org.example;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteOrder;

public class TestEncode {

    public void print(Object o) {
        System.out.println(o);
    }

    @Test
    public void getByteOrder() {
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        print(byteOrder.toString());
    }

    int byteMask = 0xFF;
    int byteSize = Byte.BYTES;
    int shortSize = Short.BYTES;
    int intSize = Integer.BYTES;
    int longSize = Long.BYTES;

    byte byteValue = -1;
    short shortValue = 256 + 128;
    int intValue = 1000001;
    long longValue = 1000000000001L;

    public void testEncodeInt() {
        print(byteMask);
        print(Byte.MAX_VALUE);
    }

    public void encodeIntBigEndian(byte[] bytes, long value, int offset, int length) {
        for (int i = 0; i < length; i++) {
            bytes[offset + i] = (byte) (value >> (length - i - 1) * Byte.SIZE);
        }
    }

    public long decodeIntBigEndian(byte[] bytes, int offset, int length) {
        long value = 0L;
        for (int i = 0; i < length; i++) {
            value = (value << Byte.SIZE) | (bytes[offset + i]);
//            value = (value << Byte.SIZE) | (long) (bytes[offset + i] & byteMask);
        }
        return value;
    }

    public void bytesToString(byte[] bytes, int offset, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(bytes[i] & byteMask).append(" ");
        }
        print(builder.toString());
    }

    @Test
    public void main() {
//        testEncodeInt();
//        byte[] bytes = new byte[byteSize + shortSize + intSize + longSize];
//        encodeIntBigEndian(bytes, shortValue, 0, shortSize);
//        bytesToString(bytes, 0, shortSize);
//
//        long l = decodeIntBigEndian(bytes, 0, shortSize);
//        print(l);
//        print((long) (byteValue & byteMask));
//        print(6 | -5);
//        print(((byte) -4) & ((byte) 5));
//        print(((byte) -4) | ((byte) 5));
//        byte b = -128;
//        print((byte) (b & byteMask));
//        print((int) b);
//        b = -4;
//        print(b << 1);
        print((byte) 128);
        print(((short) 256) | (((byte) 128) & 0xFF));
    }

    @Test
    public void testDataStream() throws Exception {
        ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrOut);
        dataOutputStream.writeByte(127);
        dataOutputStream.writeShort(256 + 128);
        dataOutputStream.writeInt(intValue);
        dataOutputStream.writeLong(longValue);
        dataOutputStream.flush();
        byte[] bytes = byteArrOut.toByteArray();
        print(bytes);
    }
}
