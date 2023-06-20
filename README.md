# api-test-framework

Fork this repo to start testing your REST APIs!

## Features:
* RestAssured
* jUnit5
* Allure reporting
* Schema-based contract testing
* Run tests against multiple environments
* Parallel testing by default

## Structure and usage
The `main` package contains classes that help interface with the API under test. Add a package for you API under the `services` package, with the following classes:
* {MyService}Api: collection of calls to your API. All methods should return a RestAssured Response.
* Endpoints: collection of endpoints that exist in your API
* PropertyNames: collection of properties returned by your API

The `utilities` package contains the following classes:
* Request:  Interfaces with RestAssured and helps to build requests to your API. Can be extended/modified to suit your specific use cases if not already covered.
* TestProperties: Helper class to retrieve properties from `.properties` files.

The repo contains a couple of example API services for reference: `numbers_api` & `restful_api`.

`resources`:
* Contains `.properties` files where environment-specific properties can be stored. You can add additional files for other environments you wish to test against.

## Writing tests
Once you've set up your API calls in the main package, you can start using them in your tests. 

In the `test` package, add a package for you API under the `services` package. Add a `*Test` class and start writing tests!

Again, there are a couple of example tests included in the repo.

## Running tests
`mvn clean test` will execute all tests against the default environment.
The environment can be specified by adding `-Denvironment={environment}`, with possible {environment} values of "dev", "staging" or "prod". 

