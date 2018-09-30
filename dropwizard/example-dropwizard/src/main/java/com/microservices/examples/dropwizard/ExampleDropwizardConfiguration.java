package com.microservices.examples.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.examples.dropwizard.resources.HelloSayingFactory;

import io.dropwizard.Configuration;

public class ExampleDropwizardConfiguration extends Configuration {

	private HelloSayingFactory sayingFactory;

	@JsonProperty("helloapp")
	public HelloSayingFactory getSayingFactory() {
		return sayingFactory;
	}

	@JsonProperty("helloapp")
	public void setSayingFactory(HelloSayingFactory sayingFactory) {
		this.sayingFactory = sayingFactory;
	}

}
