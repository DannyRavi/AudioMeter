package com.quaap.audiometer;

/**
 *   Copyright 2016 Tom Kliethermes
 *
 *   This file is part of AudioMeter.
 *
 *   AudioMeter is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   AudioMeter is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with AudioMeter.  If not, see <http://www.gnu.org/licenses/>.
 */


public enum LevelMethod {
    dBFS, SqrtRMS, RMS, LogRMS, Max, Avg;

    public double[] getTicks(int levels) {

        //generate the raw pcm data
        double step = Short.MAX_VALUE/(double)levels;

        double[] retdata = new double[levels];

        for (int i=0; i<levels; i++) {
            short v = (short)((i+1)*step);
            retdata[i] = calculate(new short[]{v});

        }
        return retdata;
    }

    public double calculate(short[] data) {
        return calculate(data, data.length);
    }

    public double calculate(short[] data, int length) {
        double max = 0;
        double avg = 0;
        double rmssum = 0;
        for (int i=0; i<length; i++) {
            short dat = data[i];
            int abs;
            switch (this) {
                case RMS:
                case LogRMS:
                case SqrtRMS:
                    rmssum += dat*dat; break;
                case Avg:
                    avg += Math.abs(dat); break;
                case dBFS:
                case Max:
                    abs = Math.abs(dat);
                    if (abs > max) max = abs;

            }
        }

        double rmsavg;
        double resultval = 0;
        switch (this) {
            case Max:
                resultval = max; break;
            case Avg:
                resultval = avg/length; break;
            case LogRMS:
                rmsavg = Math.sqrt(rmssum/length);
                resultval = Math.log(rmsavg); break;
            case SqrtRMS:
                rmsavg = Math.sqrt(rmssum/length);
                resultval = Math.sqrt(rmsavg); break;
            case RMS:
                rmsavg = Math.sqrt(rmssum/length);
                resultval = rmsavg; break;
            case dBFS:
                resultval = 20*Math.log10(max/Short.MAX_VALUE); break;

        }
        return resultval;
    }
}