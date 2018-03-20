package utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by dibrov on 20/04/17.
 */
public class Loaders {
    public static ByteBuffer loadUnsignedShortSampleSpaceFromDisk(String pPath, int pDimX, int pDimY, int pDimZ) {
        System.out.println("Loading a SampleSpace from file " + pPath);
        byte[] arr = new byte[2*pDimX * pDimY * pDimZ];

        try (FileInputStream fis = new FileInputStream(pPath); BufferedInputStream bis = new BufferedInputStream(fis)) {
            long t1 = System.nanoTime();
            bis.read(arr);

            System.out.println(" loading: " + arr[12000] + " " + arr[13000] + " " + arr[20000]);

//            }
            long t2 = System.nanoTime();
            System.out.println("---Buffering: yes. Loaded file " + pPath + " in: " + ((t2 - t1) / 1000000.) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            System.out.println("Success.");
            System.out.println();
        }
//        return arrInt;
        ByteBuffer bb = ByteBuffer.wrap(arr);
        bb.rewind();
        return bb;
    }

    public static ByteBuffer loadUnsignedByteSampleSpaceFromDisk(String pPath, int pDimX, int pDimY, int pDimZ) {
        System.out.println("Loading a SampleSpace from file " + pPath);
        byte[] arr = new byte[pDimX * pDimY * pDimZ];
        short[] arrInt = new short[pDimX*pDimY*pDimZ];

        long h = 0;
        try (FileInputStream fis = new FileInputStream(pPath); BufferedInputStream bis = new BufferedInputStream(fis)) {
            long t1 = System.nanoTime();
            bis.read(arr);

//            for (int i = 0; i < pDimX*pDimY*pDimZ; i++) {
//                int b1 = arr[2*i];
//                int b2 = arr[2*i+1];
////                System.out.println("bytes b1 and b2: " + b1 + " " + b2);
////                System.out.println("bytes b1 and b2: " + ((b1<<8)&mask1) + " " + (b2&mask2));
////                System.out.println("bytes b1 and b2 sum: " + (((b1<<8)&mask1)  + (b2&mask2)));
//
////                System.out.println("just loaded: " + h);
//            }
            long t2 = System.nanoTime();
            System.out.println("---Buffering: yes. Loaded file " + pPath + " in: " + ((t2 - t1) / 1000000.) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            System.out.println("Success.");
            System.out.println();
        }
        return ByteBuffer.wrap(arr);
    }

    public static FloatBuffer loadFloatBufferFromDisk(String pPath) {
        System.out.println("Loading a SampleSpace from file " + pPath);
        File f = new File(pPath);
        long lFileSize = f.length();

        byte[] arr = new byte[(int)lFileSize];
        try ( FileInputStream fis = new FileInputStream(f); BufferedInputStream bis = new BufferedInputStream(fis)) {

            long t1 = System.nanoTime();
            bis.read(arr);
            long t2 = System.nanoTime();
            System.out.println("---Buffering: yes. Loaded file " + pPath + " in: " + ((t2 - t1) / 1000000.) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            System.out.println("Success.");
            System.out.println();
        }
        return ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN). asFloatBuffer().duplicate();
    }

    public static void writeByteBufferToDisk(String pPath, ByteBuffer pByteBuffer) {
        System.out.println("Writing a SampleSpace to file " + pPath);
        File f = new File(pPath);
        long lFileSize = pByteBuffer.capacity();

        byte[] arr = pByteBuffer.array();

        try (FileOutputStream fos = new FileOutputStream(f); BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            long t1 = System.nanoTime();
            bos.write(arr);
            long t2 = System.nanoTime();
            System.out.println("---Buffering: yes. Write file " + pPath + " in: " + ((t2 - t1) / 1000000.) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
//            return null;
        }
        finally {
            System.out.println("Success.");
            System.out.println();
        }
//        return ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
    }


    public static ByteBuffer loadNamedUnsignedShortSampleFromDisk(String pTiffFileName, String pResourcesFolderName){

//        String fileName = "P8";
//        String resImgPath = "resources/img/";
        File resImgDir = new File(pResourcesFolderName);

        String path = "";
        for (File f : resImgDir.listFiles()) {
            if (f.getName().startsWith(pTiffFileName) && f.getName().endsWith(".raw")) {
                path = pResourcesFolderName + f.getName();
            }
        }

        String[] split1 = path.split("_");
        String suff1 = split1[split1.length - 1];
        System.out.println("suff1 is : " + suff1);
        String[] split2 = suff1.split("\\.");
        System.out.println("split2 size is: " + split2.length);
        String suff2 = split2[split2.length - 2];

//        System.out.println(split1[split1.length - 1]);


        String suff = suff2;
//        System.out.println(split2.length);
        String[] dimsS = suff2.split("x");

        int[] dims = {Integer.parseInt(dimsS[0]), Integer.parseInt(dimsS[1]), Integer.parseInt(dimsS[2])};

        System.out.println("Loading named unsigned short with dims: " + dims[0] + "x"+dims[1]+"x"+dims[2]);

        return loadUnsignedShortSampleSpaceFromDisk(path, dims[0], dims[1], dims[2]);
    }
}
