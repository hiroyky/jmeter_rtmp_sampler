package com.hiroyky.sampler;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;

import java.io.IOException;

public class RtmpPublisher {

    String source;
    String destination;
    String ffmpegBin;
    FFmpegJob ffmpegJob;
    Boolean loop;
    public RtmpPublisher(String src, String destination, String ffmpegBin, boolean loop) {
        this.source = src;
        this.destination = destination;
        this.ffmpegBin = ffmpegBin;
        this.ffmpegJob = null;
        this.loop = loop;
    }

    public String init()  throws IOException {
        FFmpegBuilder builder = new FFmpegBuilder();
        builder
                .setInput(this.source)
                .addExtraArgs("-re")
                .addOutput(this.destination)
                .setFormat("flv");

        if (this.loop) {
            builder.addExtraArgs("-stream_loop", "-1");
        }

        FFmpegExecutor executor = new FFmpegExecutor(new FFmpeg(this.ffmpegBin));
        this.ffmpegJob = executor.createJob(builder);

        return builder.build().toString();
    }

    public void run() {
        this.ffmpegJob.run();
    }
}
