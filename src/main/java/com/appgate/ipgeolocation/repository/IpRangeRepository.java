package com.appgate.ipgeolocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appgate.ipgeolocation.model.IpRange;


public interface IpRangeRepository  extends JpaRepository<IpRange, Long> {
}
