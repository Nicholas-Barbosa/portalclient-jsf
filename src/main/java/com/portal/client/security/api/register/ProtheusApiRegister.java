package com.portal.client.security.api.register;

import com.portal.client.security.api.ProtheusCompanyApiEnv;

public interface ProtheusApiRegister extends ApiRegister{
	
	ApiRegister companyEnv(ProtheusCompanyApiEnv company);
}
