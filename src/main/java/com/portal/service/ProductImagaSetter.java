package com.portal.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.ejb.Singleton;

import com.google.cloud.storage.Blob;
import com.portal.cdi.qualifier.ProductBucket;
import com.portal.dto.ProductWithImageDTO;
import com.portal.google.cloud.storage.BucketClient;

@Singleton
public class ProductImagaSetter {

	@ProductBucket
	private BucketClient bucketClient;

	public void setImage(Collection<? extends ProductWithImageDTO> products) {
		Stream<Blob> productBlobs = bucketClient.getObjects(products.parallelStream().map(p -> p.getCommercialCode())
				.collect(CopyOnWriteArrayList::new, List::add, List::addAll));
	}
}
