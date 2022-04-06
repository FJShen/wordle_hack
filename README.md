# wordle_hack, a solver for the popular game WORDLE.
## Download
This repo includes a git sub-module located under  **dictionary/**. Simply using ```git clone git@github.com:FJShen/wordle_hack.git``` does not download the submodule. 

In order to download the submodule along with my repo, use *--recurse-submodules* like this: ```git clone --recurse-submodules git@github.com:FJShen/wordle_hack.git```

If you have already cloned my repo and the **dictionary/** directory is empty, it means the submodule is not cloned. In that case run: ```git submodule update --init --recursive```  in the root directory of my project. 

## How to run
- Have JVM and sbt installed on your machine.  
- Enter the directory and type ```sbt run```
- Follow the instructions
- ![image](https://user-images.githubusercontent.com/50934207/162050636-e31951d5-d0a0-44df-8c31-824209dbc985.png)

## Dictionaries
More than one dictionaries are bundled with this repo. 

The directory **dictionary/** is a git submodule referencing [dolph's dictionary-repo on Github](https://github.com/dolph/dictionary). 

Another dictionary is Webster's Unabridged Dicttionary I found from Project Gutenburg's website. This one is located under the directory **resources/**. 

The default dictionary (whose path is hard-coded in source code) is dolph's ```enable1.txt```. 
