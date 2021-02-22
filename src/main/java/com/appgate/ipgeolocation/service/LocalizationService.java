package com.appgate.ipgeolocation.service;

import java.util.Set;

import com.appgate.ipgeolocation.model.Localization;


public interface LocalizationService {
	Set<Localization> getLocationsByIp(final String ip);
}
