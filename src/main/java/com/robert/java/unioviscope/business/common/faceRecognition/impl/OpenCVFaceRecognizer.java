package com.robert.java.unioviscope.business.common.faceRecognition.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Clase que implementa la interfaz FaceRecognizer.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer
 */
public class OpenCVFaceRecognizer
		implements com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer {

//	private static final String SAMPLE_IMAGE = "face-recognizer/-2.jpg";
//	private static final Integer SAMPLE_IMAGE_LABEL = 0;
//	private static final Integer NO_MATCHES = -1;
//	private static final Double THRESHOLD = 20.0;
//
//	private static FaceRecognizer faceRecognizer;
//	private AtomicLong counter;

	public OpenCVFaceRecognizer() throws IOException {
//		 counter = new AtomicLong(1);
//		 setUpFaceRecognizer();
	}

	@Override
	public Integer recognize(InputStream image) throws IOException {
		Random random = new Random();
	    return random.nextBoolean() ? -1 : 1;
	}

//	@Override
//	public Integer recognize(InputStream image) throws IOException {
//
//		Long label = counter.getAndIncrement();
//		Mat original = imdecode(new Mat(IOUtils.toByteArray(image)), 1);
//		Mat processed = new Mat();
//		cvtColor(original, processed, COLOR_BGRA2GRAY);
//		equalizeHist(processed, processed);
//
//		int predicted = faceRecognizer.predict_label(processed);
//
//		if (predicted == NO_MATCHES) {
//			File[] imageFiles = new File[1];
//			MatVector images = new MatVector(imageFiles.length);
//			Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
//			IntBuffer labelsBuffer = labels.createBuffer();
//			images.put(0, processed);
//			labelsBuffer.put(0, Integer.valueOf(label.toString()));
//			faceRecognizer.train(images, labels);
//		}
//
//		return predicted;
//	}

//	private void setUpFaceRecognizer() throws IOException {
//
//		InputStream sample = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_IMAGE);
//
//		Mat original = imdecode(new Mat(IOUtils.toByteArray(sample)), 1);
//		Mat processed = new Mat();
//		cvtColor(original, processed, COLOR_BGRA2GRAY);
//		equalizeHist(processed, processed);
//
//		File[] imageFiles = new File[1];
//		MatVector images = new MatVector(imageFiles.length);
//		Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
//		IntBuffer labelsBuffer = labels.createBuffer();
//		images.put(0, processed);
//		labelsBuffer.put(0, SAMPLE_IMAGE_LABEL);
//
//		faceRecognizer = createLBPHFaceRecognizer();
//		faceRecognizer.setThreshold(THRESHOLD);
//		faceRecognizer.train(images, labels);
//	}
}
