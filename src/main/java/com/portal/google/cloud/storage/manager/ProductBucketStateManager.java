package com.portal.google.cloud.storage.manager;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class ProductBucketStateManager implements BucketStateManager,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1333222103994514033L;
	private final Map<String, ProductLifeCycleAttr> productsImages = new ConcurrentHashMap<>();

	@Override
	public void initLoadingImage(String image) {
		ProductLifeCycleAttr imageAttr = null;
		if (!productsImages.containsKey(image)) {
			productsImages.put(image, new ProductLifeCycleAttr());
		}
		imageAttr = productsImages.get(image);
		imageAttr.imageRequested();
		imageAttr.imageInitLoading();
	}

	@Override
	public void loadedImage(String image) {
		productsImages.get(image).imageLoaded();
	}

	@Override
	public State getState(String image) {
		if (productsImages.containsKey(image))
			return productsImages.get(image).state;
		return null;
	}

	private class ProductLifeCycleAttr {

	
		final AtomicInteger loadedCount;
		final AtomicInteger requestCountToLoad;
		State state;

		public ProductLifeCycleAttr() {
			super();
			this.loadedCount = new AtomicInteger(0);
			this.requestCountToLoad = new AtomicInteger(0);
			this.state = State.ALIVE;
		}

		public void imageInitLoading() {
			this.state = State.LOADING;
		}

		public void imageLoaded() {
			this.state = State.LOADED;
			increaseLoadedCount();
		}

		public void imageRequested() {
			this.requestCountToLoad.incrementAndGet();
		}

		public void increaseLoadedCount() {
			this.loadedCount.incrementAndGet();
		}

	}

	public static enum State {
		ALIVE, LOADING, LOADED,LOADED_NOT_FOUND
	}

}
