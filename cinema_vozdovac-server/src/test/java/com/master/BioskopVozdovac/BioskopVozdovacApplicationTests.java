package com.master.BioskopVozdovac;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@TestPropertySource(properties = {
        "aws.access.key=test",
        "aws.secret.key=test",
        "aws.region=us-east-1",
        "aws.s3.bucket=test-bucket",
        "app.client.url=http://localhost:3000",
        "STRIPE_SECRET_KEY=sk_test_dummy_key_for_testing"
})
class BioskopVozdovacApplicationTests {

//	@Test
//	void contextLoads() {
//	}

}
