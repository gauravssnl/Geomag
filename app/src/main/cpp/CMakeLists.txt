cmake_minimum_required(VERSION 3.22.1)

project(Geomag)

set(LIBS_DIR ${CMAKE_SOURCE_DIR}/../golang/libs)

link_directories(${LIBS_DIR}/${CMAKE_ANDROID_ARCH_ABI})
include_directories(${LIBS_DIR}/${CMAKE_ANDROID_ARCH_ABI})

file(GLOB_RECURSE SRCS *.cpp)
file(GLOB_RECURSE HDRS *.h)

add_library(geomag SHARED ${SRCS} ${HDRS})

find_library(log-lib log)
target_link_libraries(geomag PUBLIC igrf wmm ${log-lib})
