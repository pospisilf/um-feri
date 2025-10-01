#!/bin/bash

# Compile Java files
javac -d bin src/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Initialize variables for the parameters
    DEBUG_FLAG=""
    RUNALL_FLAG=""
    RUNONE_FLAG=""
    COMPARE_FLAG=""

    # Loop through each argument to check for "debug" and "test"
    for arg in "$@"; do
        if [ "$arg" == "write" ]; then
            DEBUG_FLAG="writeToFile"
        fi
    done

    # Run the MainTest class with the relevant flags
    java -cp bin MainTest $DEBUG_FLAG $RUNALL_FLAG $RUNONE_FLAG
else
    echo "Compilation failed."
fi
