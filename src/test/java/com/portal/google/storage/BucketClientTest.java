package com.portal.google.storage;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobField;
import com.google.cloud.storage.Storage.BlobGetOption;
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

		// Bucket bucket =
		// storage.get(bucketName,BucketGetOption.fields(BucketField.NAME,BucketField.LOCATION));
		Blob object = storage.get(BlobId.of(bucketName, "budget.pdf"),
				BlobGetOption.fields(BlobField.NAME));
		byte[] content = object.getContent();
		System.out.println("downalod link " + object.getMediaLink());
		System.out.println("length " + content.length);
		System.out.println("Name " + object.getName());
	}

	@Test
	void testBulkCall() {
		Storage storage = StorageOptions.getDefaultInstance().getService();

		// The name for the new bucket
		String bucketName = "streams-portal"; // "my-new-bucket";

		// Creates the new bucket
		Bucket bucket = storage.get(bucketName);
		List<Blob> object = bucket
				.get(List.of("imagens_tratadas/AX001.JPG", "imagens_tratadas/AX003.JPG", "imagens_tratadas/AX003.JPG"));
		System.out.println("objects " + object.size());
	}
}
