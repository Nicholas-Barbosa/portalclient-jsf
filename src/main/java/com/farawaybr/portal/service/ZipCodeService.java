package com.farawaybr.portal.service;

import java.util.Optional;

import com.farawaybr.portal.pojo.ZipCode;

public interface ZipCodeService {

	Optional<ZipCode> find(String cep);
}
