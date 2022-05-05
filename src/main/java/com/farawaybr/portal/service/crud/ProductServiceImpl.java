package com.farawaybr.portal.service.crud;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.cdi.qualifier.ProductBucket;
import com.farawaybr.portal.dto.ProductPageDTO;
import com.farawaybr.portal.dto.ProductTechDetailJson;
import com.farawaybr.portal.dto.ProductWrapper;
import com.farawaybr.portal.google.cloud.storage.BucketClient;
import com.farawaybr.portal.repository.ProductRepository;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductPriceData;
import com.farawaybr.portal.vo.ProductTechDetail;
import com.farawaybr.portal.vo.CustomerOnOrder.CustomerType;
import com.farawaybr.portal.vo.ProductImage.ImageInfoState;
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
			String sellerType, CustomerType customerType) {
		Future<Blob> ftBlob = bucketClient.getAsyncObject(code);

		try {
			
			Future<ProductWrapper> ftProduct = customerType.equals(CustomerType.NORMAL)
					? productRepository.findByCodeAsync(code, customerCode, customerStore)
					: productRepository.findByCodeForProspectAsync(code, state, sellerType);
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
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return Optional.empty();
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
