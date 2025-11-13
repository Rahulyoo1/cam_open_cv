cmake_minimum_required(VERSION 3.10.2)
project(native-lib)

add_library(native-lib SHARED native-lib.cpp)

add_library(opencv_java4 SHARED IMPORTED)
set_target_properties(opencv_java4 PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libopencv_java4.so)

target_include_directories(native-lib PRIVATE ${CMAKE_SOURCE_DIR}/include)

find_library(log-lib log)

target_link_libraries(native-lib opencv_java4 ${log-lib})
