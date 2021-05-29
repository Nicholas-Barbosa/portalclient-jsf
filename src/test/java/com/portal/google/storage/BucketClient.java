package com.portal.google.storage;

import java.util.zip.Deflater;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

class BucketClient {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {

		Storage storage = StorageOptions.getDefaultInstance().getService();

		// The name for the new bucket
		String bucketName = "streams-portal"; // "my-new-bucket";

		// Creates the new bucket
		Bucket bucket = storage.get(bucketName);
		Blob object = bucket.get("budget.pdf");
		byte[] content = object.getContent();
		System.out.println("downalod link " + object.getMediaLink());
		System.out.println("length " + content.length);
//		bucket.get("budget.pdf")
//				.downloadTo(Path.of("C:\\Users\\nicho\\OneDrive\\Documentos\\gCloud storage\\budget.pdf"));
		// System.out.printf("Bucket %s created.%n", bucket.getName());
	}

	@Test
	void testCompactToGzip() {
		Storage storage = StorageOptions.getDefaultInstance().getService();

		// The name for the new bucket
		String bucketName = "streams-portal"; // "my-new-bucket";

		// Creates the new bucket
		Bucket bucket = storage.get(bucketName);
		Blob object = bucket.get("imagens_tratadas/GA1320.JPG");
		byte[] imageObject = object.getContent();
		System.out.println("Size bytes[] " + imageObject.length);

		Deflater deflater = new Deflater();
		byte[] deflatedStreams = new byte[1048576];
		deflater.setInput(imageObject);
		deflater.finish();
		System.out.println("length deflated " + +deflater.deflate(deflatedStreams));
	}
}
