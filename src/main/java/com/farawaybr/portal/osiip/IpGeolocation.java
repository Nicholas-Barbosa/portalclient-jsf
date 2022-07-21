package com.farawaybr.portal.osiip;

import java.util.Optional;

public interface IpGeolocation {

	Optional<IpInfo> findByIp(String ip);
}
