package utils;

import java.nio.ByteBuffer;

public class DuplicateSB {

    public static void main(String[] args) {

        ByteBufferedImage bbi = new ByteBufferedImage(128,128,1,4);

        ByteBufferedImage bbid = bbi.duplicate();
        bbi.getBuffer().rewind();
        bbid.getBuffer().rewind();

        System.out.println(bbid.getBuffer().position());

//        System.out.println(bbid.getBuffer().capacity());

        System.out.println(bbid.getBuffer().asFloatBuffer().get(0));


        System.out.println("----");

        ByteBuffer bb = ByteBuffer.allocate(128*128*4);
//        for (int i = 1; i <= 20; i++) {
//            bb.put((byte)i);
//        }

        bb.rewind();
        bb.put(ByteBuffer.allocate(128*128*4));

//        bb.rewind();
        System.out.println(bb.get(0));
    }
}
