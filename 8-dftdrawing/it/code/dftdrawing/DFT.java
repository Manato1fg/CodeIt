package it.code.dftdrawing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mnt1fg.moonlit.*;

public class DFT {

    public static FourierData[] dft(ArrayList<Double> data) {
        int N = data.size();
        FourierData[] fourierTransform = new FourierData[N];
        for (int k = 0; k < N; k++) {
            ComplexNumber cn = new ComplexNumber(0.0, 0.0);

            for (int n = 0; n < N; n++) {
                ComplexNumber cn2 = new ComplexNumber(Math.cos(2 * n * k * Math.PI / N),
                        -Math.sin(2 * n * k * Math.PI / N));
                cn2.multiply(data.get(n));
                cn.add(cn2);
            }
            cn.multiply(1 / (double) N);
            fourierTransform[k] = new FourierData(cn, k);
        }
        List<FourierData> _data = Arrays.asList(fourierTransform);
        Collections.sort(_data, (a, b) -> {
            return (int) (b.cn.amp - a.cn.amp);
        });
        return _data.toArray(new FourierData[0]);
    }

    static class FourierData {

        ComplexNumber cn;
        int frequency;

        public FourierData(ComplexNumber cn, int frequency) {
            this.cn = cn;
            this.frequency = frequency;
        }
    }
}