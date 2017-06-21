[![Build Status](https://travis-ci.org/felixreimann/jreliability.svg?branch=master)](https://travis-ci.org/felixreimann/jreliability)
[![Coverage Status](https://coveralls.io/repos/github/felixreimann/jreliability/badge.svg?branch=master)](https://coveralls.io/github/felixreimann/jreliability?branch=master)

# JReliability - The Java-based Reliability Library

JReliability allows to derive several reliability-related measures like Mean-Time-To-Failure (MTTF) or Mission-Time (MT) of complex systems that are modeled using Boolean functions, efficiently encoded in Binary Decision Diagrams (BDDs).

## Usage
JReliability is meant to be used as library. Nonetheless, there is a small demo application you can use to get an initial idea and to demonstrate the included viewers.

### Linux/Unix
Run:

	./start.sh

### Windows
Run:

	./start.bat

## Developer
JReliability uses Gradle. Run

	./gradlew tasks

to show all available tasks.

Use

	./gradlew eclipse

to create the required .project and .classpath files to import the project in Eclipse.

Use

	./gradlew run

to execute the demo application.

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

JReliability was formerly hosted at https://sourceforge.net/projects/jreliability/

## Credits

Brought to you by
* Michael Glaß
* Felix Reimann
* Martin Lukasiewycz
* Faramarz Khosravi

## License

JReliability uses [LGPLv3](./LICENSE).
