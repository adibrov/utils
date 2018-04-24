package utils;

import static utils.Convert.convertTIFF16bitToRaw;
import static utils.Convert.convertTIFF16bitToRawPlanes;
import static utils.Loaders.loadUnsignedShortFrameFromDisk;

public class Sandbox {
    public static void main(String[] args) {
        // test runs for rame loading from HDD
//        convertTIFF16bitToRaw("resources/aux/fullWingStack16bit.tif", Endianness.LE);

//        loadUnsignedShortFrameFromDisk("resources/aux/fullWingStack16bit_1984x3875x61.raw", 1984,3875,61,1);
        convertTIFF16bitToRawPlanes("resources/aux/fullWingStack16bit.tif", Endianness.LE);
    }
}
