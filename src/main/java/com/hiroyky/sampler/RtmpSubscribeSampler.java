package com.hiroyky.sampler;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class RtmpSubscribeSampler implements JavaSamplerClient {

    static final String SOURCE_URL = "Source RTMP URL";
    static final String FFMPEG_PATH = "FFMPEG Binary Path";

    @Override
    public void setupTest(JavaSamplerContext context) {

    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        String source = context.getParameter(SOURCE_URL);
        String ffmpegBin = context.getParameter(FFMPEG_PATH);

        StringBuilder builder = new StringBuilder();
        builder.append("source: ").append(source).append("\n");

        SampleResult result = new SampleResult();
        try {
            RtmpSubscriber subscriber = new RtmpSubscriber(source, ffmpegBin);
            result.sampleStart();
            subscriber.run();
            result.sampleEnd();
            result.setResponseCodeOK();
            result.setResponseMessage(builder.toString());
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setResponseCode("500");
            builder.append("error: ").append(e.getMessage()).append("\n");
            result.setResponseMessage(builder.toString());
        }
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {

    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument(SOURCE_URL, "rtmp://localhost:1935/app/stream_key");
        defaultParameters.addArgument(FFMPEG_PATH, "ffmpeg");
        return defaultParameters;
    }
}
