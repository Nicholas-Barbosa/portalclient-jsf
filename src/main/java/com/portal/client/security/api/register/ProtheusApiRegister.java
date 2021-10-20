package com.portal.client.security.api.register;

import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.security.user.User;

public interface ProtheusApiRegister extends ApiRegister<ProtheusApiRegister> {

	ProtheusApiRegister companyEnv(ProtheusCompanyApiEnv company);

	ProtheusApiRegister setUser(RepresentativeUser user);

	@Override
	default ProtheusApiRegister setUser(User user) {
		System.out.println("set user!");
		return null;
	}

}
