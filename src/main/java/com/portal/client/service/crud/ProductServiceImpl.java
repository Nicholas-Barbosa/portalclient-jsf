package com.portal.client.service.crud;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.cloud.storage.Blob;
import com.portal.client.cdi.qualifier.ProductBucket;
import com.portal.client.dto.CustomerOnOrder.CustomerType;
import com.portal.client.dto.Product;
import com.portal.client.dto.ProductPage;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.dto.ProspectCustomerOnOrder.SellerType;
import com.portal.client.google.cloud.storage.BucketClient;
import com.portal.client.repository.ProductRepository;

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
	public Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		// TODO Auto-generated method stub
		return productRepository.findByDescription(page, pageSize, descriptio);
	}

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String customerStore, String state,
			SellerType sellerType, CustomerType customerType) {
		Future<Blob> ftBlob = bucketClient.getAsyncObject(code);

		try {
			Future<ProductPage> ftProduct = customerType == CustomerType.NORMAL
					? productRepository.findByCodeAsync(code, customerCode, customerStore)
					: productRepository.findByCodeForProspectAsync(code, state, sellerType.getType());
			ProductPage response = ftProduct.get();
			if (response != null) {
				byte[] image = getBlobStreamImageContent(ftBlob);
				Product product = response.getProducts().get(0);
				product.setImage(image);
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
		byte[] imageStreams = object == null ? new byte[0] : object.getContent();
		product.setImage(imageStreams);

	}

	private byte[] getBlobStreamImageContent(Future<Blob> blob) {
		try {
			return blob.get(500, TimeUnit.MILLISECONDS).getContent();
		} catch (TimeoutException e) {
			return new byte[1];
		} catch (NullPointerException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

}
