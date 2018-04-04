package utils;

import ij.io.Opener;
import net.imglib2.RandomAccess;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.basictypeaccess.array.ByteArray;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.util.Fraction;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by dibrov on 02/08/17.
 */
public class Convert {

    public static ByteBufferedImage tiff16bitToByteBufferedImage(String pPathToTiff, Endianness pEndianness) {
        byte[] returnArray = null;
        int x = 0;
        int y = 0;
        int z = 0;

        try {
            System.out.println(String.format("[Utils]: --- tiff16bitToByteBufferedImage --- "));
            double t1 = System.nanoTime();

            Img<UnsignedShortType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));
            RandomAccess<UnsignedShortType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            System.out.println(String.format("[Utils]: Loaded a .tif file with %d dimensions.", ndim));
            if (ndim < 2 || ndim > 3) {
                throw new IllegalArgumentException(String.format("Only 2d and 3d images are supported. Given ndim: " + "%d", ndim));
            }

            x = (int) img.dimension(0);
            y = (int) img.dimension(1);
            z = (int) img.dimension(2);

            System.out.println(String.format("[Utils]: %dx%dx%d ", x, y, z));

            returnArray = new byte[2 * x * y * z];

            int mask1 = 0B1111111100000000;
            int mask2 = 0B0000000011111111;

            int d1 = 1;
            int d2 = 0;

            if (pEndianness == Endianness.BE) {
                d1 = 0;
                d2 = 1;
            }

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] = {i, j, k};
                        ra.setPosition(pos);

                        returnArray[2 * (i + x * j + x * y * k) + d1] = (byte) ((mask1 & (ra.get().getInteger())) >>> 8);
                        returnArray[2 * (i + x * j + x * y * k) + d2] = (byte) ((mask2 & ra.get().getInteger()));
                    }
                }
            }
            double t2 = System.nanoTime();
            System.out.println(String.format("[Utils]: conversion complete in %.2f ms.", (t2 - t1) * 1e-6));

        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteBufferedImage bbi = null;
        try {
            bbi = new ByteBufferedImage(ByteBuffer.wrap(returnArray), x, y, z, 2, pEndianness);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bbi;
    }

    public static byte[] tiff16bitToByteArray(String pPathToTiff, Endianness pEndianness) {
        byte[] returnArray = null;

        try {
            System.out.println(String.format("[Utils]: --- tiff16bitToByteArray --- "));
            double t1 = System.nanoTime();

            Img<UnsignedShortType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));
            RandomAccess<UnsignedShortType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            System.out.println(String.format("[Utils]: Loaded a .tif file with %d dimensions.", ndim));
            if (ndim < 2 || ndim > 3) {
                throw new IllegalArgumentException(String.format("Only 2d and 3d images are supported. Given ndim: " + "%d", ndim));
            }

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);

            System.out.println(String.format("[Utils]: %dx%dx%d ", x, y, z));

            returnArray = new byte[2 * x * y * z];

            int mask1 = 0B1111111100000000;
            int mask2 = 0B0000000011111111;

            int d1 = 1;
            int d2 = 0;

            if (pEndianness == Endianness.BE) {
                d1 = 0;
                d2 = 1;
            }

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] = {i, j, k};
                        ra.setPosition(pos);

                        returnArray[2 * (i + x * j + x * y * k) + d1] = (byte) ((mask1 & (ra.get().getInteger())) >>> 8);
                        returnArray[2 * (i + x * j + x * y * k) + d2] = (byte) ((mask2 & ra.get().getInteger()));
                    }
                }
            }
            double t2 = System.nanoTime();
            System.out.println(String.format("[Utils]: conversion complete in %.2f ms.", (t2 - t1) * 1e-6));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    public static short[] tiff16bitToShortArray(String pPathToTiff) {
        short[] returnArray = null;

        try {
            System.out.println(String.format("[Utils]: --- tiff16bitToShortArray --- "));
            double t1 = System.nanoTime();

            Img<UnsignedShortType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));
            RandomAccess<UnsignedShortType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            System.out.println(String.format("[Utils]: Loaded a .tif file with %d dimensions.", ndim));
            if (ndim < 2 || ndim > 3) {
                throw new IllegalArgumentException(String.format("Only 2d and 3d images are supported. Given ndim: " + "%d", ndim));
            }

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);

            System.out.println(String.format("[Utils]: %dx%dx%d ", x, y, z));

            returnArray = new short[x * y * z];


            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        int pos[] = {i, j, k};
                        ra.setPosition(pos);
                        returnArray[(i + x * j + x * y * k)] = (short) ra.get().getInteger();
                    }
                }
            }

            double t2 = System.nanoTime();
            System.out.println(String.format("[Utils]: conversion complete in %.2f ms.", (t2 - t1) * 1e-6));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnArray;
    }


    public static byte[] tiff8bitToByteArray(String pPathToTiff) {
        byte[] returnArray = null;

        try {
            System.out.println(String.format("[Utils]: --- tiff8bitToByteArray --- "));
            double t1 = System.nanoTime();

            Img<UnsignedByteType> img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTiff));
            RandomAccess<UnsignedByteType> ra = img.randomAccess();

            int ndim = img.numDimensions();
            System.out.println(String.format("[Utils]: Loaded a .tif file with %d dimensions.", ndim));
            if (ndim < 2 || ndim > 3) {
                throw new IllegalArgumentException(String.format("Only 2d and 3d images are supported. Given ndim: " + "%d", ndim));
            }

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);

            System.out.println(String.format("[Utils]: %dx%dx%d ", x, y, z));

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

            double t2 = System.nanoTime();
            System.out.println(String.format("[Utils]: conversion complete in %.2f ms.", (t2 - t1) * 1e-6));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    public static ArrayImg wrapByteArrayIntoImgLib2ArrayUnsignedByteImg(byte[] pDataToWrap, int pDimX, int pDimY, int pDimZ) {
        try {
            if (pDataToWrap.length != pDimX * pDimY * pDimZ) {
                throw new Exception("Target image dimensions do not match to the array size. pDimX*pDimY*pDimZ = " + pDimX * pDimY * pDimZ + "; " +
                        "pDataToWrap.length = " + pDataToWrap.length + ". Returning null.");
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

    public static void convertTIFF8bitToRaw(String pPathToTIFF) {

        Img<UnsignedByteType> img = null;

        try {

            img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTIFF));
            String name = pPathToTIFF.substring(0, pPathToTIFF.length() - 4) + "_" + img.dimension(0) + "x" + img.dimension(1) + "x" + img
                    .dimension(2) + "" + ".raw";
            FileOutputStream f = new FileOutputStream(name);
            RandomAccess<UnsignedByteType> ra = img.randomAccess();

            // ra.get().

            int ndim = img.numDimensions();

            int x = (int) img.dimension(0);
            int y = (int) img.dimension(1);
            int z = (int) img.dimension(2);


            System.out.println("converting a tiff with dims: " + x + " " + y + " " + z);

            byte[] arr = tiff8bitToByteArray(pPathToTIFF);


            f.write(arr);
            f.close();
            System.out.println("done converting");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertTIFF16bitToRaw(String pPathToTIFF, Endianness pEndianness) {

        Img<UnsignedShortType> img = null;

        try {

            img = ImagePlusAdapter.wrapImgPlus(new Opener().openImage(pPathToTIFF));
            String name = pPathToTIFF.substring(0, pPathToTIFF.length() - 4) + "_" + img.dimension(0) + "x" + img.dimension(1) + "x" + img
                    .dimension(2) + "" + ".raw";
            FileOutputStream f = new FileOutputStream(name);
            RandomAccess<UnsignedShortType> ra = img.randomAccess();


            byte[] arr = tiff16bitToByteArray(pPathToTIFF, pEndianness);


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

    public static short bytesToShort(byte[] bytes, Endianness pEndianness) {

        int mask1 = 0B1111111100000000;
        int mask2 = 0B0000000011111111;
        int d1 = 0;
        int d2 = 1;

        if (pEndianness == Endianness.BE) {
            d1 = 1;
            d2 = 0;
        }

        int v1 = (bytes[d1] & mask2);
        int v2 = (bytes[d2] << 8) & mask1;
        int value = v1 + v2;
        return (short) value;
    }

    public static long binary(byte b) {
        long bin = 0;
        for (int i = 0; i < 8; i++) {
            bin += Math.pow(10, i) * (b % 2);
            b >>>= 1;

        }
        return bin;
    }


    public static byte[] shortArrayToByteArray(short[] arr, Endianness pEndianness) {
        byte[] barr = new byte[2 * arr.length];

        int mask1 = 0B1111111100000000;
        int mask2 = 0B0000000011111111;

        int d1 = 0;
        int d2 = 1;

        if (pEndianness == Endianness.BE) {
            d1 = 1;
            d2 = 0;
        }


        for (int i = 0; i < arr.length; i++) {
            barr[2 * i + d1] = (byte) (arr[i] & mask2);
            barr[2 * i + d2] = (byte) ((arr[i] & mask1) >>> 8);
        }

        return barr;
    }

    public static void main(String[] args) {
        //        tiff16bitToByteArray("resources/img/wingCropStack.tif", Endianness.LE);
        //        convertTIFF16bitToRaw("resources/img/wingCropStack.tif", Endianness.LE);


        short sh = bytesToShort(new byte[]{1, 1}, Endianness.LE);
        System.out.println(sh);

        long l = binary((byte) 7);
        System.out.println(l);

        short[] arr = new short[] {1,2,3,4,5};
        byte[] barr = shortArrayToByteArray(arr, Endianness.LE);

        for (int i = 0; i < barr.length; i++) {
            System.out.println(barr[i]);
        }

    }

}
