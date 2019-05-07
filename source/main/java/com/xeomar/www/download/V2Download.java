package com.xeomar.www.download;

import com.xeomar.util.TextUtil;

public class V2Download {

	private String key;

	private String group;

	private String artifact;

	private String platform;

	private String asset;

	private String format;

	private String name;

	private String version;

	public V2Download(){}

	public V2Download( String artifact, String platform, String asset, String format ) {
		this.key = key( artifact, platform, asset, format );
		this.artifact = artifact;
		this.platform = platform;
		this.asset = asset;
		this.format = format;
	}

	public String getKey() {
		return key;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup( String group ) {
		this.group = group;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact( String artifact ) {
		this.artifact = artifact;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform( String platform ) {
		this.platform = platform;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset( String asset ) {
		this.asset = asset;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat( String format ) {
		this.format = format;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	static String key( String artifact, String platform, String asset, String format ) {
		StringBuilder builder = new StringBuilder();
		builder.append( artifact );
		if( !TextUtil.isEmpty( platform ) ) builder.append( "-" ).append( platform );
		builder.append( "-" ).append( asset );
		builder.append( "-" ).append( format );
		return builder.toString();
	}

	static String resolveFormat( String format ) {
		switch( format ) {
			case "pack": return "jar";
		}
		return format;
	}

}
