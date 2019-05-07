package com.xeomar.www.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith( SpringRunner.class )
public class V2DownloadControllerTest {

	private String ROOT = "/download/stable";

	private String API = ROOT + "/v2";

	private MockMvc mvc;

	@MockBean
	V2DownloadProviderFactory factory;

	@Before
	public void setup() {
		Path root = Paths.get( "source/test/repos/xeo" ).toAbsolutePath();
		Map<String, V2DownloadProvider> providers = new HashMap<>();
		providers.put( "stable", new V2LocalDownloadProvider( root.resolve( "stable" ) ) );
		providers.put( "latest", new V2LocalDownloadProvider( root.resolve( "latest" ) ) );
		System.err.println( "Mock provider count: " + providers.size() );
		when( factory.getProviders() ).thenReturn( providers );
	}

	@Autowired
	public void setMockMvc( MockMvc mvc ) {
		this.mvc = mvc;
	}

	@Test
	public void testInvalidArtifact() throws Exception {
		mvc.perform( MockMvcRequestBuilders.head( API + "/invalid/product/pack" ) ).andExpect( status().is4xxClientError() );
	}

	@Test
	public void testInvalidPlatform() throws Exception {
		mvc.perform( MockMvcRequestBuilders.head( API + "/mouse/invalid/product/pack" ) ).andExpect( status().is4xxClientError() );
	}

	@Test
	public void testInvalidAsset() throws Exception {
		mvc.perform( MockMvcRequestBuilders.head( API + "/mouse/invalid/pack" ) ).andExpect( status().is4xxClientError() );
	}

	@Test
	public void testInvalidType() throws Exception {
		mvc.perform( MockMvcRequestBuilders.head( API + "/mouse/product/invalid" ) ).andExpect( status().is4xxClientError() );
	}

	@Test
	public void testGetMetadata() throws Exception {
		MvcResult result = mvc.perform( MockMvcRequestBuilders.head( API + "/mouse/product/pack" ) ).andExpect( status().isOk() ).andReturn();
		verify( factory, times( 1 ) ).getProviders();

		assertThat( result.getResponse().getHeader( "group" ), is( "com.xeomar" ) );
		assertThat( result.getResponse().getHeader( "artifact" ), is( "mouse" ) );
		assertThat( result.getResponse().getHeader( "platform" ), is( nullValue() ) );
		assertThat( result.getResponse().getHeader( "asset" ), is( "product" ) );
		assertThat( result.getResponse().getHeader( "format" ), is( "pack" ) );
		assertThat( result.getResponse().getHeader( "name" ), is( "Mouse" ) );
		assertThat( result.getResponse().getHeader( "version" ), is( "0.0u0" ) );
	}

}