package com.portal.client.repository;

import com.portal.client.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.portal.client.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;

@NotFoundOptionalEmptyJoinPointCut
@NotAuthorizedJoinPointCut
public class OptionalEmptyRepository {

}
