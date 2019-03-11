package com.xeomar.web.root;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LocalStoreDownloadProviderTest {

	private LocalStoreDownloadProvider provider;

	private String artifact = "xenon";

	private String category = "product";

	private String type = "card";

	@Before
	public void setup() {
		provider = mock( LocalStoreDownloadProvider.class );

		// Needed because the methods are mocked
		when( provider.getDownloads( anyString(), anyString(), anyString(), anyString(), or( isNull(), anyString() ) ) ).thenCallRealMethod();
		when( provider.getDownloads( anyList(), anyString(), anyString(), anyString(), or( isNull(), anyString() ) ) ).thenCallRealMethod();
		when( provider.getDownloadKey( anyString(), anyString(), anyString(), anyString(), or( isNull(), anyString() ) ) ).thenCallRealMethod();
	}

	@Test
	public void testGetLatestDownload() throws Exception {
		String channel = "latest";
		String platform = "linux";

		// Execute the method
		List<ProductDownload> downloads = provider.getDownloads( artifact, category, type, channel, platform );

		assertTrue( "No downloads retrieved", downloads.size() > 0 );
		assertThat( downloads.get( 0 ).getKey(), is( channel + "-xenon-" + platform + "-product-card" ) );
		//		assertThat( downloads.get( 0 ).getVersion().toString(), is( "0.8-SNAPSHOT" ) );
		//		assertThat( downloads.get( 0 ).getGroupId(), is( group ) );
		assertThat( downloads.get( 0 ).getArtifact(), is( artifact ) );
		assertThat( downloads.get( 0 ).getCategory(), is( category ) );
		assertThat( downloads.get( 0 ).getType(), is( type ) );
		//		assertThat( downloads.get( 0 ).getName(), is( name ) );
		assertThat( downloads.get( 0 ).getLink(), is( "file:///opt/xeo/store/" + channel + "/" + artifact + "/" + platform + "/" + category + "." + type ) );
		assertThat( downloads.size(), is( 1 ) );
	}

	@Test
	public void testGetStableDownload() throws Exception {
		String channel = "stable";
		String platform = "windows";

		// Execute the method
		List<ProductDownload> downloads = provider.getDownloads( artifact, category, type, channel, platform );

		assertTrue( "No downloads retrieved", downloads.size() > 0 );
		assertThat( downloads.get( 0 ).getKey(), is( channel + "-xenon-" + platform + "-product-card" ) );
		assertThat( downloads.get( 0 ).getChannel().toString(), is( "stable" ) );
		//		assertThat( downloads.get( 0 ).getGroupId(), is( group ) );
		assertThat( downloads.get( 0 ).getArtifact(), is( artifact ) );
		assertThat( downloads.get( 0 ).getCategory(), is( category ) );
		assertThat( downloads.get( 0 ).getType(), is( type ) );
		//		assertThat( downloads.get( 0 ).getName(), is( name ) );
		assertThat( downloads.get( 0 ).getLink(), is( "file:///opt/xeo/store/" + channel + "/" + artifact + "/" + platform + "/" + category + "." + type ) );
		assertThat( downloads.size(), is( 1 ) );
	}

	@Test
	public void testGetLatestDownloadWithoutPlatform() throws Exception {
		String channel = "latest";
		String platform = null;

		// Execute the method
		List<ProductDownload> downloads = provider.getDownloads( artifact, category, type, channel, platform );

		assertTrue( "No downloads retrieved", downloads.size() > 0 );
		assertThat( downloads.get( 0 ).getKey(), is( channel + "-xenon-product-card" ) );
		//		assertThat( downloads.get( 0 ).getVersion().toString(), is( "0.8-SNAPSHOT" ) );
		//		assertThat( downloads.get( 0 ).getGroupId(), is( group ) );
		assertThat( downloads.get( 0 ).getArtifact(), is( artifact ) );
		assertThat( downloads.get( 0 ).getCategory(), is( category ) );
		assertThat( downloads.get( 0 ).getType(), is( type ) );
		//		assertThat( downloads.get( 0 ).getName(), is( name ) );
		assertThat( downloads.get( 0 ).getLink(), is( "file:///opt/xeo/store/" + channel + "/" + artifact + "/" + category + "." + type ) );
		assertThat( downloads.size(), is( 1 ) );
	}

}
