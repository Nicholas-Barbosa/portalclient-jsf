package com.portal.google.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

class BucketClientTest {

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
		Blob object = bucket.get("imagens_tratadas/AX001.JPG");
		byte[] content = object.getContent();
		System.out.println("downalod link " + object.getMediaLink());
		System.out.println("length " + content.length);

	}

}
