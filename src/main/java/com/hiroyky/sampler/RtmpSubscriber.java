package com.hiroyky.sampler;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;

import java.io.IOException;

public class RtmpSubscriber {
    String source;
    String ffmpegBin;
    FFmpegJob ffmpegJob;
    public RtmpSubscriber(String source, String ffmpegBin) {
        this.source = source;
        this.ffmpegBin = ffmpegBin;
    }


    public String init() throws IOException {
        FFmpegBuilder builder = new FFmpegBuilder();
        builder.setInput(this.source).setFormat("mp4").addOutput("/dev/null");
        FFmpegExecutor executor = new FFmpegExecutor(new FFmpeg(this.ffmpegBin));
        this.ffmpegJob = executor.createJob(builder);
        return builder.build().toString();
    }

    public void run() {
        System.out.println("run");
        this.ffmpegJob.run();
    }
}
