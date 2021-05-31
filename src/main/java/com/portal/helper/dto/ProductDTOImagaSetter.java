package com.portal.helper.dto;

import javax.enterprise.context.ApplicationScoped;

import com.portal.cdi.qualifier.ProductBucket;
import com.portal.google.cloud.storage.BucketClient;

@ApplicationScoped
public class ProductDTOImagaSetter {

	@ProductBucket
	private BucketClient bucketClient;

}
