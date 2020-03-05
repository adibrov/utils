package utils;

import java.nio.ByteBuffer;

import static utils.Convert.convertTIFF16bitToRaw;
import static utils.Convert.convertTIFF16bitToRawPlanes;
import static utils.Loaders.loadUnsignedShortFrameFromDisk;

public class Sandbox {
    public static void main(String[] args) {
        // test runs for rame loading from HDD
//        convertTIFF16bitToRaw("resources/aux/fullWingStack16bit.tif", Endianness.LE);

//        loadUnsignedShortFrameFromDisk("resources/aux/fullWingStack16bit_1984x3875x61.raw", 1984,3875,61,1);
//        convertTIFF16bitToRawPlanes("resources/aux/fullWingStack16bit.tif", Endianness.LE);

        ByteBuffer bb =loadUnsignedShortFrameFromDisk("resources/aux/fullWingStack16bit_1984x3875x61", 1984,3875,1);
//        ByteBuffer trg = ByteBuffer.allocate(1024*1024);
//        double t1 = System.nanoTime();
//        bb.position(10*10);
//        bb.get(trg.array(),0,1024*1024);
//        double t2 = System.nanoTime();

//        System.out.println(String.format("took %f ms ", (t2-t1)*1e-6));

        System.out.println(Math.floor(-.4));
    }
}
