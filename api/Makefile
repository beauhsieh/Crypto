GRADLE :=gradlew

default: clean build_prod

test:
	./$(GRADLE) --console=verbose test

build_dev: clean
	./$(GRADLE) --console=verbose compileJava build -PspringProfiles=dev

build_prod:
	./$(GRADLE) --console=verbose compileJava build -PspringProfiles=prod

clean:
	@echo 'Remove all build artifact and log files ...'
	# rm -rfv logs/ bin/ temp/ .classpath

	@echo 'Clean gradle build folder ...'
	./$(GRADLE) --console=verbose clean