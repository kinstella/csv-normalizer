# CSV Normalizer

## Installation

System dependencies:
This is a Clojure application built with leiningen.
This requires a few items to be installed.

#### Installation on MacOS
On MacOS, with homebrew, you should be able to install the necessary Clojure build system with the following commands, although this may take a few minutes.

This approach assumes you have 
1. Homebrew installed using the following approach https://brew.sh
2. Java Runtime with java in your PATH. https://www.andrewhoog.com/post/3-ways-to-install-java-on-macos-2023/

Then simply install Clojure and Leiningen with the following commands:
```
brew install clojure

brew install leiningen
```

To build the executable, execute the following Leiningen command to build an executable:
```
lein uberjar
```
This will create two JVM uberjars in the `./target` directory, with one `standalone` version.

For convenience, a bash shell script exists as `normalizer` in the root of this repository. You may need to set it to run as an executable, using the following:

`chmod 755 normalizer`

#### Installation on Ubuntu

On Ubuntu, you'll need Leiningen, Clojure, and a JDK:
You can install all of these by installing Leiningen through apt:

`sudo apt install leiningen`

Once installed, to build the system, you should be able to type:
`lein uberjar`

This will create a standalone uberjar in the ./target directory.

For convenience, a bash shell script exists as `normalizer` in the root of this repository. You may need to enable it as an executable, with the following command:

`chmod 755 normalizer`

## Usage

This tool accepts STDIN as input and outputs to STDOUT, so you should be able to use the standard redirect operators with the executable.

For example, you should be able to type the following for the normalizer to read a CSV at `resources/sample.csv` and output a new CSV file at `normalized.csv`:
```
./normalizer < resources/sample.csv > normalized.csv
```

## TODO

This is a work-in-progress, with the following modifications to be added when time allows:

- [ ] Add some unit tests
- [ ] Use babashka instead of bash as a shell script

