cmake_minimum_required(VERSION 2.8)

add_definitions("-std=c++11 -Wall")

include_directories(../../lua)
include_directories(./libs)

set(ALL_SRC
    ./init.cpp
    ./libs/toolslib.cpp
    ./libs/loglib.cpp
    )

add_library(luaextension STATIC ${ALL_SRC})
