package com.portal.client.repository;

import com.portal.client.cdi.aop.annotations.IllegalResponsePointCutJoinPoint;
import com.portal.client.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.portal.client.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;

@IllegalResponsePointCutJoinPoint
@NotFoundOptionalEmptyJoinPointCut
@NotAuthorizedJoinPointCut
public class RepositoryInterceptors {

}
