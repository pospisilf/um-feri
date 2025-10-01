#!/bin/bash

# Compile Java files
javac -d bin src/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Initialize variables for the parameters
    WRITE_FLAG=""
    COMPARE_FLAG=""

    # Loop through each argument to check for "debug" and "test"
    for arg in "$@"; do
        if [ "$arg" == "write" ]; then
            WRITE_FLAG="writeToFile"
        elif [ "$arg" == "compare" ]; then
            COMPARE_FLAG="compare"
        fi
    done

    # Run the MainTest class with the relevant flags
    java -cp bin MainTest $WRITE_FLAG $COMPARE_FLAG
else
    echo "Compilation failed."
fi
