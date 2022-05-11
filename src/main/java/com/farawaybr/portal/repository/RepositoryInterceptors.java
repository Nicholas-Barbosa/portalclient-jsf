package com.farawaybr.portal.repository;

import com.farawaybr.portal.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.farawaybr.portal.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;

@NotFoundOptionalEmptyJoinPointCut
@NotAuthorizedJoinPointCut
public class RepositoryInterceptors {

}
