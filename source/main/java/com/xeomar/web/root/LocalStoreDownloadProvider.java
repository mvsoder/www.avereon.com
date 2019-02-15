package com.xeomar.web.root;

import com.xeomar.util.LogUtil;
import com.xeomar.util.TextUtil;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LocalStoreDownloadProvider extends AbstractDownloadProvider {

	private static final Logger log = LogUtil.get( MethodHandles.lookup().lookupClass() );

	private static String ROOT = "/opt/xeo/store";

	private static String GROUP = "com.xeomar";

	@Override
	public List<ProductDownload> getDownloads( String artifact, String category, String type, String channel, String platform ) {
		return getDownloads( List.of( artifact ), category, type, channel, platform );
	}

	@Override
	public List<ProductDownload> getDownloads( List<String> artifacts, String category, String type, String channel, String platform ) {
		List<ProductDownload> downloads = new ArrayList<>();

		for( String artifact : artifacts ) {
			String filename = getFilename( platform, category, type );
			Path path = Paths.get( ROOT, channel, artifact, filename );

			String key = getDownloadKey( artifact, category, type, channel, platform );
			String name = null;
			String link = path.toUri().toString();
			String md5Link = "";
			String sha1Link = "";
			downloads.add( new ProductDownload( key, GROUP, artifact, channel, category, type, platform, name, link, md5Link, sha1Link ) );
		}

		return downloads;
	}

	@Override
	public String clearCache( String artifact, String category, String type, String channel, String platform ) {
		return null;
	}

	@Override
	public String clearCache() {
		log.info( "Cache cleared for all" );
		return "Cache cleared for all";
	}

	private String getFilename( String platform, String category, String type ) {
		StringBuilder builder = new StringBuilder();

		if( !TextUtil.isEmpty( platform ) ) builder.append( platform ).append( "-" );
		builder.append( category ).append( "." ).append( type );

		return builder.toString();
	}

}
