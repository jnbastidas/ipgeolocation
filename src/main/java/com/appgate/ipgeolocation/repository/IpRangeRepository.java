package com.appgate.ipgeolocation.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.appgate.ipgeolocation.model.IpRange;


public interface IpRangeRepository  extends JpaRepository<IpRange, Long> {

	@Query("SELECT r FROM IpRange r WHERE :ip BETWEEN r.ipFrom and r.ipTo")
	Set<IpRange> findContainIp(final Long ip);

}
