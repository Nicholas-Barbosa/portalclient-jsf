package com.portal.java.google.cloud.storage;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import com.google.cloud.storage.Blob;

public interface BucketClient {

	Blob getObject(String blob);

	Future<Blob> getAsyncObject(String blob);
	
	Stream<Blob> getObjects(List<String> blobs);
}