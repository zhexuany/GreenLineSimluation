export CLASSPATH="bin"
mkdir -p bin
./findAllJavafiles.sh

javac @javaFiles -d bin

