package com.farawaybr.portal.repository;

import java.util.Set;

import com.farawaybr.portal.dto.ConnectionSession;

public interface ActiveSessionsRepository {

	void persist(ConnectionSession connection);

	void remove(ConnectionSession connection);

	Set<ConnectionSession> findAll();
}
