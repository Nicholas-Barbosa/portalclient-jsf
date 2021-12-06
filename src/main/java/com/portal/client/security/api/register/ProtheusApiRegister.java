package com.portal.client.security.api.register;

import com.portal.client.dto.RepresentativeData;
import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.security.user.RepresentativeUser;

public interface ProtheusApiRegister extends ApiRegister<ProtheusApiRegister> {

	ProtheusApiRegister companyEnv(ProtheusCompanyApiEnv company);

	ProtheusApiRegister setUser(RepresentativeUser user);

	@Override
	default ProtheusApiRegister setUser(RepresentativeData user) {
		return null;
	}

}
