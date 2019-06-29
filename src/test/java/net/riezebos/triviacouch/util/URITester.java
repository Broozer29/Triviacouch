package net.riezebos.triviacouch.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

class URITester {

	@Test
	void test() throws URISyntaxException {

		String databaseUrl = "jdbc:postgresql://ec2-54-228-246-214.eu-west-1.compute.amazonaws.com:5432/d94abodpb6rlmh?user=giwcxbsjzreveu&password=5855850949f513194c3403936e69f367ef5f896b6dc33c27702857d2b45b47af&sslmode=require";

		URI dbUri = new URI(databaseUrl);

		String userInfo = dbUri.getUserInfo();
	}

}
