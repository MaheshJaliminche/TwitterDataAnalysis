package auth;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigBuilder {
	private static Configuration config;
	
	private static final String API_KEY = "fRbPrXm9wdrxgD9HGK4bSL3cg";
	private static final String API_SECRET = "ySqbRLEhHFM2otZki4ldNh0xbwAo9aIoSCc0YtXoziXeA4wdoO";
	private static final String ACCESS_TOKEN = "2261654132-mMIhmUjoSMw0tR8PX6N3zpc4zCRROCCbEUk9NOX";
	private static final String ACCESS_SECRET = "WxPGjoqnyfmVA3AGsyQUuCF0V1uAaBWwEaguOQU8P4Qxc";
	
	static {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(API_KEY);
		cb.setOAuthConsumerSecret(API_SECRET);
		cb.setOAuthAccessToken(ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ACCESS_SECRET);
		config = cb.build();
	}
	
	public static Configuration getConfig() {
		return config;
	}
}
