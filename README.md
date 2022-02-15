# wordle_hack, a solver for the popular game WORDLE.
## How to run
- Have JVM and sbt installed on your machine.  
- Enter the directory and type ```sbt run```
- Follow the instructions
## Dictionaries
More than one dictionaries are bundled with this repo. 

The directory **dictionary/** is a git submodule referencing [dolph's dictionary-repo on Github](https://github.com/dolph/dictionary). 

Another dictionary is Webster's Unabridged Dicttionary I found from Project Gutenburg's website. This one is located under the directory **resources/**. 

The default dictionary (whose path is hard-coded in source code) is dolph's ```enable1.txt```. 