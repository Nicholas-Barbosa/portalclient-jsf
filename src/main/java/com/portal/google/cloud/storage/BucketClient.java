package com.portal.google.cloud.storage;

import java.util.List;
import java.util.stream.Stream;

import com.google.cloud.storage.Blob;

public interface BucketClient {

	Blob getObject(String blob);

	Stream<Blob> getObjects(List<String> blobs);
}
