package com.avereon.www.download;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class V2DownloadProviderFactoryImpl implements V2DownloadProviderFactory {

	@Override
	public Map<String, V2DownloadProvider> getProviders() {
		Map<String, V2DownloadProvider> providers = new HashMap<>();
		providers.put( "stable", new V2LocalDownloadProvider( "/opt/avn/store/stable" ) );
		providers.put( "latest", new V2LocalDownloadProvider( "/opt/avn/store/latest" ) );
		return providers;
	}

}