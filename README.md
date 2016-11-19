# AudioMeter

A simple (and somewhat inaccurate) VU-style audio meter.  

I noticed there was no audio meter in F-Droid, so I thought I'd build one from 
scratch. I originally planned on mapping the results to decibels (dB), but, in 
researching, I discovered that it's not really possible to do so accurately. So 
I present this as a simple way to measure relative sound levels (not "loudness").

The app offers a few ways to calculate the audio level to display:


* RMS: Root Mean Square.  This is arguable the most correct, but it is a little insensitive at low volumes.
* LogRMS: Natural log of RMS: My first attempt to even out the scale.  It's a little *too* sensitive to noisy mics. 
* SqrtRMS: Squareroot of RMS: My second attempt to even out RMS: it seems to works best.  
* Max: Simply find the loudest bit of each sample. Most responsive, but will peg the scale on noisey places.
* Avg: Simple average of the sound.

