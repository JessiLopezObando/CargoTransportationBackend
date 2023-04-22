plugins {
	java
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "co.com.cargomaster"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	// email service
	implementation ("org.springframework.boot:spring-boot-starter-mail")
	// mongoDB validation
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	// mapper
	implementation ("org.reactivecommons.utils:object-mapper:0.1.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
