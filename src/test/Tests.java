import io.scif.img.ImgSaver;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.basictypeaccess.array.ShortArray;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.util.Fraction;
import org.junit.Test;
import utils.Endianness;

import java.util.Random;

import static junit.framework.TestCase.assertTrue;
import static utils.Convert.tiff16bitToByteArray;


public class Tests {
    @Test
    public void tiff16bitToByteArrayTest() {
        System.out.println("[UtilsTests]: --- tiff16bitToByteArrayTest ---");


        long[] dims = {100, 100,1};
        int size = 1;
        for (long l : dims) {
            size *= l;
        }

        System.out.println(String.format("[UtilsTests]: Original short array size: %d", size));

        short[] shArr = new short[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            shArr[i] = (short)rand.nextInt(2*Short.MAX_VALUE);
        }


        ShortArray lShortArray = new ShortArray(shArr);
        ArrayImg<UnsignedShortType, ShortArray> img0 = new ArrayImg(lShortArray, dims, new Fraction());
        UnsignedShortType ust = new UnsignedShortType(img0);
        img0.setLinkedType(ust);

        ImgSaver is = new ImgSaver();
        System.out.println("[UtilsTests]: saving a test .tif image.");
        try {
            is.saveImg("resources/img/testImg.tif", img0);
        } catch (Exception e) {
            e.printStackTrace();
        }




        boolean flag = true;
        int mask1 = 0B1111111100000000;
        int mask2 = 0B0000000011111111;

        System.out.println("[UtilsTests]: Testing little endianness...");
        byte[] barr = tiff16bitToByteArray("resources/img/testImg.tif", Endianness.LE);
        System.out.println("[UtilsTests]: Loaded byte array size: " + barr.length);
        assertTrue(barr.length == 2*shArr.length);

        for (int i=0; i<size; i++){
            int v1 = (barr[2*i]&mask2);
            int v2 = (barr[2*i+1] <<8)&mask1;
            int value =  v1+v2;

            if (shArr[i] != (short)value){
                flag=false;
                System.out.println(String.format("[UtilTests]: Assertion failed: initial value - %d, loaded value - %d (from bytes %d and %d)",
                        shArr[i], value, barr[2*i], barr[2*i+1] ));

            }
        }

        assertTrue(flag);
        System.out.println("[UtilsTests]: Success.");

        System.out.println("[UtilsTests]: Testing big endianness...");

        barr = tiff16bitToByteArray("resources/img/testImg.tif", Endianness.BE);
        for (int i=0; i<size; i++){
            int v1 = (barr[2*i+1]&mask2);
            int v2 = (barr[2*i] <<8)&mask1;
            int value =  v1+v2;

            if (shArr[i] != (short)value){
                flag=false;
                System.out.println(String.format("[UtilTests]: Assertion failed: initial value - %d, loaded value - %d (from bytes %d and %d)",
                        shArr[i], value, barr[2*i], barr[2*i+1] ));

            }
        }



        assertTrue(flag);
        System.out.println("[UtilsTests]: Success.");



    }
}
