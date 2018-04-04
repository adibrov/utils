package utils;


import java.nio.ByteBuffer;

public class ByteBufferedImage {

    private ByteBuffer buffer;
    private int x;
    private int y;
    private int z;
    private int mBytesPerPixel;
    private Endianness mEndianness;


    public ByteBufferedImage(ByteBuffer buffer, int x, int y, int z, int pBytesPerPixel, Endianness pEndianness) throws Exception{


        int lSize = pBytesPerPixel*x*y*z;
        if (buffer.capacity() != lSize) {
            throw new Exception(String.format("Wrong buffer size! Image of dimensions %dx%dx%d and %d bytes per pixel has size %d, provided buffer has %d", x,y,z,pBytesPerPixel,lSize, buffer.capacity()));
        }

        this.mBytesPerPixel = pBytesPerPixel;
        this.mEndianness = pEndianness;
        this.buffer = buffer;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ByteBufferedImage(ByteBuffer buffer, int x, int y, int z, int pBytesPerPixel) throws Exception{
        this(buffer,x,y,z, pBytesPerPixel, Endianness.LE);
    }

    public ByteBufferedImage(int x, int y, int z, int pBytesPerPixel, Endianness pEndianness) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mBytesPerPixel = pBytesPerPixel;
        this.mEndianness = pEndianness;
        this.buffer = ByteBuffer.allocate(mBytesPerPixel*x*y*z);
    }

    public ByteBufferedImage(int x, int y, int z, int pBytesPerPixel) {
        this(x,y,z,pBytesPerPixel, Endianness.LE);
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getBytesPerPixel() {
        return mBytesPerPixel;
    }
}
