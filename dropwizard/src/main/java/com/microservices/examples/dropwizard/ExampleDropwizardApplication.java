package com.microservices.examples.dropwizard;

import javax.ws.rs.client.Client;

import com.microservices.examples.dropwizard.resources.GreeterRestResource;
import com.microservices.examples.dropwizard.resources.GreeterSayingFactory;
import com.microservices.examples.dropwizard.resources.HolaRestResource;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleDropwizardApplication extends Application<ExampleDropwizardConfiguration> {

	public static void main(final String[] args) throws Exception {
		new ExampleDropwizardApplication().run(args);
	}

	@Override
	public String getName() {
		return "ExampleDropwizard";
	}

	@Override
	public void initialize(final Bootstrap<ExampleDropwizardConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
	}

	@Override
	public void run(final ExampleDropwizardConfiguration configuration, final Environment environment) {
		environment.jersey().register(new HolaRestResource(configuration.getSayingFactory().getSaying()));

		// greeter service
		GreeterSayingFactory greeterSayingFactory = configuration.getGreeterSayingFactory();
		Client greeterClient = new JerseyClientBuilder(environment).using(greeterSayingFactory.getJerseyClientConfig())
				.build("greeterClient");
		environment.jersey().register(new GreeterRestResource(greeterSayingFactory.getSaying(),
				greeterSayingFactory.getHost(), greeterSayingFactory.getPort(), greeterClient));
	}

}
