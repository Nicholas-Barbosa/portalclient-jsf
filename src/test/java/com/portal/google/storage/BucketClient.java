package com.portal.google.storage;

import java.nio.file.Path;
import java.util.Base64;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.BlobGetOption;

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
		System.out.println("length " + content.length);
//		bucket.get("budget.pdf")
//				.downloadTo(Path.of("C:\\Users\\nicho\\OneDrive\\Documentos\\gCloud storage\\budget.pdf"));
		// System.out.printf("Bucket %s created.%n", bucket.getName());
	}

}
