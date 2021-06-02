package com.portal.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.google.cloud.storage.Blob;
import com.portal.cdi.qualifier.ProductBucket;
import com.portal.dto.BaseProductDTO;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductInfoDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.google.cloud.storage.BucketClient;
import com.portal.repository.ProductRepository;

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

	@Resource
	private ManagedExecutorService executorService;

	@Inject
	private HttpSession session;

	@Override
	public Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize) {
		// TODO Auto-generated method stub
		return productRepository.getByDescription(page, pageSize, descriptio);
	}

	@Override
	public Optional<ProductDTO> findByCode(String code) {

		productRepository.getByCode(code);

		return Optional.empty();
	}

	@Override
	public void loadImage(Collection<? extends BaseProductDTO> products) {
		Stream<Blob> productBlobs = bucketClient.getObjects(products.parallelStream().map(p -> p.getCommercialCode())
				.collect(CopyOnWriteArrayList::new, List::add, List::addAll));
		Map<String, byte[]> productsBytes = productBlobs.filter(b -> b != null)
				.collect(Collectors.toConcurrentMap(k -> {
					Path path = Paths.get(k.getName());
					String code = removeExtension(path.getFileName().toString());
					return code;
				}, v -> v.getContent()));

		products.parallelStream().forEach(p -> {
			ProductInfoDTO info = p.getInfo();
			info.setImage(productsBytes.get(p.getCommercialCode()));
		});
	}

	@Override
	public void loadImage(ProductDTO productDTO) {
		Blob object = bucketClient.getObject(productDTO.getCommercialCode());
		byte[] imageStreams = object == null ? new byte[0] : object.getContent();
		productDTO.getInfo().setImage(imageStreams);

	}

	private String removeExtension(String path) {
		return path.substring(0, path.lastIndexOf("."));
	}

}
