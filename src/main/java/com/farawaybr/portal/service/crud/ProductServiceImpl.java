package com.farawaybr.portal.service.crud;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;

import com.farawaybr.portal.cdi.qualifier.ProductBucket;
import com.farawaybr.portal.dto.ProductPageDTO;
import com.farawaybr.portal.dto.ProductTechDetailJson;
import com.farawaybr.portal.dto.ProductWrapper;
import com.farawaybr.portal.google.cloud.storage.BucketClient;
import com.farawaybr.portal.repository.ProductRepository;
import com.farawaybr.portal.vo.CustomerOnOrder.CustomerType;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductImage.ImageInfoState;
import com.farawaybr.portal.vo.ProductPriceData;
import com.farawaybr.portal.vo.ProductTechDetail;
import com.google.cloud.storage.Blob;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7904939451132445103L;
	private ProductRepository productRepository;
	private BucketClient bucketClient;

	@Inject
	public ProductServiceImpl(ProductRepository productRepository, @ProductBucket BucketClient bucketClient) {
		super();
		this.productRepository = productRepository;
		this.bucketClient = bucketClient;
	}

	@Override
	public Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize) {
		// TODO Auto-generated method stub
		return productRepository.findByDescription(page, pageSize, descriptio);
	}

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String customerStore, String state,
			String sellerType, CustomerType customerType) throws ExecutionException, InterruptedException {
		Future<Blob> ftBlob = bucketClient.getAsyncObject(code);

		try {

			Future<ProductWrapper> ftProduct = customerType.equals(CustomerType.NORMAL)
					? productRepository.findByCodeAsync(code, customerCode, customerStore)
					: productRepository.findByCodeForProspectAsync(code, state, sellerType);
			/*
			 * Note that calling the java.util.concurrent.Future.get() method on the
			 * returned Future instance may throw an java.util.concurrent.ExecutionException
			 * that wraps either a javax.ws.rs.ProcessingException thrown in case of an
			 * invocation processing failure or a WebApplicationException or one of its
			 * subclasses thrown in case thereceived response status code is not successful
			 * and the specified response type is not javax.ws.rs.core.Response.
			 */
			ProductWrapper response = ftProduct.get();
			if (response != null) {
				byte[] image = getBlobStreamImageContent(ftBlob);
				Product product = response.getProducts().get(0).getProduct();
				product.setImage(image, image == null ? ImageInfoState.NOT_FOUND
						: image.length == 0 ? ImageInfoState.TIMEOUT_EXCPTION : ImageInfoState.FOUND);
				ProductPriceData priceData = product.getPriceData();
				priceData.setTotalGrossValue(priceData.getUnitGrossValue());
				priceData.setTotalStValue(priceData.getUnitStValue());
				priceData.setTotalValue(priceData.getUnitValue());
				return Optional.of(product);
			}
			ftBlob.cancel(true);
			return Optional.empty();
		} catch (ExecutionException | InterruptedException e) {
			if (e.getCause() instanceof WebApplicationException) {
				WebApplicationException wae = (WebApplicationException) e.getCause();
				wae.getResponse().close();
				return Optional.empty();
			} else if (e.getCause() instanceof ResponseProcessingException) {
				ResponseProcessingException rpe = (ResponseProcessingException) e.getCause();
				rpe.getResponse().close();
			}
			throw e;

		}
	}

	@Override
	public void loadImage(Product product) {
		Blob object = bucketClient.getObject(product.getCommercialCode());
		byte[] imageStreams = object == null ? null : object.getContent();
		product.setImage(imageStreams, imageStreams == null ? ImageInfoState.NOT_FOUND : ImageInfoState.FOUND);
	}

	private byte[] getBlobStreamImageContent(Future<Blob> blob) {
		try {
			return blob.get(500, TimeUnit.MILLISECONDS).getContent();
		} catch (TimeoutException e) {
			return new byte[0];
		} catch (NullPointerException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void loadTechDetails(Product product) {
		if (product.getProductTechDetail() == null) {
			ProductTechDetailJson json = productRepository.findTechDetails(product.getCommercialCode());
			ProductTechDetail detail = json.toDetail();
			product.setProductTechDetail(detail);
			product.setLink(json.getLink());
		}
	}

	@Override
	public void findStock(Product... products) {
		productRepository.findStock(products);

	}

}
