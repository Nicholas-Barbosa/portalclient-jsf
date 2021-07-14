package com.portal.java.service;

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
import com.portal.java.cdi.qualifier.ProductBucket;
import com.portal.java.dto.Product;
import com.portal.java.dto.ProductJsonWrapper;
import com.portal.java.dto.ProductPageDTO;
import com.portal.java.google.cloud.storage.BucketClient;
import com.portal.java.repository.ProductRepository;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7904939451132445103L;
	@Inject
	private ProductRepository productRepository;

	@Inject
	@ProductBucket
	private BucketClient bucketClient;

	@Override
	public Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		// TODO Auto-generated method stub
		return productRepository.findByDescription(page, pageSize, descriptio);
	}

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Future<Blob> ftBlob = bucketClient.getAsyncObject(code);
		Future<ProductJsonWrapper> ftProduct = productRepository.findByCodeAsync(code, customerCode, store);
		try {
			ProductJsonWrapper response = ftProduct.get();
			if (response != null) {
				byte[] image = getBlobStreamImageContent(ftBlob);
				Product product = response.getProducts().get(0);
				product.setImage(image);
				return Optional.of(product);
			}
			ftBlob.cancel(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

//	@Override
//	public void loadImage(Collection<ProductJsonWrapper> products) {
//		Stream<Blob> productBlobs = bucketClient.getObjects(products.parallelStream().map(p -> p.getCommercialCode())
//				.collect(CopyOnWriteArrayList::new, List::add, List::addAll));
//		Map<String, byte[]> productsBytes = productBlobs.filter(b -> b != null)
//				.collect(Collectors.toConcurrentMap(k -> {
//					Path path = Paths.get(k.getName());
//					String code = removeExtension(path.getFileName().toString());
//					return code;
//				}, v -> v.getContent()));
//
//		products.parallelStream().forEach(p -> {
//			ImageInfo imgInfo = p.getInfo().getImageInfo();
//			imgInfo.setImageStreams(productsBytes.get(p.getCommercialCode()));
//		});
//	}

	@Override
	public void loadImage(Product product) {
		Blob object = bucketClient.getObject(product.getCommercialCode());
		byte[] imageStreams = object == null ? new byte[0] : object.getContent();
		product.setImage(imageStreams);

	}

	private String removeExtension(String path) {
		return path.substring(0, path.lastIndexOf("."));
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

	@Override
	public Optional<Product> findByCodeForProspect(String code, String state, String sellerType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Future<Blob> ftBlob = bucketClient.getAsyncObject(code);
		Future<ProductJsonWrapper> ftProduct = productRepository.findByCodeForProspectAsync(code, state, sellerType);
		try {
			ProductJsonWrapper response = ftProduct.get();
			if (response != null) {
				byte[] image = getBlobStreamImageContent(ftBlob);
				Product product = response.getProducts().get(0);
				product.setImage(image);
				return Optional.of(product);
			}
			ftBlob.cancel(true);
		} catch (ExecutionException | InterruptedException e) {
			if (e instanceof InterruptedException)
				e.printStackTrace();
		}
		return Optional.empty();
	}

}
