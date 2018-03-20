package utils;

import ij.io.Opener;
import net.imglib2.RandomAccess;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.basictypeaccess.array.ByteArray;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.util.Fraction;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by dibrov on 02/08/17.
 */
public class Convert {

    public static byte[] tiffToByteArrayBE(String pPathToTiff) {
        byte[] returnArray = null;

        try {
            Img<UnsignedShortType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));

            RandomAccess<UnsignedShortType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            if (ndim != 3) {
                throw new IllegalArgumentException("don't know what to do with it yet... ndim != 3");
            }

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);

            System.out.println("---> tiffToArray: converting a tiff with dims: " + x + " " + y + " " + z);

            returnArray = new byte[2 * x * y * z];

            int mask1 = 0B1111111100000000;
            int mask2 = 0B0000000011111111;

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] = {i, j, k};
                        ra.setPosition(pos);
                        returnArray[2 * (i + x * j + x * y * k)] = (byte) ((mask1 & (ra.get().getInteger())) >>> 8);
                        returnArray[2 * (i + x * j + x * y * k)
                                + 1] = (byte) ((mask2 & ra.get().getInteger()));
                    }
                }
            }

            System.out.println("---> tiffToArray: conversion complete");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    public static byte[] tiffToByteArrayLE(String pPathToTiff) {
        byte[] returnArray = null;

        try {


            Img<UnsignedShortType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));

            RandomAccess<UnsignedShortType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            if (ndim != 3) {
                throw new IllegalArgumentException("don't know what to do with it yet... ndim != 3");
            }

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);

            System.out.println("---> tiffToArray: converting a tiff with dims: " + x + " " + y + " " + z);

            returnArray = new byte[2 * x * y * z];

            int mask1 = 0B1111111100000000;
            int mask2 = 0B0000000011111111;

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] = {i, j, k};
                        ra.setPosition(pos);
                        returnArray[2 * (i + x * j + x * y * k) + 1] = (byte) ((mask1 & (ra.get().getInteger())) >>> 8);
                        returnArray[2 * (i + x * j + x * y * k)
                                ] = (byte) ((mask2 & ra.get().getInteger()));
                    }
                }
            }

            System.out.println("---> tiffToArray: conversion complete");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnArray;
    }


    public static byte[] tiffToByteArray8bit(String pPathToTiff) {
        byte[] returnArray = null;

        try {
            Img<UnsignedByteType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));

            RandomAccess<UnsignedByteType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            if (ndim != 3) {
                throw new IllegalArgumentException("don't know what to do with it yet... ndim != 3");
            }

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);

            System.out.println("---> tiffToArray: converting a tiff with dims: " + x + " " + y + " " + z);

            returnArray = new byte[x * y * z];


            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] = {i, j, k};
                        ra.setPosition(pos);
                        returnArray[(i + x * j + x * y * k)] = (byte) ra.get().getInteger();
                    }
                }
            }

            System.out.println("---> tiffToArray: conversion complete");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    public static ArrayImg wrapIntoImgLib2ArrayImg(byte[] pDataToWrap, int pDimX, int pDimY, int pDimZ) {
        try {
            if (pDataToWrap.length != 2 * pDimX * pDimY * pDimZ) {
                throw new Exception("Target image dimensions do not match to the array size. pDimX*pDimY*pDimZ = " +
                        pDimX * pDimY * pDimZ + "; pDataToWrap.length = " + pDataToWrap.length + ". Returning null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        ByteArray lByteArray = new ByteArray(pDataToWrap);
        long[] dims = {pDimX, pDimY, pDimZ};
        ArrayImg<UnsignedByteType, ByteArray> img = new ArrayImg(lByteArray, dims, new Fraction());
        UnsignedByteType ubt = new UnsignedByteType(img);
        img.setLinkedType(ubt);
        return img;
    }

    public static void convertTIFF8bitToRaw(String pPathToTIFF,
                                            String pName) {

        Img<UnsignedByteType> img =
                null;
//        ImgOpener iop = new ImgOpener();


        try {

            img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTIFF));
            String name = pName + "_" + img.dimension(0) + "x" + img.dimension(1) + "x" + img.dimension(2) + ".raw";
            FileOutputStream f = new FileOutputStream(name);
            RandomAccess<UnsignedByteType> ra = img.randomAccess();

            // ra.get().

            int ndim = img.numDimensions();

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z;
            if (ndim != 3) {
                z = 1;
            } else {
                z = (int) img.dimension(2);
            }


            System.out.println("converting a tiff with dims: " + x
                    + " "
                    + y
                    + " "
                    + z);

            byte[] arr = new byte[x * y * z];

            int mask1 = 0B1111111100000000;
            int mask2 = 0B0000000011111111;

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] =
                                {i, j, k};
                        ra.setPosition(pos);
                        int curr = ra.get().getInteger();
                        // System.out.println("integer is: " + curr);
                        arr[(i + x * j
                                + x * y
                                * k)] =
                                (byte) ra.get().getInteger();

                        // System.out.println("byte1 is: " +arr[2 * (i + x * j + x * y *
                        // k)]);
                        // System.out.println("byte2 is: " +arr[2 * (i + x * j + x * y * k)
                        // + 1]);
                    }
                }
            }

            f.write(arr);
            f.close();
            System.out.println("done converting");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.out.println("finally!");
        }
    }

    public static void convertTIFF16bitToRaw(String pPathToTIFF) {

        Img<UnsignedShortType> img =
                null;
//        ImgOpener iop = new ImgOpener();


        try {

            img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTIFF));
            String name = pPathToTIFF.substring(0, pPathToTIFF.length() - 4) + "_" + img.dimension(0) + "x" + img
                    .dimension(1) +
                    "x" + img
                    .dimension(2) +
                    "" +
                    ".raw";
            FileOutputStream f = new FileOutputStream(name);
            RandomAccess<UnsignedShortType> ra = img.randomAccess();

            byte[] arr = tiffToByteArrayBE(pPathToTIFF);


            f.write(arr);
            f.close();
            System.out.println("done converting");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.out.println("finally!");
        }
    }


    public static int[] parseDimsFromFileName(String fName, String dName) {
        String path = "";
        File dir = new File(dName);
        for (File f : dir.listFiles()) {
            System.out.println("file " + f);
            if (f.getName().startsWith(fName) && f.getName().endsWith(".raw")) {
                path = dName + f.getName();
            }
        }

        System.out.println("path: " + path);
        String[] split1 = path.split("_");

        String suff1 = split1[split1.length - 1];
        System.out.println("suff1 is : " + suff1);
        String[] split2 = suff1.split("\\.");
        System.out.println("split2 size is: " + split2.length);
        String suff2 = split2[split2.length - 2];

        System.out.println(split1[split1.length - 1]);


        String suff = suff2;
        System.out.println(split2.length);
        String[] dimsS = suff2.split("x");
        int[] dims = {Integer.parseInt(dimsS[0]), Integer.parseInt(dimsS[1]), Integer.parseInt(dimsS[2])};
        return dims;
    }

//    public static void ByteBuffer32to8(ByteBuffer pBuffer32, ByteBuffer pBuffer8, OpenCLEnvironment pOpenCLEnv) {
//        if (pBuffer32.capacity() % 4 != 0) {
//            throw new IllegalArgumentException("Size of the input 32-bit buffer is not divisible by 4. That's wrong.");
//        }
//        if (pBuffer32.capacity() / 4 != pBuffer8.capacity()) {
//            throw new IllegalArgumentException("Size of the input 32-bit buffer /4 != size of the 8-bit buffer. Cannot convert32to8.");
//        }
//
//    }

    public static void main(String[] args) {
        tiffToByteArrayLE("resources/img/P5.tif");
    }

}
