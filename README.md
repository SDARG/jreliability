[![Build & Test](https://github.com/SDARG/jreliability/actions/workflows/gradle.yml/badge.svg)](https://github.com/SDARG/jreliability/actions/workflows/gradle.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/44aea8c1b9c74950b267975add9002e8)](https://www.codacy.com/gh/SDARG/jreliability/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SDARG/jreliability&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/44aea8c1b9c74950b267975add9002e8)](https://www.codacy.com/gh/SDARG/jreliability/dashboard?utm_source=github.com&utm_medium=referral&utm_content=SDARG/jreliability&utm_campaign=Badge_Coverage)
[![](https://jitpack.io/v/sdarg/jreliability.svg)](https://jitpack.io/#sdarg/jreliability)

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

1.  Fork it!
2.  Create your feature branch: `git checkout -b my-new-feature`
3.  Commit your changes: `git commit -am 'Add some feature'`
4.  Push to the branch: `git push origin my-new-feature`
5.  Submit a pull request :D

## History

JReliability was formerly hosted at https://sourceforge.net/projects/jreliability/

## Credits

Brought to you by
*   Michael Glaß
*   Felix Reimann
*   Martin Lukasiewycz
*   Faramarz Khosravi
*   Henning Oehmen

## License

JReliability uses [LGPLv3](./LICENSE).
