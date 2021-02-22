package com.appgate.ipgeolocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appgate.ipgeolocation.model.Localization;


public interface LocalizationRepository extends JpaRepository<Localization, Long> {
}
